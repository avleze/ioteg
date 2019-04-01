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
 * @version $Id: $Id
 */
public class OptionalFields {
	private Boolean mandatory;
	@Valid
	private List<Field> fields;

	/**
	 * <p>Constructor for OptionalFields.</p>
	 *
	 * @param mandatory a {@link java.lang.Boolean} object.
	 * @param fields a {@link java.util.List} object.
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
	 * <p>Getter for the field <code>mandatory</code>.</p>
	 *
	 * @return the mandatory
	 */
	public Boolean getMandatory() {
		return mandatory;
	}

	/**
	 * <p>Setter for the field <code>mandatory</code>.</p>
	 *
	 * @param mandatory the mandatory to set
	 */
	public void setMandatory(Boolean mandatory) {
		this.mandatory = mandatory;
	}

	/**
	 * <p>Getter for the field <code>fields</code>.</p>
	 *
	 * @return the fields
	 */
	public List<Field> getFields() {
		return fields;
	}

	/**
	 * <p>Setter for the field <code>fields</code>.</p>
	 *
	 * @param fields the fields to set
	 */
	public void setFields(List<Field> fields) {
		this.fields = fields;
	}

}
