package com.ioteg.builders;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import com.ioteg.model.CustomBehaviour;
import com.ioteg.model.RuleCustomBehaviour;
import com.ioteg.model.VariableCustomBehaviour;


/**
 * @author Antonio Vélez Estévez.
 *
 */
public class CustomBehaviourBuilder {
	private static final String SIMULATIONS_ATTR = "simulations";
	private static final String CUSTOM_BEHAVIOUR_ATTR = "custom_behaviour";
	private static final String VARIABLES_TAG = "variables";
	private static final String VARIABLE_TAG = "variable";
	private static final String RULES_TAG = "rules";
	private static final String RULE_TAG = "rule";

	public CustomBehaviour build(Element fieldElement) throws JDOMException, IOException {
		CustomBehaviour customBehaviour = new CustomBehaviour();
		
		String externalFilePath = fieldElement.getAttributeValue(CUSTOM_BEHAVIOUR_ATTR);
		customBehaviour.setExternalFilePath(externalFilePath);
		
		File xmlFile = new File(externalFilePath);
		SAXBuilder builder = new SAXBuilder();
		Document customBehaviourDocument = builder.build(xmlFile);
		Element customConditions = customBehaviourDocument.getRootElement();

		buildVariables(customConditions, customBehaviour);
		buildRules(customConditions, customBehaviour);
		
		Integer simulations = Integer.valueOf(customConditions.getAttributeValue(SIMULATIONS_ATTR));
		customBehaviour.setSimulations(simulations);
		
		return customBehaviour;
	}

	/**
	 * @param customConditions
	 * @param customBehaviour
	 */
	private void buildVariables(Element customConditions, CustomBehaviour customBehaviour) {
		List<VariableCustomBehaviour> variables = new ArrayList<>();
		for (Element variablesInNode : customConditions.getChildren(VARIABLES_TAG))
			for (Element variable : variablesInNode.getChildren(VARIABLE_TAG))	
			{
				String name = variable.getAttributeValue("name");
				String min = variable.getAttributeValue("min");
				String max = variable.getAttributeValue("max");
				String value = variable.getAttributeValue("value");
				
				variables.add(new VariableCustomBehaviour(name, min, max, value));
			}
		
		customBehaviour.setVariables(variables);
	}

	/**
	 * @param customConditions
	 * @param customBehaviour
	 */
	private void buildRules(Element customConditions, CustomBehaviour customBehaviour) {
		List<RuleCustomBehaviour> rules = new ArrayList<>();
		for (Element variablesInNode : customConditions.getChildren(RULES_TAG))
			for (Element variable : variablesInNode.getChildren(RULE_TAG))	
			{
				String min = variable.getAttributeValue("min");
				String max = variable.getAttributeValue("max");
				String value = variable.getAttributeValue("value");
				String sequence = variable.getAttributeValue("sequence");
				Double weight = Double.valueOf(variable.getAttributeValue("weight"));

				rules.add(new RuleCustomBehaviour(weight, value, min, max, sequence));
			}
		
		customBehaviour.setRules(rules);
	}
}