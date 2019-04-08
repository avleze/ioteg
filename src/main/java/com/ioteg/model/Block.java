package com.ioteg.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class represents a block inside an event type in the data model.
 *
 * @author Antonio Vélez Estévez
 * @version $Id: $Id
 */

@Entity
public class Block {
	@Id
	private UUID id;

	@NotEmpty
	@NotNull
	private String name;
	private String value;
	private Integer repetition;
	@Valid
	@OneToMany(cascade = CascadeType.ALL)
	private List<Field> fields;
	@Valid
	@OneToMany(cascade = CascadeType.ALL)
	private List<InjectedField> injectedFields;
	@Valid
	@OneToMany(cascade = CascadeType.ALL)
	private List<OptionalFields> optionalFields;

	@SuppressWarnings("unused")
	private Block() {

	}

	/**
	 * <p>
	 * Constructor for Block.
	 * </p>
	 * 
	 * @param id             a {@link java.lang.Long} object.
	 * @param name           a {@link java.lang.String} object.
	 * @param value          a {@link java.lang.String} object.
	 * @param repetition     a {@link java.lang.Integer} object.
	 * @param fields         a {@link java.util.List} object.
	 * @param optionalFields a {@link java.util.List} object.
	 * @param injectedFields a {@link java.util.List} object.
	 */
	@JsonCreator
	public Block(@JsonProperty("id") UUID id, @NotEmpty @NotNull @JsonProperty("name") String name,
			@JsonProperty("value") String value, @JsonProperty("repetition") Integer repetition,
			@Valid @JsonProperty("fields") List<Field> fields,
			@Valid @JsonProperty("injectedfields") List<InjectedField> injectedFields,
			@Valid @JsonProperty("optionalfields") List<OptionalFields> optionalFields) {
		if(id == null)
			id = UUID.randomUUID();
		if (injectedFields == null)
			injectedFields = new ArrayList<>();
		if (optionalFields == null)
			optionalFields = new ArrayList<>();
		if (fields == null)
			fields = new ArrayList<>();

		this.id = id;
		this.name = name;
		this.value = value;
		this.repetition = repetition;
		this.fields = fields;
		this.injectedFields = injectedFields;
		this.optionalFields = optionalFields;
	}

	/**
	 * @return the id
	 */
	public UUID getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(UUID id) {
		this.id = id;
	}

	/**
	 * <p>
	 * Getter for the field <code>name</code>.
	 * </p>
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * <p>
	 * Setter for the field <code>name</code>.
	 * </p>
	 *
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * <p>
	 * Getter for the field <code>value</code>.
	 * </p>
	 *
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * <p>
	 * Setter for the field <code>value</code>.
	 * </p>
	 *
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * <p>
	 * Getter for the field <code>repetition</code>.
	 * </p>
	 *
	 * @return the repetition
	 */
	public Integer getRepetition() {
		return repetition;
	}

	/**
	 * <p>
	 * Setter for the field <code>repetition</code>.
	 * </p>
	 *
	 * @param repetition the repetition to set
	 */
	public void setRepetition(Integer repetition) {
		this.repetition = repetition;
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

	/**
	 * <p>
	 * Getter for the field <code>injectedFields</code>.
	 * </p>
	 *
	 * @return the injectedFields
	 */
	public List<InjectedField> getInjectedFields() {
		return injectedFields;
	}

	/**
	 * <p>
	 * Setter for the field <code>injectedFields</code>.
	 * </p>
	 *
	 * @param injectedFields the injectedFields to set
	 */
	public void setInjectedFields(List<InjectedField> injectedFields) {
		this.injectedFields = injectedFields;
	}

	/**
	 * <p>
	 * Getter for the field <code>optionalFields</code>.
	 * </p>
	 *
	 * @return the optionalFields
	 */
	public List<OptionalFields> getOptionalFields() {
		return optionalFields;
	}

	/**
	 * <p>
	 * Setter for the field <code>optionalFields</code>.
	 * </p>
	 *
	 * @param optionalFields the optionalFields to set
	 */
	public void setOptionalFields(List<OptionalFields> optionalFields) {
		this.optionalFields = optionalFields;
	}

}
