package com.ioteg.model;

import java.util.List;

/**
 * <p>CustomBehaviour class.</p>
 *
 * @author antonio
 * @version $Id: $Id
 */
public class CustomBehaviour {
	private String externalFilePath;
	private Integer simulations;
	private List<VariableCustomBehaviour> variables;
	private List<RuleCustomBehaviour> rules;
	
	
	/**
	 * <p>Constructor for CustomBehaviour.</p>
	 */
	public CustomBehaviour() {
		
	}
	/**
	 * <p>Constructor for CustomBehaviour.</p>
	 *
	 * @param externalFilePath a {@link java.lang.String} object.
	 * @param simulations a {@link java.lang.Integer} object.
	 * @param variables a {@link java.util.List} object.
	 * @param rules a {@link java.util.List} object.
	 */
	public CustomBehaviour(String externalFilePath, Integer simulations, List<VariableCustomBehaviour> variables,
			List<RuleCustomBehaviour> rules) {
		super();
		this.externalFilePath = externalFilePath;
		this.simulations = simulations;
		this.variables = variables;
		this.rules = rules;
	}
	
	/**
	 * <p>Getter for the field <code>externalFilePath</code>.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getExternalFilePath() {
		return externalFilePath;
	}
	/**
	 * <p>Setter for the field <code>externalFilePath</code>.</p>
	 *
	 * @param externalFilePath a {@link java.lang.String} object.
	 */
	public void setExternalFilePath(String externalFilePath) {
		this.externalFilePath = externalFilePath;
	}
	/**
	 * <p>Getter for the field <code>simulations</code>.</p>
	 *
	 * @return a {@link java.lang.Integer} object.
	 */
	public Integer getSimulations() {
		return simulations;
	}
	/**
	 * <p>Setter for the field <code>simulations</code>.</p>
	 *
	 * @param simulations a {@link java.lang.Integer} object.
	 */
	public void setSimulations(Integer simulations) {
		this.simulations = simulations;
	}
	/**
	 * <p>Getter for the field <code>variables</code>.</p>
	 *
	 * @return a {@link java.util.List} object.
	 */
	public List<VariableCustomBehaviour> getVariables() {
		return variables;
	}
	/**
	 * <p>Setter for the field <code>variables</code>.</p>
	 *
	 * @param variables a {@link java.util.List} object.
	 */
	public void setVariables(List<VariableCustomBehaviour> variables) {
		this.variables = variables;
	}
	/**
	 * <p>Getter for the field <code>rules</code>.</p>
	 *
	 * @return a {@link java.util.List} object.
	 */
	public List<RuleCustomBehaviour> getRules() {
		return rules;
	}
	/**
	 * <p>Setter for the field <code>rules</code>.</p>
	 *
	 * @param rules a {@link java.util.List} object.
	 */
	public void setRules(List<RuleCustomBehaviour> rules) {
		this.rules = rules;
	}
}
