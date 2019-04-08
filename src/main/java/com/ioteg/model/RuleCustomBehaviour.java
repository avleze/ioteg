package com.ioteg.model;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * <p>
 * RuleCustomBehaviour class.
 * </p>
 *
 * @author antonio
 * @version $Id: $Id
 */

@Entity
public class RuleCustomBehaviour {
	@Id
	private UUID id;
	private Double weight;
	private String value;
	private String min;
	private String max;
	private String sequence;

	@SuppressWarnings("unused")
	private RuleCustomBehaviour() {

	}

	/**
	 * <p>
	 * Constructor for RuleCustomBehaviour.
	 * </p>
	 * 
	 * @param id       a {@link java.lang.Long} object.
	 * @param weight   a {@link java.lang.Double} object.
	 * @param value    a {@link java.lang.String} object.
	 * @param min      a {@link java.lang.String} object.
	 * @param max      a {@link java.lang.String} object.
	 * @param sequence a {@link java.lang.String} object.
	 */
	@JsonCreator
	public RuleCustomBehaviour(@JsonProperty("id") UUID id, @JsonProperty("weight") Double weight,
			@JsonProperty("value") String value, @JsonProperty("min") String min, @JsonProperty("max") String max,
			@JsonProperty("sequence") String sequence) {
		super();
		if(id == null)
			id = UUID.randomUUID();
		this.id = id;
		this.weight = weight;
		this.value = value;
		this.min = min;
		this.max = max;
		this.sequence = sequence;
	}

	/**
	 * <p>
	 * Getter for the field <code>weight</code>.
	 * </p>
	 *
	 * @return a {@link java.lang.Double} object.
	 */
	public Double getWeight() {
		return weight;
	}

	/**
	 * <p>
	 * Setter for the field <code>weight</code>.
	 * </p>
	 *
	 * @param weight a {@link java.lang.Double} object.
	 */
	public void setWeight(Double weight) {
		this.weight = weight;
	}

	/**
	 * <p>
	 * Getter for the field <code>value</code>.
	 * </p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getValue() {
		return value;
	}

	/**
	 * <p>
	 * Setter for the field <code>value</code>.
	 * </p>
	 *
	 * @param value a {@link java.lang.String} object.
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * <p>
	 * Getter for the field <code>min</code>.
	 * </p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getMin() {
		return min;
	}

	/**
	 * <p>
	 * Setter for the field <code>min</code>.
	 * </p>
	 *
	 * @param min a {@link java.lang.String} object.
	 */
	public void setMin(String min) {
		this.min = min;
	}

	/**
	 * <p>
	 * Getter for the field <code>max</code>.
	 * </p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getMax() {
		return max;
	}

	/**
	 * <p>
	 * Setter for the field <code>max</code>.
	 * </p>
	 *
	 * @param max a {@link java.lang.String} object.
	 */
	public void setMax(String max) {
		this.max = max;
	}

	/**
	 * <p>
	 * Getter for the field <code>sequence</code>.
	 * </p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getSequence() {
		return sequence;
	}

	/**
	 * <p>
	 * Setter for the field <code>sequence</code>.
	 * </p>
	 *
	 * @param sequence a {@link java.lang.String} object.
	 */
	public void setSequence(String sequence) {
		this.sequence = sequence;
	}

}
