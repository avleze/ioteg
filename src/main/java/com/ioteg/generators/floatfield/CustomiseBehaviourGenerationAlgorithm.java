package com.ioteg.generators.floatfield;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ioteg.exprlang.Parser;
import com.ioteg.exprlang.ast.ExpressionAST;
import com.ioteg.generators.GenerationAlgorithm;
import com.ioteg.model.Field;
import com.ioteg.model.RuleCustomBehaviour;
import com.ioteg.model.VariableCustomBehaviour;

public class CustomiseBehaviourGenerationAlgorithm extends GenerationAlgorithm<Float> {

	private static final String INC_VALUE = "inc";
	private static final String DEC_VALUE = "dec";
	protected Integer numOfEventsToGenerate;
	protected Integer totalGeneratedEvents;
	protected Parser parser;
	protected Map<String, Double> variables;
	protected List<RuleCustomBehaviour> rules;
	protected Double generatedValue;
	protected Integer generatedEventsInCurrentRule;
	protected Integer eventsPerSimulation;
	
	public CustomiseBehaviourGenerationAlgorithm(Field field, Integer numOfEventsToGenerate) throws IOException {
		super(field);
		this.numOfEventsToGenerate = numOfEventsToGenerate;
		this.totalGeneratedEvents = 0;
		this.parser = new Parser();
		this.variables = getVariablesFromField(field);
		this.rules = field.getCustomBehaviour().getRules();
		this.generatedValue = null;
		this.generatedEventsInCurrentRule = 0;
		this.eventsPerSimulation = numOfEventsToGenerate / field.getCustomBehaviour().getSimulations();
	}

	public List<RuleCustomBehaviour> getRules() {
		return rules;
	}

	private Map<String, Double> getVariablesFromField(Field field) throws IOException {
		Map<String, Double> vars = new HashMap<>();

		for (VariableCustomBehaviour variable : field.getCustomBehaviour().getVariables())
			vars.put(variable.getName(), obtainVariableValue(variable, vars));
		
		return vars;
	}

	private Double obtainVariableValue(VariableCustomBehaviour variable, Map<String, Double> vars) throws IOException {

		Double result = null;
		String minStr = variable.getMin();
		String maxStr = variable.getMax();
		String valueStr = variable.getValue();

		if (minStr != null && maxStr != null) {
			Double max;
			Double min;

			ExpressionAST exp = parser.parse(minStr);
			min = exp.evaluate(vars);
			exp = parser.parse(maxStr);
			max = exp.evaluate(vars);

			result = r.doubles(min, max).findFirst().getAsDouble();
		} else if (valueStr != null) {
			ExpressionAST exp = parser.parse(valueStr);
			result = exp.evaluate(vars);
		}

		return result;
	}

	private Double obtainOperationValue(String operation) throws IOException {
		ExpressionAST exp = parser.parse(operation);
		return exp.evaluate(variables);
	}

	private void generateValueDecSequence(Double lowerBound, Double upperBound) {
		Double min = lowerBound;
		Double max = generatedValue;

		if (generatedValue == null)
			max = upperBound;

		generatedValue = min;

		if (min < max)
			generatedValue = r.doubles(min, max).findFirst().getAsDouble();
	}

	private void generateValueIncSequence(Double lowerBound, Double upperBound) {
		Double min = generatedValue;
		Double max = upperBound;
		if (generatedValue == null)
			min = lowerBound;

		generatedValue = max;

		if (min < max)
			generatedValue = r.doubles(min, max).findFirst().getAsDouble();
	}
	
	private void generateValue(RuleCustomBehaviour rule) {

		try {
			if (rule.getValue() != null)
				generatedValue = obtainOperationValue(rule.getValue());
			else {
				Double min = obtainOperationValue(rule.getMin());
				Double max = obtainOperationValue(rule.getMax());

				if (rule.getSequence() == null)
					generatedValue = (min + Math.random() * (max - min));
				else if (rule.getSequence().equalsIgnoreCase(DEC_VALUE))
					generateValueDecSequence(min, max);
				else if (rule.getSequence().equalsIgnoreCase(INC_VALUE))
					generateValueIncSequence(min, max);
			}
		} catch (IOException e) {
			logger.error(e);
		}
	}

	@Override
	public Float generate() {
		RuleCustomBehaviour rule = rules.get(0);
		
		generateValue(rule);

		generatedEventsInCurrentRule++;
		totalGeneratedEvents++;
		
		if(totalGeneratedEvents.equals(numOfEventsToGenerate))
			rules.clear();
		
		if(0 < rule.getWeight() && rule.getWeight() < 1)
		{
			Double eventsPerRule = rule.getWeight() * eventsPerSimulation;
			
			if(eventsPerRule.intValue() == generatedEventsInCurrentRule)
			{
				rules.remove(rule);
				generatedEventsInCurrentRule = 0;
			}
		}
		
		return generatedValue.floatValue();
	}
	


}
