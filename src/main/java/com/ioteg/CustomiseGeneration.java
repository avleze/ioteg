package com.ioteg;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jdom2.DataConversionException;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

public class CustomiseGeneration {

	public static Map<String, Object> variables = new HashMap<String, Object>();
	public static List<Rule<Object, Object, Object, Object, Object>> rules = new ArrayList<Rule<Object, Object, Object, Object, Object>>();
	public static float generatedvalue;
	public static int controlpercentage;

	public static void ReadCustomFile(String value, String type) throws JDOMException, IOException {

		File xmlFile = new File(value);

		if (xmlFile.exists()) {
			ReadVariables(xmlFile, type);
			ReadRules(xmlFile, type);
		}
	}

	private static void ReadRules(File xmlFile, String type) throws JDOMException, IOException {
		SAXBuilder builder = new SAXBuilder();

		// To build a document from the xml
		Document document = builder.build(xmlFile);

		// To get the root
		Element rootNode = document.getRootElement();

		List<Element> rulesitem = rootNode.getChildren("rules");
		List<Element> ruleitem = new ArrayList<Element>();

		for (int i = 0; i < rulesitem.size(); i++) {
			Element item = rulesitem.get(i);
			ruleitem = item.getChildren("rule");
		}

		for (int i = 0; i < ruleitem.size(); i++) {
			Element item = ruleitem.get(i);
			float weightvalue = Float.parseFloat(item.getAttributeValue("weight").toString());
			String valuevalue = null, minvalue = null, maxvalue = null;
			String sequencevalue = null;

			if (item.getAttribute("value") != null) {
				if (item.getAttributeValue("value").contains("$")) {
					String value = ObtainVariableValue(item.getAttributeValue("value"));
					if (CheckOperation(item.getAttributeValue("value"))) {
						valuevalue = Float.toString(ObtainOperationValue(item.getAttributeValue("value")));
					} else {
						valuevalue = Float.toString(Float.parseFloat(value));
					}
				} else {
					valuevalue = Float.toString(Float.parseFloat(item.getAttributeValue("value")));
				}
			}

			if (item.getAttribute("min") != null && (item.getAttributeValue("max") != null)) {

				if (item.getAttributeValue("min").contains("$")) {
					String value = ObtainVariableValue(item.getAttributeValue("min"));
					if (CheckOperation(item.getAttributeValue("min"))) {
						minvalue = Float.toString(ObtainOperationValue(item.getAttributeValue("min")));
					} else {
						minvalue = Float.toString(Float.parseFloat(value));
					}
				} else {
					float value = Float.parseFloat(item.getAttributeValue("min").toString());
					minvalue = Float.toString(value);
				}

				if (item.getAttributeValue("max").contains("$")) {
					String value = ObtainVariableValue(item.getAttributeValue("max"));
					if (CheckOperation(item.getAttributeValue("max"))) {
						maxvalue = Float.toString(ObtainOperationValue(item.getAttributeValue("max")));
					} else {
						maxvalue = Float.toString(Float.parseFloat(value));
					}
				} else {
					float value = Float.parseFloat(item.getAttributeValue("max").toString());
					maxvalue = Float.toString(value);
				}
			}

			if (item.getAttribute("sequence") != null) {
				sequencevalue = item.getAttributeValue("sequence").toString();
			}

			Rule<Object, Object, Object, Object, Object> rule = new Rule<Object, Object, Object, Object, Object>(
					weightvalue, valuevalue, minvalue, maxvalue, sequencevalue);

			rules.add(rule);
		}
	}

	public static void ReadVariables(File xmlFile, String type) throws JDOMException, IOException {
		SAXBuilder builder = new SAXBuilder();

		// To build a document from the xml
		Document document = builder.build(xmlFile);

		// To get the root
		Element rootNode = document.getRootElement();

		List<Element> variablesitem = rootNode.getChildren("variables");
		List<Element> variableitem = new ArrayList<Element>();

		for (int i = 0; i < variablesitem.size(); i++) {
			Element item = variablesitem.get(i);
			variableitem = item.getChildren("variable");
		}

		for (int i = 0; i < variableitem.size(); i++) {
			Element item = variableitem.get(i);
			switch (type) {
			case "Integer":
				break;
			case "Float":
				variables.put(item.getAttributeValue("name"), ObtainFloatValue(item));
				break;
			case "Long":
				break;
			case "String":
				break;
			case "Boolean":
				break;
			case "Date":
				break;
			case "Time":
				break;
			}
		}
	}

