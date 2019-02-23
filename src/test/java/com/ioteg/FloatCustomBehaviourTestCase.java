package com.ioteg;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.lessThanOrEqualTo;
import static org.hamcrest.Matchers.equalTo;

import static org.hamcrest.Matchers.allOf;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import com.ioteg.exprlang.ExprParser.ExprLangParsingException;
import com.ioteg.generators.Generable;
import com.ioteg.generators.GeneratorsFactory;
import com.ioteg.generators.exceptions.NotExistingGeneratorException;
import com.ioteg.model.CustomBehaviour;
import com.ioteg.model.Field;
import com.ioteg.model.RuleCustomBehaviour;
import com.ioteg.model.VariableCustomBehaviour;
import com.ioteg.resultmodel.ResultSimpleField;

public class FloatCustomBehaviourTestCase {

	@Test
	public void testSequenceIncreasing() throws NotExistingGeneratorException, ExprLangParsingException {

		Field field = new Field();
		field.setType("Float");

		CustomBehaviour customBehaviour = new CustomBehaviour();
		customBehaviour.setSimulations(10);
		List<VariableCustomBehaviour> variables = new ArrayList<>();
		List<RuleCustomBehaviour> rules = new ArrayList<>();

		variables.add(new VariableCustomBehaviour("var1", null, null, "156.96"));
		rules.add(new RuleCustomBehaviour(0.0, null, "$(var1)", "300", "inc"));

		customBehaviour.setVariables(variables);
		customBehaviour.setRules(rules);

		field.setCustomBehaviour(customBehaviour);

		Generable generator = GeneratorsFactory.makeGenerator(field, 100);

		Double result = null;
		for (int events = 0; events < 10; ++events) {

			List<Double> resultsOfSimulation = new LinkedList<>();

			for (int i = 0; i < 10; ++i) {
				ResultSimpleField rF = (ResultSimpleField) generator.generate(1).get(0);
				result = Double.parseDouble(rF.getValue());
				assertThat(result, allOf(greaterThanOrEqualTo(156.96), lessThanOrEqualTo(300.0)));
				resultsOfSimulation.add(result);
			}

			assertTrue(resultsOfSimulation.stream().sorted().collect(Collectors.toList()).equals(resultsOfSimulation));
		}
	}

	@Test
	public void testSequenceDecreasing() throws NotExistingGeneratorException, ExprLangParsingException {

		Field field = new Field();
		field.setType("Float");

		CustomBehaviour customBehaviour = new CustomBehaviour();
		customBehaviour.setSimulations(10);
		List<VariableCustomBehaviour> variables = new ArrayList<>();
		List<RuleCustomBehaviour> rules = new ArrayList<>();

		variables.add(new VariableCustomBehaviour("var1", null, null, "156.96"));
		rules.add(new RuleCustomBehaviour(0.0, null, "$(var1)", "300", "dec"));

		customBehaviour.setVariables(variables);
		customBehaviour.setRules(rules);

		field.setCustomBehaviour(customBehaviour);

		Generable generator = GeneratorsFactory.makeGenerator(field, 100);

		Double result = null;
		for (int events = 0; events < 10; ++events) {
			List<Double> resultsOfSimulation = new LinkedList<>();

			for (int simulation = 0; simulation < 10; ++simulation) {
				ResultSimpleField rF = (ResultSimpleField) generator.generate(1).get(0);
				result = Double.parseDouble(rF.getValue());
				resultsOfSimulation.add(result);
				assertThat(result, allOf(greaterThanOrEqualTo(156.96), lessThanOrEqualTo(300.0)));
			}
			assertTrue(resultsOfSimulation.stream().sorted(Collections.reverseOrder()).collect(Collectors.toList())
					.equals(resultsOfSimulation));
		}
	}

