

package com.ioteg.model;

import java.util.List;

/**
 * This class represents a field inside a block or another field in the data model.
 * @author Antonio Vélez Estévez
 */
public class Field {
	private String name;
	private String quotes;
	private String type;
	private String value;
	private String min;
	private String max;
	private String precision;
	private String length;
	private String case_;
	private String endcharacter;
	private String format;
	private String isNumeric;
	private String chooseone;
	private String dependence;
	
	private List<Field> fields;
	private List<Attribute> attributes;
	
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
	public String getQuotes() {
		return quotes;
	}
	/**
	 * @param quotes the quotes to set
	 */
	public void setQuotes(String quotes) {
		this.quotes = quotes;
	}
	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
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
	 * @return the min
	 */
	public String getMin() {
		return min;
	}
	/**
	 * @param min the min to set
	 */
	public void setMin(String min) {
		this.min = min;
	}
	/**
	 * @return the max
	 */
	public String getMax() {
		return max;
	}
	/**
	 * @param max the max to set
	 */
	public void setMax(String max) {
		this.max = max;
	}
	/**
	 * @return the precision
	 */
	public String getPrecision() {
		return precision;
	}
	/**
	 * @param precision the precision to set
	 */
	public void setPrecision(String precision) {
		this.precision = precision;
	}
	/**
	 * @return the length
	 */
	public String getLength() {
		return length;
	}
	/**
	 * @param length the length to set
	 */
	public void setLength(String length) {
		this.length = length;
	}
	/**
	 * @return the case
	 */
	public String getCase() {
		return case_;
	}
	/**
	 * @param case_ the case to set
	 */
	public void setCase(String case_) {
		this.case_ = case_;
	}
	/**
	 * @return the endcharacter
	 */
	public String getEndcharacter() {
		return endcharacter;
	}
	/**
	 * @param endcharacter the endcharacter to set
	 */
	public void setEndcharacter(String endcharacter) {
		this.endcharacter = endcharacter;
	}
	/**
	 * @return the format
	 */
	public String getFormat() {
		return format;
	}
	/**
	 * @param format the format to set
	 */
	public void setFormat(String format) {
		this.format = format;
	}
	/**
	 * @return the isNumeric
	 */
	public String getIsNumeric() {
		return isNumeric;
	}
	/**
	 * @param isNumeric the isNumeric to set
	 */
	public void setIsNumeric(String isNumeric) {
		this.isNumeric = isNumeric;
	}
	/**
	 * @return the chooseone
	 */
	public String getChooseone() {
		return chooseone;
	}
	/**
	 * @param chooseone the chooseone to set
	 */
	public void setChooseone(String chooseone) {
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
	
}
