
package com.ioteg.model;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * This class represents a field inside a block or another field in the data
 * model.
 * 
 * @author Antonio Vélez Estévez
 */
public class Field extends Attribute {
	@NotEmpty
	@NotNull
	private String name;
	@NotNull
	private Boolean quotes;
	private Boolean chooseone;
	private String dependence;
	@Valid
	private List<Field> fields;
	@Valid
	private List<Attribute> attributes;
	private CustomBehaviour customBehaviour;

	public Field(String name, Boolean quotes, Attribute attr) {
		this.name = name;
		this.quotes = quotes;
		this.type = attr.getType();
		this.value = attr.getValue();
		this.min = attr.getMin();
		this.max = attr.getMax();
		this.precision = attr.getPrecision();
		this.length = attr.getLength();
		this.strCase = attr.getCase();
		this.endcharacter = attr.getEndcharacter();
		this.format = attr.getFormat();
		this.isNumeric = attr.getIsNumeric();
	}

	public Field(String name, Boolean quotes) {
		this.name = name;
		this.quotes = quotes;
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
