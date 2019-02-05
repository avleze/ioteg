package com.ioteg;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import com.ioteg.builders.FieldBuilder;
import com.ioteg.generators.Generable;
import com.ioteg.generators.GeneratorsFactory;
import com.ioteg.model.Field;

public class EventGenerator {

	protected static final Set<String> types;
	protected static List<Map<String, List<Trio<String, String, String>>>> fieldvalues = new ArrayList<>();
	public static int totalnumevents;
	public static int iteration;
	protected static final List<Integer> iterationvalues = new ArrayList<>();
	protected static Random r;
	protected static Logger logger;
	
	static {
		types = new TreeSet<>();
		types.add("Integer");
		types.add("Float");
		types.add("Long");
		types.add("Boolean");
		types.add("String");
		types.add("Date");
		types.add("Time");

		logger = Logger.getRootLogger();
		try {
			r = SecureRandom.getInstanceStrong();
		} catch (NoSuchAlgorithmException e) {
			logger.error(e);
		}
	}

	public static void main(String args[]) throws Exception {

		String input = args[0];
		String type = args[1];
		String EPLfile = args[2];
		String output = "";
		String path = input.substring(0, input.lastIndexOf("/") + 1);
		SAXBuilder builder = new SAXBuilder();
		File xmlFile = new File(input);
		
		try (FileWriter values = new FileWriter(path + output)){

			Calendar cal2 = Calendar.getInstance();
			System.out.println("Start time: " + cal2.get(Calendar.MINUTE) + ":" + cal2.get(Calendar.SECOND) + ":"
					+ cal2.get(Calendar.MILLISECOND));

			if (!ValidationUtil.validStandart(xmlFile))
				System.exit(1);

			// To build a document from the xml
			Document document = builder.build(xmlFile);

			// To get the root
			Element rootNode = document.getRootElement();

			output = rootNode.getAttributeValue("name") + "." + type;

			if (!EPLfile.equals("null")) { // To generate the values according to the queries
				GetEPLValues(EPLfile, rootNode);
				RemovingComplexType(rootNode);
			}

			if (type.equals("json")) {
				JsonUtil.JsonFormatValues(values, document);
			}
			if (type.equals("xml")) {
				XmlUtil.XmlFormatValues(values, document);
			}
			if (type.equals("csv")) {
				CsvUtil.CsvFormatValues(values, document);
			}

			Calendar cal3 = Calendar.getInstance();
			logger.info("End time: " + cal3.get(Calendar.MINUTE) + ":" + cal3.get(Calendar.SECOND) + ":"
					+ cal3.get(Calendar.MILLISECOND));

		} catch (IOException io) {
			logger.error(io.getMessage());
		} catch (JDOMException jdomex) {
			logger.error(jdomex.getMessage());
		}
	}

