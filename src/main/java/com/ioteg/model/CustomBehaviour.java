package com.ioteg.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * <p>
 * CustomBehaviour class.
 * </p>
 *
 * @author antonio
 * @version $Id: $Id
 */

@Entity
public class CustomBehaviour {
	@Id
	private UUID id;
	private String externalFilePath;
	private Integer simulations;
	@OneToMany(cascade = CascadeType.ALL)
	private List<VariableCustomBehaviour> variables;
	@OneToMany(cascade = CascadeType.ALL)
	private List<RuleCustomBehaviour> rules;

	@SuppressWarnings("unused")
	private CustomBehaviour() {
		
	}

	/**
	 * <p>
	 * Constructor for CustomBehaviour.
	 * </p>
	 * 
	 * @param id               a {@link java.lang.Long} object.
	 * @param externalFilePath a {@link java.lang.String} object.
	 * @param simulations      a {@link java.lang.Integer} object.
	 * @param variables        a {@link java.util.List} object.
	 * @param rules            a {@link java.util.List} object.
	 */
	@JsonCreator
	public CustomBehaviour(@JsonProperty("id") UUID id, @JsonProperty("externalFilePath") String externalFilePath,
			@JsonProperty("simulations") Integer simulations,
			@JsonProperty("variables") List<VariableCustomBehaviour> variables,
			@JsonProperty("rules") List<RuleCustomBehaviour> rules) {
		super();
		if(id == null)
			id = UUID.randomUUID();
		if (variables == null)
			variables = new ArrayList<>();
		if (rules == null)
			rules = new ArrayList<>();

		this.id = id;
		this.externalFilePath = externalFilePath;
		this.simulations = simulations;
		this.variables = variables;
		this.rules = rules;
	}

	/**
	 * <p>
	 * Getter for the field <code>externalFilePath</code>.
	 * </p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getExternalFilePath() {
		return externalFilePath;
	}

	/**
	 * <p>
	 * Setter for the field <code>externalFilePath</code>.
	 * </p>
	 *
	 * @param externalFilePath a {@link java.lang.String} object.
	 */
	public void setExternalFilePath(String externalFilePath) {
		this.externalFilePath = externalFilePath;
	}

	/**
	 * <p>
	 * Getter for the field <code>simulations</code>.
	 * </p>
	 *
	 * @return a {@link java.lang.Integer} object.
	 */
	public Integer getSimulations() {
		return simulations;
	}

	/**
	 * <p>
	 * Setter for the field <code>simulations</code>.
	 * </p>
	 *
	 * @param simulations a {@link java.lang.Integer} object.
	 */
	public void setSimulations(Integer simulations) {
		this.simulations = simulations;
	}

	/**
	 * <p>
	 * Getter for the field <code>variables</code>.
	 * </p>
	 *
	 * @return a {@link java.util.List} object.
	 */
	public List<VariableCustomBehaviour> getVariables() {
		return variables;
	}

	/**
	 * <p>
	 * Setter for the field <code>variables</code>.
	 * </p>
	 *
	 * @param variables a {@link java.util.List} object.
	 */
	public void setVariables(List<VariableCustomBehaviour> variables) {
		this.variables = variables;
	}

	/**
	 * <p>
	 * Getter for the field <code>rules</code>.
	 * </p>
	 *
	 * @return a {@link java.util.List} object.
	 */
	public List<RuleCustomBehaviour> getRules() {
		return rules;
	}

	/**
	 * <p>
	 * Setter for the field <code>rules</code>.
	 * </p>
	 *
	 * @param rules a {@link java.util.List} object.
	 */
	public void setRules(List<RuleCustomBehaviour> rules) {
		this.rules = rules;
	}
}
