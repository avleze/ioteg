package com.ioteg.model;

import java.util.List;

/**
 * This class represents a set of optional fields in the data model.
 * 
 * @author Antonio Vélez Estévez
 */
public class OptionalFields {
	private String mandatory;
	private List<Field> fields;

	/**
	 * @return the mandatory
	 */
	public String getMandatory() {
		return mandatory;
	}

	/**
	 * @param mandatory the mandatory to set
	 */
	public void setMandatory(String mandatory) {
		this.mandatory = mandatory;
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

}