	/**
	 * A function to obtain values from the fields elements at the where clauses of
	 * the EPL queries to assign them to the fields of the events
	 * 
	 * @param EPLfile  contains the EPL queries
	 * @param rootNode is the root of the event definition
	 * @throws IOException if the file causes problems
	 */
	public static void GetEPLValues(String EPLfile, Element rootNode) throws IOException {
		List<String> fieldnames = new ArrayList<>();
		List<Element> blockchilds = rootNode.getChildren("block");
		List<Element> fieldchilds = new ArrayList<>();

		for (int i = 0; i < blockchilds.size(); i++) {
			Element blck = blockchilds.get(i);

			if (blck.getAttributeValue("repeat") != null) {
				fieldchilds = blck.getChildren("field");
				totalnumevents = Integer.parseInt(blck.getAttributeValue("repeat"));
			}
		}

		for (int i = 0; i < fieldchilds.size(); i++) {
			Element field = fieldchilds.get(i);
			fieldnames.add(field.getAttributeValue("name"));
		}

		FileReader eplF = new FileReader(new File(EPLfile));
		String query = "";
		
		try(BufferedReader bf = new BufferedReader(eplF)) {
			while (((query = bf.readLine()) != null)) {
				if (query.contains(" where ") || query.contains(" WHERE ")) {
					String subquery = "";
					if (query.indexOf(" where ") != -1) {
						subquery = query.substring(query.indexOf(" where ") + 7);
					} else {
						subquery = query.substring(query.indexOf(" WHERE ") + 7);
					}
					String copyquery = subquery;

					/* To save the logical operators */

					Pattern logicalop = Pattern.compile(" or | and | OR | AND ");
					Matcher logicalmatcher = logicalop.matcher(copyquery);
					List<String> logicaloplist = new ArrayList<>();

					while (logicalmatcher.find()) {
						logicaloplist.add(logicalmatcher.group().toLowerCase().trim());
					}

					/* To save the field, the relational operator and the value */

					Pattern pattern = Pattern.compile("<=|>=|<|>|=|!=");
					Matcher matcher = pattern.matcher(copyquery);
					Trio<String, String, String> lastFieldPut = null;
					// Check all occurrences
					int i = 0; // to control the loop
					while (matcher.find()) {
						String field = copyquery.substring(0, matcher.start());
						field = field.trim();
						String operator = matcher.group();
						copyquery = copyquery.substring(matcher.end() + 1);

						copyquery = copyquery.trim();

						Pattern word = Pattern.compile("(['\"])(.*?)(['\"][$]?)");
						Pattern number = Pattern.compile("(?<!\\w)-?[0-9]+[$]?");
						Pattern booleanPattern = Pattern.compile("true|false[$]?");

						Matcher valuechar = word.matcher(copyquery);
						Matcher valuenum = number.matcher(copyquery);
						Matcher valueboolean = booleanPattern.matcher(copyquery);
						String finalvalue = "";
						String allmatch = "";

						if (valuechar.find()) {
							finalvalue = valuechar.group(2);
							allmatch = valuechar.group();
						} else if (valuenum.find()) {
							finalvalue = valuenum.group(0);
							allmatch = finalvalue;
						} else if (valueboolean.find()) {
							finalvalue = valueboolean.group(0);
							allmatch = finalvalue;
						}

						copyquery = copyquery.substring(copyquery.indexOf(allmatch) + allmatch.length());
						Trio<String, String, String> value = new Trio<>(field, operator, finalvalue);
						if (!copyquery.isEmpty()) {
							if (!logicaloplist.isEmpty()) {
								if (logicaloplist.get(0).equals("or")) {
									copyquery = copyquery.substring(3);
								} else {
									copyquery = copyquery.substring(4);
								}
							}
						}

						if (i > 0) {
							if (!logicaloplist.isEmpty()) {
								if (logicaloplist.get(0).equals("or")) {
									int originalsize = fieldvalues.size();
									for (int j = 0; j < originalsize; j++) {
										Map<String, List<Trio<String, String, String>>> values = new HashMap<>();

										for (Entry<String, List<Trio<String, String, String>>> entry : fieldvalues
												.get(j).entrySet()) {
											List<Trio<String, String, String>> list = new ArrayList<>();
											list.addAll(entry.getValue());
											values.put(entry.getKey(), list);
										}

										values.get(lastFieldPut.getFirst()).remove(lastFieldPut);
										values.get(value.getFirst()).add(value);
										fieldvalues.add(values);
									}
									logicaloplist.remove(0);
								} else if (logicaloplist.get(0).equals("and")) {
									for (Map<String, List<Trio<String, String, String>>> values : fieldvalues)
										if (values.get(value.getFirst()) != null)
											values.get(value.getFirst()).add(value);
										else {
											List<Trio<String, String, String>> list = new ArrayList<>();
											list.add(value);
											values.put(value.getFirst(), list);
										}
									logicaloplist.remove(0);
								}
							}
						} else {
							if (fieldvalues.isEmpty()) {
								Map<String, List<Trio<String, String, String>>> values = new HashMap<>();
								List<Trio<String, String, String>> list = new ArrayList<>();
								list.add(value);
								values.put(value.getFirst(), list);
								fieldvalues.add(values);
							} else {
								for (Map<String, List<Trio<String, String, String>>> values : fieldvalues)
									if (values.get(value.getFirst()) != null)
										values.get(value.getFirst()).add(value);
									else {
										List<Trio<String, String, String>> list = new ArrayList<>();
										list.add(value);
										values.put(value.getFirst(), list);
									}
							}
						}
						matcher.reset();
						matcher = pattern.matcher(copyquery);
						i++;

						lastFieldPut = value;
					}
				}
			}
		}
	}

