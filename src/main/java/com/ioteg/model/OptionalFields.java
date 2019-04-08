package com.ioteg.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.Valid;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class represents a set of optional fields in the data model.
 *
 * @author Antonio Vélez Estévez
 * @version $Id: $Id
 */

@Entity
public class OptionalFields {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Boolean mandatory;
	@Valid
	@OneToMany(cascade = CascadeType.ALL)
	private List<Field> fields;

	@SuppressWarnings("unused")
	private OptionalFields() {
		
	}

	/**
	 * <p>
	 * Constructor for OptionalFields.
	 * </p>
	 *
	 * @param id        a {@link java.lang.Long} object.
	 * @param mandatory a {@link java.lang.Boolean} object.
	 * @param fields    a {@link java.util.List} object.
	 */
	@JsonCreator
	public OptionalFields(@JsonProperty("id") Long id, @JsonProperty("mandatory") Boolean mandatory,
			@Valid @JsonProperty("fields") List<Field> fields) {
		if (mandatory == null)
			mandatory = true;
		if (fields == null)
			fields = new ArrayList<>();

		this.id = id;
		this.mandatory = mandatory;
		this.fields = fields;
	}
	
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * <p>
	 * Getter for the field <code>mandatory</code>.
	 * </p>
	 *
	 * @return the mandatory
	 */
	public Boolean getMandatory() {
		return mandatory;
	}

	/**
	 * <p>
	 * Setter for the field <code>mandatory</code>.
	 * </p>
	 *
	 * @param mandatory the mandatory to set
	 */
	public void setMandatory(Boolean mandatory) {
		this.mandatory = mandatory;
	}

	/**
	 * <p>
	 * Getter for the field <code>fields</code>.
	 * </p>
	 *
	 * @return the fields
	 */
	public List<Field> getFields() {
		return fields;
	}

	/**
	 * <p>
	 * Setter for the field <code>fields</code>.
	 * </p>
	 *
	 * @param fields the fields to set
	 */
	public void setFields(List<Field> fields) {
		this.fields = fields;
	}

}