	@Test
	public void testRuleWithFixedVariable() throws NotExistingGeneratorException, ExprLangParsingException {

		Field field = new Field();
		field.setType("Float");

		CustomBehaviour customBehaviour = new CustomBehaviour();
		customBehaviour.setSimulations(10);
		List<VariableCustomBehaviour> variables = new ArrayList<>();
		List<RuleCustomBehaviour> rules = new ArrayList<>();

		variables.add(new VariableCustomBehaviour("var1", "0", "100", null));
		rules.add(new RuleCustomBehaviour(0.0, "$(var1)", null, null, null));

		customBehaviour.setVariables(variables);
		customBehaviour.setRules(rules);

		field.setCustomBehaviour(customBehaviour);

		Generable generator = GeneratorsFactory.makeGenerator(field, 100);

		Double result = null;
		for (int events = 0; events < 10; ++events) {
			ResultSimpleField rF = (ResultSimpleField) generator.generate(1).get(0);
			Double previous = Double.parseDouble(rF.getValue());
			for (int simulation = 1; simulation < 10; ++simulation) {
				rF = (ResultSimpleField) generator.generate(1).get(0);
				result = Double.parseDouble(rF.getValue());
				assertThat(result, equalTo(previous));
				previous = result;
			}

		}
	}

	@Test
	public void testObtainVariableValueWithMinVariableDependence()
			throws NotExistingGeneratorException, ExprLangParsingException {

		Field field = new Field();
		field.setType("Float");

		CustomBehaviour customBehaviour = new CustomBehaviour();
		customBehaviour.setSimulations(10);
		List<VariableCustomBehaviour> variables = new ArrayList<>();
		List<RuleCustomBehaviour> rules = new ArrayList<>();

		variables.add(new VariableCustomBehaviour("var1", null, null, "0"));
		variables.add(new VariableCustomBehaviour("varDependentOfVar1", "$(var1)", "100", null));

		rules.add(new RuleCustomBehaviour(0.0, null, "$(varDependentOfVar1)", "$(varDependentOfVar1)", null));

		customBehaviour.setVariables(variables);
		customBehaviour.setRules(rules);

		field.setCustomBehaviour(customBehaviour);

		Generable generator = GeneratorsFactory.makeGenerator(field, 100);

		for (int i = 0; i < 100; ++i) {
			ResultSimpleField rF = (ResultSimpleField) generator.generate(1).get(0);
			Double result = Double.parseDouble(rF.getValue());
			assertThat(result, allOf(greaterThanOrEqualTo(0.0), lessThanOrEqualTo(100.0)));
		}

	}

	@Test
	public void testObtainVariableValueWithMaxVariableDependence()
			throws NotExistingGeneratorException, ExprLangParsingException {

		Field field = new Field();
		field.setType("Float");

		CustomBehaviour customBehaviour = new CustomBehaviour();
		customBehaviour.setSimulations(10);
		List<VariableCustomBehaviour> variables = new ArrayList<>();
		List<RuleCustomBehaviour> rules = new ArrayList<>();

		variables.add(new VariableCustomBehaviour("var1", null, null, "100.0"));
		variables.add(new VariableCustomBehaviour("varDependentOfVar1", "0", "$(var1)", null));

		rules.add(new RuleCustomBehaviour(0.0, null, "$(varDependentOfVar1)", "$(varDependentOfVar1)", null));

		customBehaviour.setVariables(variables);
		customBehaviour.setRules(rules);

		field.setCustomBehaviour(customBehaviour);

		Generable generator = GeneratorsFactory.makeGenerator(field, 100);

		for (int i = 0; i < 100; ++i) {
			ResultSimpleField rF = (ResultSimpleField) generator.generate(1).get(0);
			Double result = Double.parseDouble(rF.getValue());
			assertThat(result, allOf(greaterThanOrEqualTo(0.0), lessThanOrEqualTo(100.0)));
		}

	}

