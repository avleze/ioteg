package com.ioteg;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

import org.apache.log4j.Logger;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import com.ioteg.builders.FieldBuilder;
import com.ioteg.eplutils.EPLConditionsParser;
import com.ioteg.generators.Generable;
import com.ioteg.generators.GeneratorsFactory;
import com.ioteg.model.Field;

public class EventGenerator {

	protected static final Set<String> types;
	protected static List<Map<String, List<Trio<String, String, String>>>> fieldvalues = new ArrayList<>();
	public static int totalnumevents;
	public static int iteration;
	protected static List<Integer> iterationvalues;
	protected static Random r;
	protected static Logger logger;

	static {
		types = new TreeSet<>();
		types.add("Integer");
		types.add("Float");
		types.add("Long");
		types.add("Boolean");
		types.add("String");
		types.add("Alphanumeric");
		types.add("Date");
		types.add("Time");

		logger = Logger.getRootLogger();
		try {
			r = SecureRandom.getInstanceStrong();
		} catch (NoSuchAlgorithmException e) {
			logger.error(e);
		}
	}

	public static void main(String[] args) throws Exception {

		String xmlEventSchema = args[0];
		String outputFormat = args[1];
		String eplFilePath = args[2];
		String output = "";
		String path = xmlEventSchema.substring(0, xmlEventSchema.lastIndexOf('/') + 1);
		SAXBuilder builder = new SAXBuilder();
		File xmlFile = new File(xmlEventSchema);

		try (FileWriter values = new FileWriter(path + output)) {

			if (!ValidationUtil.validStandart(xmlFile))
				System.exit(1);

			// To build a document from the xml
			Document document = builder.build(xmlFile);

			// To get the root
			Element rootNode = document.getRootElement();

			output = rootNode.getAttributeValue("name") + "." + outputFormat;

			if (!eplFilePath.equals("null"))
				getEPLValues(eplFilePath, rootNode);

			if (outputFormat.equals("json")) 
				JsonUtil.jsonFormatValues(values, document);
			if (outputFormat.equals("xml")) 
				XmlUtil.xmlFormatValues(values, document);
			if (outputFormat.equals("csv")) 
				CsvUtil.csvFormatValues(values, document);
			

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
	public static void getEPLValues(String EPLfile, Element rootNode) throws IOException {
		EPLConditionsParser eplConditionsParser = new EPLConditionsParser(EPLfile, rootNode);
		fieldvalues = eplConditionsParser.getRestrictions(totalnumevents);
		iterationvalues = eplConditionsParser.getIterationValues();
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
	public static String generateValueSimpleType(String type, Element field) throws JDOMException, IOException {

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
	public static StringBuilder generateValueComplexType(Element field, String type) throws JDOMException, IOException {

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
					result.append(JsonUtil.normalFieldJson(simple));
					result.append("}");
				}
				if (type.equals("xml")) {
					result.append(" type=\"" + simple.getAttributeValue("type") + "\">");
					result.append(XmlUtil.normalFieldsXml(simple));
				}
				if (type.equals("csv")) {
					result.append(CsvUtil.NormalFieldCsv(simple) + ",");
				}
			} else {
				if (type.equals("xml")) {
					result.append(" type=\"" + simple.getAttributeValue("type") + "\">");
				}

				String stype = simple.getAttributeValue("type");
				finalvalue = generateValueSimpleType(stype, simple);
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
			finalvalue = generateValueSimpleType(stype, simple);
		}
		if ((field.getAttributeValue("dependence") == null) && (field.getAttributeValue("chooseone") == null)) {
			if (simpletype.get(0).getName().equals("field")) {
				if (type.equals("json")) {
					result.append("{");
					for (int s = 0; s < simpletype.size(); s++) {
						Element simple = simpletype.get(s);
						result.append(JsonUtil.normalFieldJson(simple));
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
						result.append(XmlUtil.normalFieldsXml(simple));
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
					value = generateValueSimpleType(stype, simple);
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
	public static boolean existType(String type) {

		return types.contains(type);
	}

}