	private static Object ObtainFloatValue(Element item) throws DataConversionException {

		Float result = null, min, max = null;
		String value = "";

		if (item.getAttributeValue("min") != null && (item.getAttributeValue("max") != null)) {
			// Check if the values of min and max variables contain an operation
			if (item.getAttributeValue("min").contains("$")) {
				value = ObtainVariableValue(item.getAttributeValue("min"));
				if (CheckOperation(item.getAttributeValue("min"))) {
					min = ObtainOperationValue(item.getAttributeValue("min"));
				} else {
					min = Float.parseFloat(value);
				}
			} else {
				min = Float.parseFloat(item.getAttributeValue("min"));
			}

			if (item.getAttributeValue("max").contains("$")) {
				value = ObtainVariableValue(item.getAttributeValue("max"));
				if (CheckOperation(item.getAttributeValue("max"))) {
					max = ObtainOperationValue(item.getAttributeValue("max"));
				} else {
					max = Float.parseFloat(value);
				}
			} else {
				max = Float.parseFloat(item.getAttributeValue("max"));
			}

			result = min + (float) (Math.random() * ((max - min)));
		}

		if (item.getAttribute("value") != null) {
			// Check if the value is obtained from an operation which involves a saved
			// variable
			if (item.getAttributeValue("value").contains("$")) {
				value = ObtainVariableValue(item.getAttributeValue("value"));
				if (CheckOperation(item.getAttributeValue("value"))) {
					result = ObtainOperationValue(item.getAttributeValue("value"));
				} else {
					result = Float.parseFloat(value);
				}
			} else {
				result = item.getAttribute("value").getFloatValue();
			}
		}

		return result;
	}

	private static float ObtainOperationValue(String operation) {

		String[] opdiv = null;
		float result = 0;

		if (operation.contains("+")) {
			opdiv = operation.split("\\+");
			if (opdiv[0].contains("$") && result == 0.0) {
				if (CheckOperation(opdiv[1])) {
					result = ObtainOperationValue(opdiv[1]) + Float.parseFloat(ObtainVariableValue(opdiv[0]));
				} else {
					result = Float.parseFloat(ObtainVariableValue(opdiv[0])) + Float.parseFloat(opdiv[1]);
				}
			}
			if (opdiv[1].contains("$") && result == 0.0) {
				if (CheckOperation(opdiv[0])) {
					result = ObtainOperationValue(opdiv[0]) + Float.parseFloat(ObtainVariableValue(opdiv[1]));
				} else {
					result = Float.parseFloat(ObtainVariableValue(opdiv[1])) + Float.parseFloat(opdiv[0]);
				}
			}
			if (!opdiv[0].contains("$") && !opdiv[1].contains("$") && result == 0.0) {
				result = Float.parseFloat(opdiv[0]) + Float.parseFloat(opdiv[1]);
			}
		}

		if (operation.contains("-")) {
			opdiv = operation.split("-");
			if (opdiv[0].contains("$") && result == 0.0) {
				if (CheckOperation(opdiv[1])) {
					result = Float.parseFloat(ObtainVariableValue(opdiv[0])) - ObtainOperationValue(opdiv[1]);
				} else {
					result = Float.parseFloat(ObtainVariableValue(opdiv[0])) - Float.parseFloat(opdiv[1]);
				}
			}
			if (opdiv[1].contains("$") && result == 0.0) {
				if (CheckOperation(opdiv[0])) {
					result = ObtainOperationValue(opdiv[0]) - Float.parseFloat(ObtainVariableValue(opdiv[1]));
				} else {
					result = Float.parseFloat(opdiv[0]) - Float.parseFloat(ObtainVariableValue(opdiv[1]));
				}
			}
			if (!opdiv[0].contains("$") && !opdiv[1].contains("$") && result == 0.0) {
				result = Float.parseFloat(opdiv[0]) - Float.parseFloat(opdiv[1]);
			}
		}

		if (operation.contains("*")) {
			opdiv = operation.split("\\*");
			if (opdiv[0].contains("$") && result == 0.0) {
				if (CheckOperation(opdiv[1])) {
					result = Float.parseFloat(opdiv[0]) * ObtainOperationValue(opdiv[1]);
				} else {
					result = Float.parseFloat(ObtainVariableValue(opdiv[0])) * Float.parseFloat(opdiv[1]);
				}
			}
			if (opdiv[1].contains("$") && result == 0.0) {
				if (CheckOperation(opdiv[0])) {
					result = Float.parseFloat(opdiv[1]) * ObtainOperationValue(opdiv[0]);
				} else {
					result = Float.parseFloat(ObtainVariableValue(opdiv[1])) * Float.parseFloat(opdiv[0]);
				}
			}
			if (!opdiv[0].contains("$") && !opdiv[1].contains("$") && result == 0.0) {
				result = Float.parseFloat(opdiv[0]) * Float.parseFloat(opdiv[1]);
			}
		}

		if (operation.contains("/")) {
			opdiv = operation.split("/");
			if (opdiv[0].contains("$") && result == 0.0) {
				if (CheckOperation(opdiv[1])) {
					result = Float.parseFloat(ObtainVariableValue(opdiv[0])) / ObtainOperationValue(opdiv[1]);
				} else {
					result = Float.parseFloat(ObtainVariableValue(opdiv[0])) / Float.parseFloat(opdiv[1]);
				}
			}
			if (opdiv[1].contains("$") && result == 0.0) {
				if (CheckOperation(opdiv[0])) {
					result = ObtainOperationValue(opdiv[0]) / Float.parseFloat(ObtainVariableValue(opdiv[1]));
				} else {
					result = Float.parseFloat(opdiv[0]) / Float.parseFloat(ObtainVariableValue(opdiv[1]));
				}
			}
			if (!opdiv[0].contains("$") && !opdiv[1].contains("$") && result == 0.0) {
				result = Float.parseFloat(opdiv[0]) / Float.parseFloat(opdiv[1]);
			}
		}

		return result;
	}