	@Test
	public void testObtainVariableValueWithSum() throws NotExistingGeneratorException, ExprLangParsingException {

		Field field = new Field();
		field.setType("Float");

		CustomBehaviour customBehaviour = new CustomBehaviour();
		customBehaviour.setSimulations(1);
		List<VariableCustomBehaviour> variables = new ArrayList<>();
		List<RuleCustomBehaviour> rules = new ArrayList<>();

		variables.add(new VariableCustomBehaviour("var1", null, null, "100.0"));
		variables.add(new VariableCustomBehaviour("varDependentOfVar1", null, null, "$(var1)+1"));
		variables.add(new VariableCustomBehaviour("var2DependentOfVar1", null, null, "1+$(var1)"));

		rules.add(new RuleCustomBehaviour(0.5, "$(varDependentOfVar1)", null, null, null));
		rules.add(new RuleCustomBehaviour(0.0, "$(var2DependentOfVar1)", null, null, null));

		customBehaviour.setVariables(variables);
		customBehaviour.setRules(rules);

		field.setCustomBehaviour(customBehaviour);

		Generable generator = GeneratorsFactory.makeGenerator(field, 100);

		for (int i = 0; i < 100; ++i) {
			ResultSimpleField rF = (ResultSimpleField) generator.generate(1).get(0);
			Double result = Double.parseDouble(rF.getValue());
			assertThat(result, equalTo(101.0));
		}

	}

	@Test
	public void testObtainVariableValueWithSubstraction()
			throws NotExistingGeneratorException, ExprLangParsingException {

		Field field = new Field();
		field.setType("Float");

		CustomBehaviour customBehaviour = new CustomBehaviour();
		customBehaviour.setSimulations(1);
		List<VariableCustomBehaviour> variables = new ArrayList<>();
		List<RuleCustomBehaviour> rules = new ArrayList<>();

		variables.add(new VariableCustomBehaviour("var1", null, null, "100.0"));
		variables.add(new VariableCustomBehaviour("varDependentOfVar1", null, null, "$(var1)-1"));
		variables.add(new VariableCustomBehaviour("var2DependentOfVar1", null, null, "1-$(var1)"));

		rules.add(new RuleCustomBehaviour(0.5, "$(varDependentOfVar1)", null, null, null));
		rules.add(new RuleCustomBehaviour(0.0, "$(var2DependentOfVar1)", null, null, null));

		customBehaviour.setVariables(variables);
		customBehaviour.setRules(rules);

		field.setCustomBehaviour(customBehaviour);

		Generable generator = GeneratorsFactory.makeGenerator(field, 100);

		for (int i = 0; i < 50; ++i) {
			ResultSimpleField rF = (ResultSimpleField) generator.generate(1).get(0);
			Double result = Double.parseDouble(rF.getValue());
			assertThat(result, equalTo(99.0));
		}

		for (int i = 0; i < 50; ++i) {
			ResultSimpleField rF = (ResultSimpleField) generator.generate(1).get(0);
			Double result = Double.parseDouble(rF.getValue());
			assertThat(result, equalTo(-99.0));
		}

	}

	@Test
	public void testObtainVariableValueWithDivision() throws NotExistingGeneratorException, ExprLangParsingException {

		Field field = new Field();
		field.setType("Float");

		CustomBehaviour customBehaviour = new CustomBehaviour();
		customBehaviour.setSimulations(1);
		List<VariableCustomBehaviour> variables = new ArrayList<>();
		List<RuleCustomBehaviour> rules = new ArrayList<>();

		variables.add(new VariableCustomBehaviour("var1", null, null, "100.0"));
		variables.add(new VariableCustomBehaviour("varDependentOfVar1", null, null, "$(var1)/2"));
		variables.add(new VariableCustomBehaviour("var2DependentOfVar1", null, null, "2/$(var1)"));

		rules.add(new RuleCustomBehaviour(0.5, "$(varDependentOfVar1)", null, null, null));
		rules.add(new RuleCustomBehaviour(0.0, "$(var2DependentOfVar1)", null, null, null));

		customBehaviour.setVariables(variables);
		customBehaviour.setRules(rules);

		field.setCustomBehaviour(customBehaviour);

		Generable generator = GeneratorsFactory.makeGenerator(field, 100);

		for (int i = 0; i < 50; ++i) {
			ResultSimpleField rF = (ResultSimpleField) generator.generate(1).get(0);
			Double result = Double.parseDouble(rF.getValue());
			assertThat(result, equalTo(50.0));
		}

		for (int i = 0; i < 50; ++i) {
			ResultSimpleField rF = (ResultSimpleField) generator.generate(1).get(0);
			Double result = Double.parseDouble(rF.getValue());
			assertThat(result, equalTo(.02));
		}

	}