	public static void RemovingComplexType(Element rootNode) {

		List<Element> blockchilds = rootNode.getChildren("block");
		List<Element> fieldchilds = new ArrayList<>();

		for (int i = 0; i < blockchilds.size(); i++) {
			Element blck = blockchilds.get(i);

			if (blck.getAttributeValue("repeat") != null) {
				fieldchilds = blck.getChildren("field");
			}
		}

		Boolean simple = false;

		for (int i = 0; i < fieldchilds.size(); i++) {
			Element field = fieldchilds.get(i);
			for (int j = 0; j < fieldvalues.size(); j++) {
				for (int k = 0; k < fieldvalues.get(j).size(); k++) {
					if (fieldvalues.get(j).get(field.getAttributeValue("name")) != null) {

						if (types.contains(field.getAttributeValue("type"))) 
							simple = true;

						if (!simple) {
							fieldvalues.get(j).get(field.getAttributeValue("name")).remove(k);
							if (fieldvalues.get(j).get(field.getAttributeValue("name")).isEmpty()) {
								fieldvalues.get(j).remove(field.getAttributeValue("name"));
								if (fieldvalues.get(j).isEmpty()) {
									fieldvalues.remove(j);
									j = 0;
									k = -1;
									break;
								}
							}
						}
						simple = false;
					}
				}
			}
		}

		/*
		 * To count the number of iterations of each field according to the the saved
		 * lists
		 */

		int listsinlist = fieldvalues.size();
		int iterationperlist = totalnumevents / (listsinlist + 1); // To generate a no-under-query-restrictions
																	// percentage
		int aux = iterationperlist;

		for (int i = 0; i < fieldvalues.size(); i++) {
			iterationvalues.add(iterationperlist);
			iterationperlist = iterationperlist + aux;
		}
	}

	/**
	 * The assigned type is in the IoT-EG. This function select the correct value
	 * generator according to the type.
	 * 
	 * @param type  is the String to check
	 * @param field The element which the type @param type
	 * @return a value of the specific type according to the declared restriction
	 *         and attributes of the Element field
	 * @throws IOException
	 * @throws JDOMException
	 */
	public static String GenerateValueSimpleType(String type, Element field) throws JDOMException, IOException {

		String value = "";
		String fieldname = field.getAttributeValue("name");

		FieldBuilder theBuilder = new FieldBuilder();
		Field f = theBuilder.build(field);
		Generable generator = null;

		if (!fieldvalues.isEmpty()) {
			Map<String, List<Trio<String, String, String>>> values = fieldvalues.get(0);

			if (values.get(fieldname) != null) {
				generator = GeneratorsFactory.makeQueryRestrictionGenerator(f, values.get(fieldname));
				value = generator.generate(f, 1).get(0);

				if (!iterationvalues.isEmpty() && iterationvalues.get(0).equals(iteration)) {
					iterationvalues.remove(0);
					fieldvalues.remove(0);
				}
			}
		} else {
			generator = GeneratorsFactory.makeGenerator(f, totalnumevents);
			value = generator.generate(f, 1).get(0);
		}

		return value;
	}

