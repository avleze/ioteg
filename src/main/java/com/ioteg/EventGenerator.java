package com.ioteg;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import com.ioteg.builders.FieldBuilder;
import com.ioteg.generators.Generable;
import com.ioteg.generators.Generator;
import com.ioteg.generators.GeneratorsFactory;
import com.ioteg.model.Field;

public class EventGenerator {

	public static final String[] types = { "Integer", "Float", "Long", "Boolean", "String", "Date", "Time" };
	public static int chosen;
	public static List<Map<String, List<Trio<String, String, String>>>> fieldvalues = new ArrayList<>();
	public static int totalnumevents;
	public static int iteration;
	public static int eventscustombehaviour;
	public static int controlcustombehaviour;
	public static final List<Integer> iterationvalues = new ArrayList<>();
	protected static Random r;
	protected static Logger logger;

	static {
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

		try {

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

			FileWriter values = new FileWriter(path + output);

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
			System.out.println("End time: " + cal3.get(Calendar.MINUTE) + ":" + cal3.get(Calendar.SECOND) + ":"
					+ cal3.get(Calendar.MILLISECOND));

		} catch (IOException io) {
			System.out.println(io.getMessage());
		} catch (JDOMException jdomex) {
			System.out.println(jdomex.getMessage());
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
		;
		BufferedReader bf = new BufferedReader(eplF);

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
					copyquery.trim();
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
									
									for(Entry<String, List<Trio<String, String, String>>> entry : fieldvalues.get(j).entrySet())
									{
										List<Trio<String, String, String>> list = new ArrayList<>();
										list.addAll(entry.getValue());
										values.put(entry.getKey(), list);
									}

									values.get(lastFieldPut.first).remove(lastFieldPut);
									values.get(value.first).add(value);
									fieldvalues.add(values);
								}
								logicaloplist.remove(0);
							} else if (logicaloplist.get(0).equals("and")) {
								for (Map<String, List<Trio<String, String, String>>> values : fieldvalues) 
									if(values.get(value.first) != null)
										values.get(value.first).add(value);
									else {
										List<Trio<String, String, String>> list = new ArrayList<>();
										list.add(value);
										values.put(value.first, list);
									}
								logicaloplist.remove(0);
							}
						}
					} else {
						if (fieldvalues.isEmpty()) {
							Map<String, List<Trio<String, String, String>>> values = new HashMap<>();
							List<Trio<String, String, String>> list = new ArrayList<>();
							list.add(value);
							values.put(value.first, list);
							fieldvalues.add(values);
						} else {
							for (Map<String, List<Trio<String, String, String>>> values : fieldvalues)
								if(values.get(value.first) != null)
									values.get(value.first).add(value);
								else {
									List<Trio<String, String, String>> list = new ArrayList<>();
									list.add(value);
									values.put(value.first, list);
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

		bf.close();
		eplF.close();

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

						for (int t = 0; t < types.length; t++) {
							if (types[t].equals(field.getAttributeValue("type"))) {
								simple = true;
							}

						}

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
		Generable generator = GeneratorsFactory.makeGenerator(f);
		
		if (!fieldvalues.isEmpty()) {
			Map<String,List<Trio<String, String, String>>> values = fieldvalues.get(0);

				if (values.get(fieldname) != null) {
					switch (type) {
					case "Integer":
						value = GenerateIntegerQueryRestriction(field);
						break;
					case "Float":
						value = GenerateFloatQueryRestriction(field);
						break;
					case "Long":
						value = GenerateLongQueryRestriction(field);
						break;
					case "String":
						value = GenerateStringQueryRestriction(field);
						break;
					case "Alphanumeric":
						value = GenerateAlphanumericQueryRestriction(field);
						break;
					case "Boolean":
						value = GenerateBooleanQueryRestriction(field);
						break;
					case "Date":
						value = GenerateDateQueryRestriction(field);
						break;
					case "Time":
						value = GenerateTimeQueryRestriction(field);
						break;
					}

			

			if (!iterationvalues.isEmpty()) {
				if (iterationvalues.get(0).equals(iteration)) {
					iterationvalues.remove(0);
					fieldvalues.remove(0);
				}
			}
		}
		} else if(generator != null){
			if(field.getAttributeValue("custom_behaviour") == null)
				value = generator.generate(f, 1).get(0);
			else
				value = GenerateFloat(field);
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
		if(quotes == null)
			quotes = "false";
		
		String value = "";
		String finalvalue = "";
		StringBuilder result = new StringBuilder();

		List<Element> simpletype = field.getChildren();

		if (field.getAttributeValue("chooseone") != null) { // Default: get attribute doesn't exist
			int max = simpletype.size() - 1;
			int chosen = r.nextInt(((max - 0) + 1) + 0);
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
		boolean exist = false;

		if(type != null) {
			for (int i = 0; i < types.length; i++) {
				if (type.equals(types[i])) {
					exist = true;
				}
			}}
		return exist;
	}

	
	/**
	 * Generate a value of Time type according to the query/ies value/s
	 * 
	 * @param field is the Element in which the value will be assigned
	 * @return a value of the Time type according not only to the queries
	 *         restriction but also to the declared restriction
	 */
	private static String GenerateTimeQueryRestriction(Element field) {
		String result = "";
		String fieldname = field.getAttributeValue("name");
		List<Trio<String, String, String>> values = fieldvalues.get(0).get(fieldname);
		String operator = "";
		String value = "";

		
		if (values != null) {
			operator = values.get(0).second;
			value = values.get(0).third;
		}

		if (operator.equals("=")) {
			result = value;
		}
		if (operator.equals("!=")) {
			do {
				if (field.getAttributeValue("format") != null) {
					java.util.Date randomDate = RandomUtil.getRandomDate(
							new java.util.Date(RandomUtil.getMinimumDate()),
							new java.util.Date(RandomUtil.getMaximumDate()), false);
					SimpleDateFormat sdf = new SimpleDateFormat(field.getAttributeValue("format"));
					result = sdf.format(randomDate);
				}
			} while (result.equals(value));
		}

		return result;
	}


	/**
	 * Generate a value of Date type according to the query/ies value/s
	 * 
	 * @param field is the Element in which the value will be assigned
	 * @return a value of the Date type according not only to the queries
	 *         restriction but also to the declared restriction
	 */
	private static String GenerateDateQueryRestriction(Element field) {
		String result = "";
		String fieldname = field.getAttributeValue("name");
		List<Trio<String, String, String>> values = fieldvalues.get(0).get(fieldname);
		String operator = "";
		String value = "";

		
		if (values != null) {
			operator = values.get(0).second;
			value = values.get(0).third;
		}

		if (operator.equals("=")) {
			result = value;
		}
		if (operator.equals("!=")) {

			do {
				if (field.getAttributeValue("format") != null) {
					java.util.Date dateFromDB = new java.util.Date(System.currentTimeMillis());
					Calendar calendarFromDB = Calendar.getInstance();

					calendarFromDB.setTime(dateFromDB);

					java.util.Date randomDate = RandomUtil.getRandomDate(
							new java.util.Date(RandomUtil.getMinimumDate()),
							new java.util.Date(RandomUtil.getMaximumDate()), false);
					SimpleDateFormat sdf = new SimpleDateFormat(field.getAttributeValue("format"));
					result = sdf.format(randomDate);
				}
			} while (result.equals(value));
		}
		return result;
	}

	/**
	 * Generate a value of Boolean type according to the query/ies value/s
	 * 
	 * @param field is the Element in which the value will be assigned
	 * @return a value of the Boolean type according not only to the queries
	 *         restriction but also to the declared restriction
	 */
	private static String GenerateBooleanQueryRestriction(Element field) {
		String result = "";
		String fieldname = field.getAttributeValue("name");
		List<Trio<String, String, String>> values = fieldvalues.get(0).get(fieldname);
		String operator = "";
		String value = "";

		
		if (values != null) {
			operator = values.get(0).second;
			value = values.get(0).third;
		}
		

		if (operator.equals("=")) {
			result = value;
		}

		if (operator.equals("!=")) {
			if (field.getAttributeValue("isnumeric").equals("true")) {
				if (value.equals("0")) {
					result = "1";
				} else {
					result = "0";
				}
			} else {
				if (value.equals("false")) {
					result = "true";
				} else {
					result = "false";
				}
			}
		}

		return result;
	}

	/**
	 * Generate a value of String type according to the query/ies value/s
	 * 
	 * @param field is the Element in which the value will be assigned
	 * @return a value of the String type according not only to the queries
	 *         restriction but also to the declared restriction
	 */
	private static String GenerateStringQueryRestriction(Element field) {
		String result = "";
		String fieldname = field.getAttributeValue("name");
		List<Trio<String, String, String>> values = fieldvalues.get(0).get(fieldname);
		String operator = "";
		String value = "";

		
		if (values != null) {
			operator = values.get(0).second;
			value = values.get(0).third;
		}

		if (operator.equals("=")) {
			result = value;
		}

		if (operator.equals("!=")) {
			do {
				if (field.getAttributeValue("length") != null) {

					if (field.getAttributeValue("endcharacter") != null) {
						result = RandomUtil.getRandStringRange(Integer.parseInt(field.getAttributeValue("length")),
								field.getAttributeValue("endcharacter"));
					} else {
						result = RandomUtil.getRandStringRange(Integer.parseInt(field.getAttributeValue("length")), null);
					}

					if (field.getAttributeValue("case") != null) {
						if (field.getAttributeValue("case").equals("low")) {
							result = result.toLowerCase();
						}
					}
				} else {
					if (field.getAttributeValue("endcharacter") != null) {
						result = RandomUtil.getRandStringRange(10, field.getAttributeValue("endcharacter"));
					} else {
						result = RandomUtil.getRandStringRange(10, null); // Default length
					}

					if (field.getAttributeValue("case") != null) {
						if (field.getAttributeValue("case").equals("low")) {
							result = result.toLowerCase();
						}
					}
				}

			} while (result.equals(value));
		}

		return result;
	}

	/**
	 * Generate a value of Alphanumeric type according to the query/ies value/s
	 * 
	 * @param field is the Element in which the value will be assigned
	 * @return a value of the Alphanumeric type according not only to the queries
	 *         restriction but also to the declared restriction
	 */
	private static String GenerateAlphanumericQueryRestriction(Element field) {
		String result = "";
		String fieldname = field.getAttributeValue("name");
		List<Trio<String, String, String>> values = fieldvalues.get(0).get(fieldname);
		String operator = "";
		String value = "";

		
		if (values != null) {
			operator = values.get(0).second;
			value = values.get(0).third;
		}

		if (operator.equals("=")) {
			result = value;
		}

		if (operator.equals("!=")) {
			do {

				if (field.getAttributeValue("length") != null) {

					if (field.getAttributeValue("endcharacter") != null) {
						result = RandomUtil.getAlphaNumRandStringRange(Integer.parseInt(field.getAttributeValue("length")),
								field.getAttributeValue("endcharacter"));
					} else {
						result = RandomUtil.getAlphaNumRandStringRange(Integer.parseInt(field.getAttributeValue("length")), null);
					}

					if (field.getAttributeValue("case") != null) {
						if (field.getAttributeValue("case").equals("low")) {
							result = result.toLowerCase();
						}
					}
				} else {
					if (field.getAttributeValue("endcharacter") != null) {
						result = RandomUtil.getAlphaNumRandStringRange(10, field.getAttributeValue("endcharacter"));
					} else {
						result = RandomUtil.getAlphaNumRandStringRange(10, null); // Default length
					}

					if (field.getAttributeValue("case") != null) {
						if (field.getAttributeValue("case").equals("low")) {
							result = result.toLowerCase();
						}
					}
				}

			} while (result.equals(value));
		}

		return result;
	}

	/**
	 * Generate a value of Float type
	 * 
	 * @param field is the Element in which the value will be assigned
	 * @return a value of the Float type according to the declared restriction and
	 *         attributes of the Element field
	 * @throws IOException
	 * @throws JDOMException
	 */
	private static String GenerateFloat(Element field) throws JDOMException, IOException {
		String result = "";

		if (field.getAttribute("custom_behaviour") != null) {

			if (CustomiseGeneration.rules.isEmpty()) {
				CustomiseGeneration.ReadCustomFile(field.getAttribute("custom_behaviour").getValue(),
						field.getAttribute("type").getValue());
				eventscustombehaviour = totalnumevents
						/ CustomiseGeneration.ReadSimulations(field.getAttribute("custom_behaviour").getValue());
				controlcustombehaviour = 0;
			}

			if (controlcustombehaviour != eventscustombehaviour) {
				result = CustomiseGeneration.GenerateValue();
				controlcustombehaviour++;
			} else { // No debería de llegar ya que previamente se habrá vaciado la lista de
						// reglas...
				controlcustombehaviour = 0;
				CustomiseGeneration.rules.clear();
				CustomiseGeneration.generatedvalue = 0.0f;
				result = GenerateFloat(field);
			}

		} else {
			FieldBuilder theBuilder = new FieldBuilder();
			Field floatField = theBuilder.build(field);
			Generator<Float> floatGenerator = GeneratorsFactory.makeFloatGenerator(floatField);
			if(floatGenerator != null)
				result = floatGenerator.generate(floatField, 1).get(0);

			
			
		}

		return result;
	}

	/**
	 * Generate a value of Float type according to the query/ies value/s
	 * 
	 * @param field is the Element in which the value will be assigned
	 * @return a value of the Float type according not only to the queries
	 *         restriction but also to the declared restriction
	 */
	private static String GenerateFloatQueryRestriction(Element field) {
		String result = "";
		String fieldname = field.getAttributeValue("name");
		List<Trio<String, String, String>> values = fieldvalues.get(0).get(fieldname);
		String operator = "";
		String value = "";

		
		if (values != null) {
			operator = values.get(0).second;
			value = values.get(0).third;
		}
		
		float max = Float.MAX_VALUE;
		if (field.getAttributeValue("max") != null)
			max = Float.parseFloat(field.getAttributeValue("max"));

		float min = -Float.MAX_VALUE;
		if (field.getAttributeValue("min") != null)
			min = Float.parseFloat(field.getAttributeValue("min"));

		if (operator.equals("=")) {
			result = value;
		}
		if (operator.equals(">")) {
			min = Float.parseFloat(value) + 1f; // It must be >
			result = Double.toString(r.doubles(min, max).findFirst().getAsDouble());
		}
		if (operator.equals(">=")) {
			min = Float.parseFloat(value);
			result = Double.toString(r.doubles(min, max).findFirst().getAsDouble());
		}
		if (operator.equals("<")) {
			max = Float.parseFloat(value) - 1f; // It must be <
			result = Double.toString(r.doubles(min, max).findFirst().getAsDouble());
		}
		if (operator.equals("<=")) {
			max = Float.parseFloat(value);
			result = Double.toString(r.doubles(min, max).findFirst().getAsDouble());
		}
		if (operator.equals("!=")) {
			float randomvalue = 0f;
			do {
				randomvalue = (float) r.doubles(min, max).findFirst().getAsDouble();
			} while (randomvalue == Float.parseFloat(value));

			result = Float.toString(randomvalue);
		}
		if (field.getAttributeValue("precision") != null) {
			Integer paddingSize = Integer.parseInt(field.getAttributeValue("precision"));
			String format = "%." + paddingSize + "f";
			result = String.format(Locale.US, format, Float.valueOf(result));
		}

		return result;
	}

	/**
	 * Generate a value of Integer type according to the query/ies value/s
	 * 
	 * @param field is the Element in which the value will be assigned
	 * @return a value of the Integer type according not only to the queries
	 *         restriction but also to the declared restriction
	 */
	private static String GenerateIntegerQueryRestriction(Element field) {

		String result = "";
		String fieldname = field.getAttributeValue("name");
		List<Trio<String, String, String>> values = fieldvalues.get(0).get(fieldname);
		String operator = "";
		String value = "";
		
		if (values != null) {
			operator = values.get(0).second;
			value = values.get(0).third;
		}
		
		int max, min;

		if (operator.equals("=")) {
			result = value;
		}

		if ((field.getAttributeValue("max") != null) && (field.getAttributeValue("min") != null)) {
			max = Integer.parseInt(field.getAttributeValue("max"));
			min = Integer.parseInt(field.getAttributeValue("min"));
		} else {
			// Default values
			max = 9;
			min = 0;
		}

		if (operator.equals(">")) {
			max = Integer.MAX_VALUE;

			if (field.getAttributeValue("max") != null) {
				max = Integer.parseInt(field.getAttributeValue("max"));
			}
			min = Integer.parseInt(value) + 1; // It must be >
			result = Integer.toString(r.ints(min, max).findFirst().getAsInt());
		}
		if (operator.equals(">=")) {
			max = Integer.MAX_VALUE;

			if (field.getAttributeValue("max") != null) {
				max = Integer.parseInt(field.getAttributeValue("max"));
			}
			min = Integer.parseInt(value);
			result = Integer.toString(r.ints(min, max).findFirst().getAsInt());
		}
		if (operator.equals("<")) {
			min = Integer.MIN_VALUE;

			if (field.getAttributeValue("min") != null) {
				min = Integer.parseInt(field.getAttributeValue("min"));
			}
			max = Integer.parseInt(value) - 1; // It must be <
			result = Integer.toString(r.ints(min, max).findFirst().getAsInt());
		}
		if (operator.equals("<=")) {
			min = Integer.MIN_VALUE;
			if (field.getAttributeValue("min") != null) {
				min = Integer.parseInt(field.getAttributeValue("min"));
			}
			max = Integer.parseInt(value);
			result = Integer.toString(r.ints(min, max).findFirst().getAsInt());
		}
		if (operator.equals("!=")) {
			if ((field.getAttributeValue("max") != null) && (field.getAttributeValue("min") != null)) {
				max = Integer.parseInt(field.getAttributeValue("max"));
				min = Integer.parseInt(field.getAttributeValue("min"));
			}

			int randomvalue = 0;
			do {
				randomvalue = r.ints(min, max).findFirst().getAsInt();
			} while (randomvalue == Integer.parseInt(value));
			result = Integer.toString(randomvalue);

		}

		return result;
	}

	/**
	 * Generate a value of Long type according to the query/ies value/s
	 * 
	 * @param field is the Element in which the value will be assigned
	 * @return a value of the Long type according not only to the queries
	 *         restriction but also to the declared restriction
	 */
	private static String GenerateLongQueryRestriction(Element field) {

		String result = "";
		String fieldname = field.getAttributeValue("name");
		List<Trio<String, String, String>> values = fieldvalues.get(0).get(fieldname);
		String operator = "";
		String value = "";

		
		if (values != null) {
			operator = values.get(0).second;
			value = values.get(0).third;
		}

		long max = Long.MAX_VALUE;
		if (field.getAttributeValue("max") != null)
			max = Long.parseLong(field.getAttributeValue("max"));

		long min = Long.MIN_VALUE;
		if (field.getAttributeValue("min") != null)
			min = Long.parseLong(field.getAttributeValue("min"));

		if (operator.equals("=")) {
			result = value;
		}
		if (operator.equals(">")) {
			min = Long.parseLong(value) + 1L; // It must be >
			result = Long.toString(r.longs(min, max).findFirst().getAsLong());
		}
		if (operator.equals(">=")) {
			min = Long.parseLong(value);
			result = Long.toString(r.longs(min, max).findFirst().getAsLong());
		}
		if (operator.equals("<")) {
			max = Long.parseLong(value) - 1L; // It must be <
			result = Long.toString(r.longs(min, max).findFirst().getAsLong());
		}
		if (operator.equals("<=")) {
			max = Long.parseLong(value);
			result = Long.toString(r.longs(min, max).findFirst().getAsLong());
		}
		if (operator.equals("!=")) {
			long randomvalue = 0L;

			do {
				randomvalue = r.longs(min, max).findFirst().getAsLong();
			} while (randomvalue == Long.parseLong(value));

			result = Long.toString(randomvalue);
		}

		return result;
	}
}
