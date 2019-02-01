package com.ioteg;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.log4j.Logger;
import org.jdom2.DataConversionException;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import com.ioteg.exprlang.Parser;
import com.ioteg.exprlang.ast.ExpressionAST;

public class CustomiseGeneration {

	protected static Map<String, Double> variables = new HashMap<>();
	protected static List<Rule<Object, Object, Object, Object, Object>> rules = new ArrayList<>();
	protected static float generatedvalue;
	protected static int controlpercentage;
	private static Parser parser;
	protected static Random r;
	protected static Logger logger;

	static {
		logger = Logger.getRootLogger();
		parser = new Parser();
		try {
			r = SecureRandom.getInstanceStrong();
		} catch (NoSuchAlgorithmException e) {
			logger.error(e);
		}
	}

	public static void readCustomFile(String value, String type) throws JDOMException, IOException {

		File xmlFile = new File(value);

		if (xmlFile.exists()) {
			readVariables(xmlFile, type);
			readRules(xmlFile, type);
		}
	}

	private static void readRules(File xmlFile, String type) throws JDOMException, IOException {
		SAXBuilder builder = new SAXBuilder();

		// To build a document from the xml
		Document document = builder.build(xmlFile);

		// To get the root
		Element rootNode = document.getRootElement();

		for (Element rulesInNode : rootNode.getChildren("rules"))
			for (Element rule : rulesInNode.getChildren("rule")) {
				float weight = Float.parseFloat(rule.getAttributeValue("weight"));
				String value = null;
				String min = null;
				String max = null;
				String sequence = null;

				if (rule.getAttribute("value") != null)
					value = Double.toString(obtainOperationValue(rule.getAttributeValue("value")));

				if (rule.getAttribute("min") != null && (rule.getAttributeValue("max") != null)) {
					min = Double.toString(obtainOperationValue(rule.getAttributeValue("min")));
					max = Double.toString(obtainOperationValue(rule.getAttributeValue("max")));
				}

				if (rule.getAttribute("sequence") != null)
					sequence = rule.getAttributeValue("sequence");

				rules.add(new Rule<>(weight, value, min, max, sequence));
			}
	}

	private static Double obtainOperationValue(String operation) throws IOException {

		Parser parser = new Parser();
		ExpressionAST exp = parser.parse(operation);

		return exp.evaluate(variables);
	}

	public static void readVariables(File xmlFile, String type) throws JDOMException, IOException {
		SAXBuilder builder = new SAXBuilder();

		// To build a document from the xml
		Document document = builder.build(xmlFile);

		// To get the root
		Element rootNode = document.getRootElement();

		for (Element variablesInNode : rootNode.getChildren("variables"))
			for (Element variable : variablesInNode.getChildren("variable"))
				if (type.equals("Float"))
					variables.put(variable.getAttributeValue("name"), obtainVariableValue(variable));

	}

	private static Double obtainVariableValue(Element item) throws DataConversionException, IOException {

		Double result = null;

		if (item.getAttributeValue("min") != null && (item.getAttributeValue("max") != null)) {
			Double max;
			Double min;

			ExpressionAST exp = parser.parse(item.getAttributeValue("min"));
			min = exp.evaluate(variables);
			exp = parser.parse(item.getAttributeValue("max"));
			max = exp.evaluate(variables);

			result = r.doubles(min, max).findFirst().getAsDouble();
		}

		if (item.getAttribute("value") != null) {
			ExpressionAST exp = parser.parse(item.getAttributeValue("value"));
			result = exp.evaluate(variables);
		}

		return result;
	}

	public static int readSimulations(String xmlFile) throws JDOMException, IOException {
		SAXBuilder builder = new SAXBuilder();

		// To build a document from the xml
		Document document = builder.build(xmlFile);

		// To get the root
		Element rootNode = document.getRootElement();
		return Integer.parseInt(rootNode.getAttributeValue("simulations"));
	}

	public static String generateValue() {

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
			getRuleGeneratedValue(r);
		} else {
			if (controlpercentage < eventsrule) {
				getRuleGeneratedValue(r);
				controlpercentage++;
			} else {
				rules.remove(0);
				controlpercentage = 0;
				generatedvalue = 0;
				generateValue();
			}
		}

		return Float.toString(generatedvalue);
	}

	private static void getRuleGeneratedValue(Rule<Object, Object, Object, Object, Object> rule) {

		if (!(rule.second == null)) {
			generatedvalue = Float.parseFloat(rule.getValue().toString());
		}

		if (!(rule.getMin() == null)) {
			if ((rule.getSequence() == null)) {
				float min = Float.parseFloat(rule.getMin().toString());
				float max = Float.parseFloat(rule.getMax().toString());
				generatedvalue = min + (float) (Math.random() * ((max - min)));
			} else {
				if (String.valueOf(rule.getSequence()).equals("dec")) {
					float min = Float.parseFloat(rule.getMin().toString());
					float max;
					if (generatedvalue == 0) {
						max = Float.parseFloat(rule.getMax().toString());
					} else {
						max = generatedvalue;
					}
					if(min != max)
						generatedvalue = (float) r.doubles(min, max).findFirst().getAsDouble();
					else
						generatedvalue = min;
				}
				if (String.valueOf(rule.getSequence()).equals("inc")) {
					float min;
					float max = Float.parseFloat(rule.getMax().toString());
					if (generatedvalue == 0) {
						min = Float.parseFloat(rule.getMin().toString());
					} else {
						min = generatedvalue;
					}
					if(min != max)
						generatedvalue = (float) r.doubles(min, max).findFirst().getAsDouble();
					else
						generatedvalue = max;
				}
			}
		}
	}
}
