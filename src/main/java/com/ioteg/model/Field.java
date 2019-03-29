
package com.ioteg.model;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class represents a field inside a block or another field in the data
 * model.
 * 
 * @author Antonio Vélez Estévez
 */
public class Field extends Attribute {
	@NotEmpty(message = "The field name can't be empty.")
	@NotNull(message = "The field name can't be null.")
	private String name;
	@NotNull(message = "The field quotes can't be null.")
	private Boolean quotes;
	private Boolean chooseone;
	private String dependence;
	@Valid
	private List<Field> fields;
	@Valid
	private List<Attribute> attributes;
	private CustomBehaviour customBehaviour;

	public Field(String name, Boolean quotes, Attribute attr) {
		super(attr.getType(), attr.getValue(), attr.getMin(), attr.getStep(), attr.getUnit(), attr.getMax(),
				attr.getPrecision(), attr.getLength(), attr.getCase(), attr.getBegin(), attr.getEnd(),
				attr.getEndcharacter(), attr.getFormat(), attr.getIsNumeric());
		this.name = name;
		this.quotes = quotes;
	}

	public Field(String name, Boolean quotes, String type) {
		super(type);
		this.name = name;
		this.quotes = quotes;
	}

	/**
	 * @param type
	 * @param value
	 * @param min
	 * @param step
	 * @param unit
	 * @param max
	 * @param precision
	 * @param length
	 * @param strCase
	 * @param begin
	 * @param end
	 * @param endcharacter
	 * @param format
	 * @param isNumeric
	 * @param name
	 * @param quotes
	 * @param chooseone
	 * @param dependence
	 * @param fields
	 * @param attributes
	 * @param customBehaviour
	 */

	@JsonCreator
	public Field(@NotEmpty @NotNull @JsonProperty("type") String type, @JsonProperty("value") String value,
			@JsonProperty("min") Double min, @JsonProperty("step") String step, @JsonProperty("unit") String unit,
			@JsonProperty("max") Double max, @JsonProperty("precision") Integer precision,
			@JsonProperty("length") Integer length, @JsonProperty("strCase") String strCase,
			@JsonProperty("begin") String begin, @JsonProperty("end") String end,
			@JsonProperty("endcharacter") String endcharacter, @JsonProperty("format") String format,
			@JsonProperty("isNumeric") Boolean isNumeric, @NotEmpty @NotNull @JsonProperty("name") String name,
			@NotNull @JsonProperty("quotes") Boolean quotes, @JsonProperty("chooseone") Boolean chooseone,
			@JsonProperty("dependence") String dependence, @Valid @JsonProperty("fields") List<Field> fields,
			@Valid @JsonProperty("attributes") List<Attribute> attributes,
			@JsonProperty("customBehaviour") CustomBehaviour customBehaviour) {
		super(type, value, min, step, unit, max, precision, length, strCase, begin, end, endcharacter, format,
				isNumeric);
		
		if(quotes == null)
			quotes = false;
		if(fields == null)
			fields = new ArrayList<>();
		if(dependence == null)
			dependence = "false";
		if(chooseone == null)
			chooseone = false;
		if(attributes == null)
			attributes = new ArrayList<>();
		
		this.name = name;
		this.quotes = quotes;
		this.chooseone = chooseone;
		this.dependence = dependence;
		this.fields = fields;
		this.attributes = attributes;
		this.customBehaviour = customBehaviour;
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
	 * @return the quotes
	 */
	public Boolean getQuotes() {
		return quotes;
	}

	/**
	 * @param quotes the quotes to set
	 */
	public void setQuotes(Boolean quotes) {
		this.quotes = quotes;
	}

	/**
	 * @return the chooseone
	 */
	public Boolean getChooseone() {
		return chooseone;
	}

	/**
	 * @param chooseone the chooseone to set
	 */
	public void setChooseone(Boolean chooseone) {
		this.chooseone = chooseone;
	}

	/**
	 * @return the dependence
	 */
	public String getDependence() {
		return dependence;
	}

	/**
	 * @param dependence the dependence to set
	 */
	public void setDependence(String dependence) {
		this.dependence = dependence;
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
	 * @return the attributes
	 */
	public List<Attribute> getAttributes() {
		return attributes;
	}

	/**
	 * @param attributes the attributes to set
	 */
	public void setAttributes(List<Attribute> attributes) {
		this.attributes = attributes;
	}

	public CustomBehaviour getCustomBehaviour() {
		return customBehaviour;
	}

	public void setCustomBehaviour(CustomBehaviour customBehaviour) {
		this.customBehaviour = customBehaviour;
	}

}
