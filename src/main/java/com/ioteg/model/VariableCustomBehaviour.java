package com.ioteg.model;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * <p>
 * VariableCustomBehaviour class.
 * </p>
 *
 * @author antonio
 * @version $Id: $Id
 */

@Entity
public class VariableCustomBehaviour {
	@Id
	private UUID id;
	private String name;
	private String min;
	private String max;
	private String value;

	@SuppressWarnings("unused")
	private VariableCustomBehaviour() {
		
	}
	
	/**
	 * <p>
	 * Constructor for VariableCustomBehaviour.
	 * </p>
	 * 
	 * @param id    a {@link java.lang.Long} object.
	 * @param name  a {@link java.lang.String} object.
	 * @param min   a {@link java.lang.String} object.
	 * @param max   a {@link java.lang.String} object.
	 * @param value a {@link java.lang.String} object.
	 */
	public VariableCustomBehaviour(@JsonProperty("id") UUID id, @JsonProperty("name") String name,
			@JsonProperty("min") String min, @JsonProperty("max") String max, @JsonProperty("value") String value) {
		super();
		if(id == null)
			id = UUID.randomUUID();
		this.id = id;
		this.name = name;
		this.min = min;
		this.max = max;
		this.value = value;
	}
	
	

	/**
	 * @param name
	 * @param min
	 * @param max
	 * @param value
	 */
	public VariableCustomBehaviour(String name, String min, String max, String value) {
		this(null, name, min, max, value);
	}

	/**
	 * <p>
	 * Getter for the field <code>name</code>.
	 * </p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getName() {
		return name;
	}

	/**
	 * <p>
	 * Setter for the field <code>name</code>.
	 * </p>
	 *
	 * @param name a {@link java.lang.String} object.
	 */
	public void setName(String name) {
		this.name = name;
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
}
