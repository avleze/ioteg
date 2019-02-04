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
	private static final String MIN_ATTR = "min";
	private static final String MAX_ATTR = "max";
	private static final String VALUE_ATTR = "value";
	
	protected Map<String, Double> variables = new HashMap<>();
	protected List<Rule<Double, Double, Double, Double, String>> rules = new ArrayList<>();
	protected Double generatedValue;
	protected Parser parser;
	protected static Random r;
	protected static Logger logger;
	protected Integer eventsCustomBehaviour;
	protected Integer controlCustomBehaviour;
	protected String fileCustomBehaviour;
	protected Integer totalEventsGenerated;
	protected Integer numOfEventsToGenerate;
	
	static {
		logger = Logger.getRootLogger();
		try {
			r = SecureRandom.getInstanceStrong();
		} catch (NoSuchAlgorithmException e) {
			logger.error(e);
		}
	}



	public CustomiseGeneration(Integer numOfEventsToGenerate, Integer eventsCustomBehaviour, Integer controlCustomBehaviour,
			String fileCustomBehaviour) throws JDOMException, IOException {
		super();
		this.numOfEventsToGenerate = numOfEventsToGenerate;
		this.eventsCustomBehaviour = eventsCustomBehaviour;
		this.controlCustomBehaviour = controlCustomBehaviour;
		this.fileCustomBehaviour = fileCustomBehaviour;
		this.totalEventsGenerated = 0;
		this.generatedValue = 0.0;
		this.parser = new Parser();
		readCustomFile(fileCustomBehaviour);
	}

	public void readCustomFile(String value) throws JDOMException, IOException {

		File xmlFile = new File(value);

		if (xmlFile.exists()) {
			readVariables(xmlFile);
			readRules(xmlFile);
		}
	}

	private void readRules(File xmlFile) throws JDOMException, IOException {
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

	private Double obtainOperationValue(String operation) throws IOException {

		Parser parser = new Parser();
		ExpressionAST exp = parser.parse(operation);

		return exp.evaluate(variables);
	}

	public void readVariables(File xmlFile) throws JDOMException, IOException {
		SAXBuilder builder = new SAXBuilder();

		// To build a document from the xml
		Document document = builder.build(xmlFile);

		// To get the root
		Element rootNode = document.getRootElement();

		for (Element variablesInNode : rootNode.getChildren(VARIABLES_TAG))
			for (Element variable : variablesInNode.getChildren(VARIABLE_TAG))
				variables.put(variable.getAttributeValue(NAME_ATTR), obtainVariableValue(variable));
	}

	private Double obtainVariableValue(Element item) throws DataConversionException, IOException {

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

	

	public Integer getEventsCustomBehaviour() {
		return eventsCustomBehaviour;
	}

	public Integer getControlCustomBehaviour() {
		return controlCustomBehaviour;
	}

	public List<Rule<Double, Double, Double, Double, String>> getRules() {
		return rules;
	}

	public String generateValue() {

		Rule<Double, Double, Double, Double, String> rule = rules.get(0);

		getRuleGeneratedValue(rule);

		controlCustomBehaviour++;
		totalEventsGenerated++;
		
		if(totalEventsGenerated == numOfEventsToGenerate)
			rules.clear();
		
		if(rule.getWeight() < 1 && rule.getWeight() > 0)
		{
			Double eventsPerRule = rule.getWeight() * eventsCustomBehaviour;
			
			if(eventsPerRule.intValue() == controlCustomBehaviour)
			{
				rules.remove(rule);
				controlCustomBehaviour = 0;
			}
		}
		
		return Double.toString(generatedValue);
	}

	private void getRuleGeneratedValue(Rule<Double, Double, Double, Double, String> rule) {

		if (rule.getValue() != null)
			generatedValue = rule.getValue();

		if (rule.getMin() != null) {
			if ((rule.getSequence() == null)) {
				double min = rule.getMin();
				double max = rule.getMax();
				generatedValue = (min + Math.random() * (max - min));
			} 
			else if (rule.getSequence().equalsIgnoreCase(DEC_VALUE))
				generateRuleValueDecSequence(rule);
			else if (rule.getSequence().equalsIgnoreCase(INC_VALUE))
				generateRuleValueIncSequence(rule);

		}
	}

	private void generateRuleValueDecSequence(Rule<Double, Double, Double, Double, String> rule) {
		Double min = rule.getMin();
		Double max;
		if (generatedValue == 0)
			max = rule.getMax();
		else
			max = (double) generatedValue;

		if (min < max)
			generatedValue = r.doubles(min, max).findFirst().getAsDouble();
		else
			generatedValue = min;
	}

	private void generateRuleValueIncSequence(Rule<Double, Double, Double, Double, String> rule) {
		Double min;
		Double max = rule.getMax();
		if (generatedValue == 0)
			min = rule.getMin();
		else
			min = generatedValue;

		if (min < max)
			generatedValue =  r.doubles(min, max).findFirst().getAsDouble();
		else
			generatedValue = max;
	}
}
