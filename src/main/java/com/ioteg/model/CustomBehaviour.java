package com.ioteg.model;

import java.util.List;

public class CustomBehaviour {
	private String externalFilePath;
	private Integer simulations;
	private List<VariableCustomBehaviour> variables;
	private List<RuleCustomBehaviour> rules;
	
	
	public CustomBehaviour() {
		
	}
	public CustomBehaviour(String externalFilePath, Integer simulations, List<VariableCustomBehaviour> variables,
			List<RuleCustomBehaviour> rules) {
		super();
		this.externalFilePath = externalFilePath;
		this.simulations = simulations;
		this.variables = variables;
		this.rules = rules;
	}
	
	public String getExternalFilePath() {
		return externalFilePath;
	}
	public void setExternalFilePath(String externalFilePath) {
		this.externalFilePath = externalFilePath;
	}
	public Integer getSimulations() {
		return simulations;
	}
	public void setSimulations(Integer simulations) {
		this.simulations = simulations;
	}
	public List<VariableCustomBehaviour> getVariables() {
		return variables;
	}
	public void setVariables(List<VariableCustomBehaviour> variables) {
		this.variables = variables;
	}
	public List<RuleCustomBehaviour> getRules() {
		return rules;
	}
	public void setRules(List<RuleCustomBehaviour> rules) {
		this.rules = rules;
	}
}