	/**
	 * The assigned type is not in the IoT-EG. This function construct the
	 * complextype (composed by simple types).
	 * 
	 * @param field The element which a complextype
	 * @return a value of the specific type according to the declared restriction
	 *         and attributes of the Element field
	 * @throws IOException
	 * @throws JDOMException
	 */
	public static StringBuilder GenerateValueComplexType(Element field, String type) throws JDOMException, IOException {

		String quotes = field.getAttributeValue("quotes");
		if (quotes == null)
			quotes = "false";

		String value = "";
		String finalvalue = "";
		StringBuilder result = new StringBuilder();
		int chosen = 0;
		List<Element> simpletype = field.getChildren();

		if (field.getAttributeValue("chooseone") != null) { // Default: get attribute doesn't exist
			int max = simpletype.size() - 1;
			chosen = r.nextInt(((max - 0) + 1) + 0);
			Element simple = simpletype.get(chosen);

			if (simple.getName().equals("field")) {
				if (type.equals("json")) {
					result.append("{");
					result.append(JsonUtil.NormalFieldJson(simple));
					result.append("}");
				}
				if (type.equals("xml")) {
					result.append(" type=\"" + simple.getAttributeValue("type") + "\">");
					result.append(XmlUtil.NormalFieldsXml(simple));
				}
				if (type.equals("csv")) {
					result.append(CsvUtil.NormalFieldCsv(simple) + ",");
				}
			} else {
				if (type.equals("xml")) {
					result.append(" type=\"" + simple.getAttributeValue("type") + "\">");
				}

				String stype = simple.getAttributeValue("type");
				finalvalue = GenerateValueSimpleType(stype, simple);
			}
		}
		if (field.getAttributeValue("dependence") != null) {
			if (field.getAttributeValue("dependence").equals("true")) {
				int max = simpletype.size() - 1;
				chosen = r.nextInt(((max - 1) + 1) + 1);
			}
			Element simple = simpletype.get(chosen);

			if (type.equals("xml")) {
				result.append(" type=\"" + simple.getAttributeValue("type") + "\">");
			}

			String stype = simple.getAttributeValue("type");
			finalvalue = GenerateValueSimpleType(stype, simple);
		}
		if ((field.getAttributeValue("dependence") == null) && (field.getAttributeValue("chooseone") == null)) {
			if (simpletype.get(0).getName().equals("field")) {
				if (type.equals("json")) {
					result.append("{");
					for (int s = 0; s < simpletype.size(); s++) {
						Element simple = simpletype.get(s);
						result.append(JsonUtil.NormalFieldJson(simple));
						if (s < simpletype.size() - 1) {
							result.append(",");
						}
					}
					result.append("}");
				}
				if (type.equals("xml")) {
					result.append(">\n");
					for (int s = 0; s < simpletype.size(); s++) {
						Element simple = simpletype.get(s);
						result.append(XmlUtil.NormalFieldsXml(simple));
						if (s < simpletype.size() - 1) {
							;
						}
					}
				}
				if (type.equals("csv")) {
					for (int s = 0; s < simpletype.size(); s++) {
						Element simple = simpletype.get(s);
						result.append(CsvUtil.NormalFieldCsv(simple));
						if (s < simpletype.size() - 1) {
							result.append(",");
						}
					}
				}
			} else {
				if (type.equals("xml")) {
					result.append(" type=\"" + field.getAttributeValue("type") + "\">");
				}

				for (int s = 0; s < simpletype.size(); s++) {
					Element simple = simpletype.get(s);
					String stype = simple.getAttributeValue("type");
					value = GenerateValueSimpleType(stype, simple);
					finalvalue = finalvalue + value;
				}
			}
		}

		if (quotes.equals("true")) {
			result.append("\"" + finalvalue + "\"");
		} else {
			result.append(finalvalue);
		}

		return result;
	}

	/**
	 * Determine if the type is a "basic" type include in the IoT-EG
	 * 
	 * @param type is the String to check
	 * @return true or false
	 */
	public static boolean ExistType(String type) {
		
		return types.contains(type);
	}

}
