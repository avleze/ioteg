package com.ioteg.model;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class represents a set of optional fields in the data model.
 * 
 * @author Antonio Vélez Estévez
 */
public class OptionalFields {
	private Boolean mandatory;
	@Valid
	private List<Field> fields;

	/**
	 * @param mandatory
	 * @param fields
	 */
	@JsonCreator
	public OptionalFields(@JsonProperty("mandatory") Boolean mandatory,
			@Valid @JsonProperty("fields") List<Field> fields) {
		if (mandatory == null)
			mandatory = true;
		if (fields == null)
			fields = new ArrayList<>();

		this.mandatory = mandatory;
		this.fields = fields;
	}

	/**
	 * @return the mandatory
	 */
	public Boolean getMandatory() {
		return mandatory;
	}

	/**
	 * @param mandatory the mandatory to set
	 */
	public void setMandatory(Boolean mandatory) {
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
