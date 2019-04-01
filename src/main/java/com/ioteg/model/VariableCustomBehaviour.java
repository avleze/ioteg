package com.ioteg.model;

/**
 * <p>VariableCustomBehaviour class.</p>
 *
 * @author antonio
 * @version $Id: $Id
 */
public class VariableCustomBehaviour {
	private String name;
	private String min;
	private String max;
	private String value;
	
	/**
	 * <p>Constructor for VariableCustomBehaviour.</p>
	 *
	 * @param name a {@link java.lang.String} object.
	 * @param min a {@link java.lang.String} object.
	 * @param max a {@link java.lang.String} object.
	 * @param value a {@link java.lang.String} object.
	 */
	public VariableCustomBehaviour(String name, String min, String max, String value) {
		super();
		this.name = name;
		this.min = min;
		this.max = max;
		this.value = value;
	}
	
	/**
	 * <p>Getter for the field <code>name</code>.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getName() {
		return name;
	}
	/**
	 * <p>Setter for the field <code>name</code>.</p>
	 *
	 * @param name a {@link java.lang.String} object.
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * <p>Getter for the field <code>min</code>.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getMin() {
		return min;
	}
	/**
	 * <p>Setter for the field <code>min</code>.</p>
	 *
	 * @param min a {@link java.lang.String} object.
	 */
	public void setMin(String min) {
		this.min = min;
	}
	/**
	 * <p>Getter for the field <code>max</code>.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getMax() {
		return max;
	}
	/**
	 * <p>Setter for the field <code>max</code>.</p>
	 *
	 * @param max a {@link java.lang.String} object.
	 */
	public void setMax(String max) {
		this.max = max;
	}
	/**
	 * <p>Getter for the field <code>value</code>.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getValue() {
		return value;
	}
	/**
	 * <p>Setter for the field <code>value</code>.</p>
	 *
	 * @param value a {@link java.lang.String} object.
	 */
	public void setValue(String value) {
		this.value = value;
	}
}