	@Test
	public void testObtainVariableValueWithProduct() throws NotExistingGeneratorException, ExprLangParsingException {

		Field field = new Field();
		field.setType("Float");

		CustomBehaviour customBehaviour = new CustomBehaviour();
		customBehaviour.setSimulations(1);
		List<VariableCustomBehaviour> variables = new ArrayList<>();
		List<RuleCustomBehaviour> rules = new ArrayList<>();

		variables.add(new VariableCustomBehaviour("var1", null, null, "100.0"));
		variables.add(new VariableCustomBehaviour("varDependentOfVar1", null, null, "$(var1)*2"));
		variables.add(new VariableCustomBehaviour("var2DependentOfVar1", null, null, "2*$(var1)"));

		rules.add(new RuleCustomBehaviour(0.5, "$(varDependentOfVar1)", null, null, null));
		rules.add(new RuleCustomBehaviour(0.0, "$(var2DependentOfVar1)", null, null, null));

		customBehaviour.setVariables(variables);
		customBehaviour.setRules(rules);

		field.setCustomBehaviour(customBehaviour);

		Generable generator = GeneratorsFactory.makeGenerator(field, 100);

		for (int i = 0; i < 100; ++i) {
			ResultSimpleField rF = (ResultSimpleField) generator.generate(1).get(0);
			Double result = Double.parseDouble(rF.getValue());
			assertThat(result, equalTo(200.0));
		}

	}

	@Test
	public void testRuleWithFixedValue() throws NotExistingGeneratorException, ExprLangParsingException {

		Field field = new Field();
		field.setType("Float");

		CustomBehaviour customBehaviour = new CustomBehaviour();
		customBehaviour.setSimulations(1);
		List<VariableCustomBehaviour> variables = new ArrayList<>();
		List<RuleCustomBehaviour> rules = new ArrayList<>();

		rules.add(new RuleCustomBehaviour(0.0, "50.0", null, null, null));

		customBehaviour.setVariables(variables);
		customBehaviour.setRules(rules);

		field.setCustomBehaviour(customBehaviour);

		Generable generator = GeneratorsFactory.makeGenerator(field, 100);

		for (int i = 0; i < 10; ++i) {
			ResultSimpleField rF = (ResultSimpleField) generator.generate(1).get(0);
			Double result = Double.parseDouble(rF.getValue());
			assertThat(result, equalTo(50.0));
		}

	}

	@Test
	public void testRuleWithOperationValue() throws NotExistingGeneratorException, ExprLangParsingException {

		Field field = new Field();
		field.setType("Float");

		CustomBehaviour customBehaviour = new CustomBehaviour();
		customBehaviour.setSimulations(1);
		List<VariableCustomBehaviour> variables = new ArrayList<>();
		List<RuleCustomBehaviour> rules = new ArrayList<>();

		variables.add(new VariableCustomBehaviour("var1", null, null, "100.0"));

		rules.add(new RuleCustomBehaviour(0.0, "$(var1)+1", null, null, null));

		customBehaviour.setVariables(variables);
		customBehaviour.setRules(rules);

		field.setCustomBehaviour(customBehaviour);

		Generable generator = GeneratorsFactory.makeGenerator(field, 100);
		for (int i = 0; i < 100; ++i) {
			ResultSimpleField rF = (ResultSimpleField) generator.generate(1).get(0);
			Double result = Double.parseDouble(rF.getValue());
			assertThat(result, equalTo(101.0));
		}

	}

}
