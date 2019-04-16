
package com.ioteg.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
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
 * @version $Id: $Id
 */

@Entity
public class Field extends Attribute {
	@NotEmpty(message = "The field name can't be empty.")
	@NotNull(message = "The field name can't be null.")
	private String name;
	private Boolean quotes;
	private Boolean chooseone;
	private String dependence;
	private Boolean injectable;
	@Valid
	@OneToMany(cascade = CascadeType.ALL)
	private List<Field> fields;
	@Valid
	@OneToMany(cascade = CascadeType.ALL)
	private List<Attribute> attributes;
	@OneToOne(cascade = CascadeType.ALL)
	private CustomBehaviour customBehaviour;

	@SuppressWarnings("unused")
	private Field() {

	}
	
	/**
	 * <p>
	 * Constructor for Field.
	 * </p>
	 *
	 * @param name   a {@link java.lang.String} object.
	 * @param quotes a {@link java.lang.Boolean} object.
	 * @param attr   a {@link com.ioteg.model.Attribute} object.
	 */
	public Field(String name, Boolean quotes, Attribute attr) {
		super(null, attr.getType(), attr.getValue(), attr.getMin(), attr.getStep(), attr.getUnit(), attr.getMax(),
				attr.getPrecision(), attr.getLength(), attr.getCase(), attr.getBegin(), attr.getEnd(),
				attr.getEndcharacter(), attr.getFormat(), attr.getIsNumeric());
		this.name = name;
		this.quotes = quotes;
		this.injectable = false;
	}

	/**
	 * <p>
	 * Constructor for Field.
	 * </p>
	 *
	 * @param name   a {@link java.lang.String} object.
	 * @param quotes a {@link java.lang.Boolean} object.
	 * @param type   a {@link java.lang.String} object.
	 */
	public Field(String name, Boolean quotes, String type) {
		super(type);
		this.name = name;
		this.quotes = quotes;
		this.injectable = false;
	}

	/**
	 * <p>
	 * Constructor for Field.
	 * </p>
	 * 
	 * @param id			  a {@link java.lang.Long} object.
	 * @param type            a {@link java.lang.String} object.
	 * @param value           a {@link java.lang.String} object.
	 * @param min             a {@link java.lang.Double} object.
	 * @param step            a {@link java.lang.String} object.
	 * @param unit            a {@link java.lang.String} object.
	 * @param max             a {@link java.lang.Double} object.
	 * @param precision       a {@link java.lang.Integer} object.
	 * @param length          a {@link java.lang.Integer} object.
	 * @param strCase         a {@link java.lang.String} object.
	 * @param begin           a {@link java.lang.String} object.
	 * @param end             a {@link java.lang.String} object.
	 * @param endcharacter    a {@link java.lang.String} object.
	 * @param format          a {@link java.lang.String} object.
	 * @param isNumeric       a {@link java.lang.Boolean} object.
	 * @param name            a {@link java.lang.String} object.
	 * @param quotes          a {@link java.lang.Boolean} object.
	 * @param chooseone       a {@link java.lang.Boolean} object.
	 * @param dependence      a {@link java.lang.String} object.
	 * @param fields          a {@link java.util.List} object.
	 * @param attributes      a {@link java.util.List} object.
	 * @param customBehaviour a {@link com.ioteg.model.CustomBehaviour} object.
	 * @param injectable      a {@link java.lang.Boolean} object.
	 */

