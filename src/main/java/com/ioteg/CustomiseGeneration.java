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

	private static final String NAME_ATTR = "name";
	private static final String VARIABLE_TAG = "variable";
	private static final String VARIABLES_TAG = "variables";
	private static final String WEIGHT_ATTR = "weight";
	private static final String RULE_TAG = "rule";
	private static final String RULES_TAG = "rules";
	private static final String SEQUENCE_ATTR = "sequence";
	private static final String INC_VALUE = "inc";
	private static final String DEC_VALUE = "dec";
	private static final String SIMULATIONS_TAG = "simulations";
	private static final String MIN_ATTR = "min";
	private static final String MAX_ATTR = "max";
	private static final String VALUE_ATTR = "value";
	protected static Map<String, Double> variables = new HashMap<>();
	protected static List<Rule<Double, Double, Double, Double, String>> rules = new ArrayList<>();
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
			readRules(xmlFile);
		}
	}

	private static void readRules(File xmlFile) throws JDOMException, IOException {
		SAXBuilder builder = new SAXBuilder();

		// To build a document from the xml
		Document document = builder.build(xmlFile);

		// To get the root
		Element rootNode = document.getRootElement();

		for (Element rulesInNode : rootNode.getChildren(RULES_TAG))
			for (Element rule : rulesInNode.getChildren(RULE_TAG)) {
				Double weight = Double.valueOf(rule.getAttributeValue(WEIGHT_ATTR));
				String valueAttr = rule.getAttributeValue(VALUE_ATTR);
				String minAttr = rule.getAttributeValue(MIN_ATTR);
				String maxAttr = rule.getAttributeValue(MAX_ATTR);
				String sequenceAttr = rule.getAttributeValue(SEQUENCE_ATTR);
				
				Double value = null;
				Double min = null;
				Double max = null;
				
				if (valueAttr != null)
					value = obtainOperationValue(valueAttr);
				if (minAttr != null && maxAttr != null) {
					min = obtainOperationValue(minAttr);
					max = obtainOperationValue(maxAttr);
				}

				rules.add(new Rule<>(weight, value, min, max, sequenceAttr));
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

		for (Element variablesInNode : rootNode.getChildren(VARIABLES_TAG))
			for (Element variable : variablesInNode.getChildren(VARIABLE_TAG))
				if (type.equals("Float"))
					variables.put(variable.getAttributeValue(NAME_ATTR), obtainVariableValue(variable));

	}

	private static Double obtainVariableValue(Element item) throws DataConversionException, IOException {

		Double result = null;
		String minStr = item.getAttributeValue(MIN_ATTR);
		String maxStr = item.getAttributeValue(MAX_ATTR);
		String valueStr = item.getAttributeValue(VALUE_ATTR);

		if (minStr != null && maxStr != null) {
			Double max;
			Double min;

			ExpressionAST exp = parser.parse(minStr);
			min = exp.evaluate(variables);
			exp = parser.parse(maxStr);
			max = exp.evaluate(variables);

			result = r.doubles(min, max).findFirst().getAsDouble();
		} else if (valueStr != null) {
			ExpressionAST exp = parser.parse(valueStr);
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
		return Integer.parseInt(rootNode.getAttributeValue(SIMULATIONS_TAG));
	}

	public static String generateValue() {

		Rule<Double, Double, Double, Double, String> r = rules.get(0);
		float eventsrule;

		if (Float.parseFloat(r.getWeight().toString()) < 1) {
			if (Float.parseFloat(r.getWeight().toString()) == 0) {
				eventsrule = 0;
				controlpercentage = 0;
			} else {
				eventsrule = EventGenerator.eventscustombehaviour * r.getWeight().floatValue();
			}
		} else {
			eventsrule = r.getWeight().floatValue();
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

	private static void getRuleGeneratedValue(Rule<Double, Double, Double, Double, String> rule) {

		if (rule.getValue() != null)
			generatedvalue = rule.getValue().floatValue();

		if (rule.getMin() != null) {
			if ((rule.getSequence() == null)) {
				double min = rule.getMin();
				double max = rule.getMax();
				generatedvalue = (float) (min + Math.random() * (max - min));
			} else if (rule.getSequence().equalsIgnoreCase(DEC_VALUE)) 
				generateRuleValueDecSequence(rule);
			else if (rule.getSequence().equalsIgnoreCase(INC_VALUE)) 
				generateRuleValueIncSequence(rule);
			
		}
	}

	private static void generateRuleValueDecSequence(Rule<Double, Double, Double, Double, String> rule) {
		Double min = rule.getMin();
		Double max;
		if (generatedvalue == 0)
			max = rule.getMax();
		else
			max = (double) generatedvalue;

		if (min != max)
			generatedvalue = (float) r.doubles(min, max).findFirst().getAsDouble();
		else
			generatedvalue = min.floatValue();
	}

	private static void generateRuleValueIncSequence(Rule<Double, Double, Double, Double, String> rule) {
		Double min;
		Double max = rule.getMax();
		if (generatedvalue == 0)
			min = rule.getMin();
		else
			min = (double) generatedvalue;

		if (min != max)
			generatedvalue = (float) r.doubles(min, max).findFirst().getAsDouble();
		else
			generatedvalue = max.floatValue();
	}
}