	private static boolean CheckOperation(String variablevalue) {

		boolean exits = false;

		if (variablevalue.contains("+")) {
			exits = true;
		}
		if (variablevalue.contains("*")) {
			exits = true;
		}
		if (variablevalue.contains("/")) {
			exits = true;
		}
		if (variablevalue.contains("-")) {
			exits = true;
		}

		return exits;
	}

	private static String ObtainVariableValue(String definedvar) {

		String name = "";
		String value = "";

		name = definedvar.substring(definedvar.indexOf("(") + 1, definedvar.indexOf(")"));
		if (variables.containsKey(name)) {
			value = variables.get(name).toString();
		}

		return value;
	}

	public static int ReadSimulations(String xmlFile) throws JDOMException, IOException {
		SAXBuilder builder = new SAXBuilder();

		// To build a document from the xml
		Document document = builder.build(xmlFile);

		// To get the root
		Element rootNode = document.getRootElement();
		return Integer.parseInt(rootNode.getAttributeValue("simulations"));
	}

	public static String GenerateValue() {

		Rule<Object, Object, Object, Object, Object> r = rules.get(0);
		float eventsrule;

		if (Float.parseFloat(r.getWeight().toString()) < 1) {
			if (Float.parseFloat(r.getWeight().toString()) == 0) {
				eventsrule = 0;
				controlpercentage = 0;
			} else {
				eventsrule = EventGenerator.eventscustombehaviour * Float.parseFloat(r.getWeight().toString());
			}
		} else {
			eventsrule = Float.parseFloat(r.getWeight().toString());
		}

		if (controlpercentage == 0 && eventsrule == 0 && rules.size() == 1) {
			GetRuleGeneratedValue(r);
		} else {
			if (controlpercentage < eventsrule) {
				GetRuleGeneratedValue(r);
				controlpercentage++;
			} else {
				rules.remove(0);
				controlpercentage = 0;
				generatedvalue = 0;
				GenerateValue();
			}
		}

		return Float.toString(generatedvalue);
	}

	private static void GetRuleGeneratedValue(Rule<Object, Object, Object, Object, Object> r) {

		if (!(r.second == null)) {
			generatedvalue = Float.parseFloat(r.getValue().toString());
		}

		if (!(r.getMin() == null)) {
			if ((r.getSequence() == null)) {
				float min = Float.parseFloat(r.getMin().toString());
				float max = Float.parseFloat(r.getMax().toString());
				generatedvalue = min + (float) (Math.random() * ((max - min)));
			} else {
				if (String.valueOf(r.getSequence()).equals("dec")) {
					float min = Float.parseFloat(r.getMin().toString());
					float max;
					if (generatedvalue == 0) {
						max = Float.parseFloat(r.getMax().toString());
					} else {
						max = generatedvalue;
					}
					generatedvalue = min + (float) (Math.random() * ((max - min)));
				}
				if (String.valueOf(r.getSequence()).equals("inc")) {
					float min;
					float max = Float.parseFloat(r.getMax().toString());
					if (generatedvalue == 0) {
						min = Float.parseFloat(r.getMin().toString());
					} else {
						min = generatedvalue;
					}
					generatedvalue = min + (float) (Math.random() * ((max - min)));
				}
			}
		}
	}
}
