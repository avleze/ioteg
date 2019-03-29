package com.ioteg.model;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class represents a block inside an event type in the data model.
 * 
 * @author Antonio Vélez Estévez
 */
public class Block {
	@NotEmpty
	@NotNull
	private String name;
	private String value;
	private Integer repetition;
	@Valid
	private List<Field> fields;
	@Valid
	private List<OptionalFields> optionalFields;

	/**
	 * @param name
	 * @param value
	 * @param repetition
	 * @param fields
	 * @param optionalFields
	 */
	@JsonCreator
	public Block(@NotEmpty @NotNull @JsonProperty("name") String name, @JsonProperty("value") String value,
			@JsonProperty("repetition") Integer repetition, @Valid @JsonProperty("fields") List<Field> fields,
			@Valid @JsonProperty("optionalfields") List<OptionalFields> optionalFields) {

		if (optionalFields == null)
			optionalFields = new ArrayList<>();
		if (fields == null)
			fields = new ArrayList<>();

		this.name = name;
		this.value = value;
		this.repetition = repetition;
		this.fields = fields;
		this.optionalFields = optionalFields;
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
