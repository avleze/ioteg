package com.ioteg.generation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ioteg.exprlang.ExprParser;
import com.ioteg.exprlang.ExpressionAST;
import com.ioteg.exprlang.ExprParser.ExprLangParsingException;
import com.ioteg.model.Field;
import com.ioteg.model.RuleCustomBehaviour;
import com.ioteg.model.VariableCustomBehaviour;

/**
 * <p>CustomiseBehaviourGenerationAlgorithm class.</p>
 *
 * @author antonio
 * @version $Id: $Id
 */
public class CustomiseBehaviourGenerationAlgorithm extends GenerationAlgorithm<Float> {

	private static final String INC_VALUE = "inc";
	private static final String DEC_VALUE = "dec";
	protected Integer totalGeneratedInRule;
	protected ExprParser parser;
	protected Map<String, Double> variables;
	protected List<RuleCustomBehaviour> rules;
	protected Double generatedValue;
	protected Integer eventsPerSimulation;
	protected Integer totalGenerated;
	private int currentRule;
	
	/**
	 * <p>Constructor for CustomiseBehaviourGenerationAlgorithm.</p>
	 *
	 * @param field a {@link com.ioteg.model.Field} object.
	 * @param numOfEventsToGenerate a {@link java.lang.Integer} object.
	 * @param generationContext a {@link com.ioteg.generation.GenerationContext} object.
	 * @throws com.ioteg.exprlang.ExprParser.ExprLangParsingException if any.
	 */
	public CustomiseBehaviourGenerationAlgorithm(Field field, GenerationContext generationContext) throws ExprLangParsingException {
		super(field, generationContext);
		this.totalGeneratedInRule = 0;
		this.totalGenerated = 0;
		this.currentRule = 0;
		this.parser = new ExprParser();
		this.variables = getVariablesFromField(field);
		this.rules = field.getCustomBehaviour().getRules();
		this.generatedValue = null;
		this.eventsPerSimulation = field.getCustomBehaviour().getSimulations();
	}

	/**
	 * <p>Getter for the field <code>rules</code>.</p>
	 *
	 * @return a {@link java.util.List} object.
	 */
	public List<RuleCustomBehaviour> getRules() {
		return rules;
	}

	private Map<String, Double> getVariablesFromField(Field field) throws ExprLangParsingException {
		Map<String, Double> vars = new HashMap<>();

		for (VariableCustomBehaviour variable : field.getCustomBehaviour().getVariables())
			vars.put(variable.getName(), obtainVariableValue(variable, vars));

		return vars;
	}

	private Double obtainVariableValue(VariableCustomBehaviour variable, Map<String, Double> vars) throws ExprLangParsingException {

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

	private Double obtainOperationValue(String operation) throws ExprLangParsingException {
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

	private void generateValue(RuleCustomBehaviour rule) throws ExprLangParsingException {

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

	}

	private void updateIndex() throws ExprLangParsingException {
		final double ruleWeight = rules.get(currentRule).getWeight();
		
		if(ruleWeight == 0 && totalGenerated.equals(eventsPerSimulation))
		{
			currentRule = 0;
			totalGeneratedInRule = 0;
			totalGenerated = 0;
			variables = getVariablesFromField(field);
			generatedValue = null;
		}
		
		if(ruleWeight != 0 && (eventsPerSimulation * ruleWeight) <= totalGeneratedInRule)
		{
			currentRule = (currentRule + 1) % rules.size();
			totalGeneratedInRule = 0;
		}
	}
	
	/** {@inheritDoc} */
	@Override
	public Float generate() throws ExprLangParsingException {
		updateIndex();
		RuleCustomBehaviour rule = rules.get(currentRule);

		generateValue(rule);
		totalGeneratedInRule++;
		totalGenerated++;
		
		return generatedValue.floatValue();
	}

}
