package com.ioteg.model;

import java.util.List;

/**
 * This class represents a block inside an event type in the data model.
 * @author Antonio Vélez Estévez
 */
public class Block {
	private String name;
	private String value;
	private Integer repetition;
	private List<Field> fields;
	private List<OptionalFields> optionalFields;
	
	public Block() {
		
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * @return the repetition
	 */
	public Integer getRepetition() {
		return repetition;
	}

	/**
	 * @param repetition the repetition to set
	 */
	public void setRepetition(Integer repetition) {
		this.repetition = repetition;
	}

	/**
	 * @return the fields
	 */
	public List<Field> getFields() {
		return fields;
	}

	/**
	 * @param fields the fields to set
	 */
	public void setFields(List<Field> fields) {
		this.fields = fields;
	}

	/**
	 * @return the optionalFields
	 */
	public List<OptionalFields> getOptionalFields() {
		return optionalFields;
	}

	/**
	 * @param optionalFields the optionalFields to set
	 */
	public void setOptionalFields(List<OptionalFields> optionalFields) {
		this.optionalFields = optionalFields;
	}
	
}