	@JsonCreator
	public Field(@JsonProperty("id") UUID id, @NotEmpty @NotNull @JsonProperty("type") String type, @JsonProperty("value") String value,
			@JsonProperty("min") Double min, @JsonProperty("step") String step, @JsonProperty("unit") String unit,
			@JsonProperty("max") Double max, @JsonProperty("precision") Integer precision,
			@JsonProperty("length") Integer length, @JsonProperty("strCase") String strCase,
			@JsonProperty("begin") String begin, @JsonProperty("end") String end,
			@JsonProperty("endcharacter") String endcharacter, @JsonProperty("format") String format,
			@JsonProperty("isNumeric") Boolean isNumeric, @NotEmpty @NotNull @JsonProperty("name") String name,
			@JsonProperty("quotes") Boolean quotes, @JsonProperty("chooseone") Boolean chooseone,
			@JsonProperty("dependence") String dependence, @JsonProperty("injectable") Boolean injectable,
			@Valid @JsonProperty("fields") List<Field> fields,
			@Valid @JsonProperty("attributes") List<Attribute> attributes,
			@JsonProperty("customBehaviour") CustomBehaviour customBehaviour) {
		super(id, type, value, min, step, unit, max, precision, length, strCase, begin, end, endcharacter, format,
				isNumeric);
		if (quotes == null)
			quotes = false;
		if (fields == null)
			fields = new ArrayList<>();
		if (dependence == null)
			dependence = "false";
		if (injectable == null)
			injectable = false;
		if (chooseone == null)
			chooseone = false;
		if (attributes == null)
			attributes = new ArrayList<>();

		this.name = name;
		this.quotes = quotes;
		this.chooseone = chooseone;
		this.dependence = dependence;
		this.injectable = injectable;
		this.fields = fields;
		this.attributes = attributes;
		this.customBehaviour = customBehaviour;
	}
	
	
	
	
	/**
	 * @param id
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
	 * @param injectable
	 * @param fields
	 * @param attributes
	 * @param customBehaviour
	 */
	public Field(@NotEmpty @NotNull String type, String value, Double min, String step, String unit,
			Double max, Integer precision, Integer length, String strCase, String begin, String end,
			String endcharacter, String format, Boolean isNumeric,
			@NotEmpty(message = "The field name can't be empty.") @NotNull(message = "The field name can't be null.") String name,
			Boolean quotes, Boolean chooseone, String dependence, Boolean injectable, @Valid List<Field> fields,
			@Valid List<Attribute> attributes, CustomBehaviour customBehaviour) {
		this(null, type, value, min, step, unit, max, precision, length, strCase, begin, end, endcharacter, format,
				isNumeric, name, quotes, chooseone, dependence, injectable, fields, attributes, customBehaviour);
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
	 * Getter for the field <code>quotes</code>.
	 * </p>
	 *
	 * @return the quotes
	 */
	public Boolean getQuotes() {
		return quotes;
	}

	/**
	 * <p>
	 * Setter for the field <code>quotes</code>.
	 * </p>
	 *
	 * @param quotes the quotes to set
	 */
	public void setQuotes(Boolean quotes) {
		this.quotes = quotes;
	}

	/**
	 * <p>
	 * Getter for the field <code>chooseone</code>.
	 * </p>
	 *
	 * @return the chooseone
	 */
	public Boolean getChooseone() {
		return chooseone;
	}

	/**
	 * <p>
	 * Setter for the field <code>chooseone</code>.
	 * </p>
	 *
	 * @param chooseone the chooseone to set
	 */
	public void setChooseone(Boolean chooseone) {
		this.chooseone = chooseone;
	}

	/**
	 * <p>
	 * Getter for the field <code>dependence</code>.
	 * </p>
	 *
	 * @return the dependence
	 */
	public String getDependence() {
		return dependence;
	}

	/**
	 * <p>
	 * Setter for the field <code>dependence</code>.
	 * </p>
	 *
	 * @param dependence the dependence to set
	 */
	public void setDependence(String dependence) {
		this.dependence = dependence;
	}

	/**
	 * <p>
	 * Getter for the field <code>injectable</code>.
	 * </p>
	 *
	 * @return the injectable
	 */
	public Boolean getInjectable() {
		return injectable;
	}

	/**
	 * <p>
	 * Setter for the field <code>injectable</code>.
	 * </p>
	 *
	 * @param injectable the injectable to set
	 */
	public void setInjectable(Boolean injectable) {
		this.injectable = injectable;
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
	 * Getter for the field <code>attributes</code>.
	 * </p>
	 *
	 * @return the attributes
	 */
	public List<Attribute> getAttributes() {
		return attributes;
	}

	/**
	 * <p>
	 * Setter for the field <code>attributes</code>.
	 * </p>
	 *
	 * @param attributes the attributes to set
	 */
	public void setAttributes(List<Attribute> attributes) {
		this.attributes = attributes;
	}

	/**
	 * <p>
	 * Getter for the field <code>customBehaviour</code>.
	 * </p>
	 *
	 * @return a {@link com.ioteg.model.CustomBehaviour} object.
	 */
	public CustomBehaviour getCustomBehaviour() {
		return customBehaviour;
	}

	/**
	 * <p>
	 * Setter for the field <code>customBehaviour</code>.
	 * </p>
	 *
	 * @param customBehaviour a {@link com.ioteg.model.CustomBehaviour} object.
	 */
	public void setCustomBehaviour(CustomBehaviour customBehaviour) {
		this.customBehaviour = customBehaviour;
	}
	

}
