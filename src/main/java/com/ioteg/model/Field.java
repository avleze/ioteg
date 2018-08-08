package com.ioteg.model;

import java.util.List;

public class Field {
	private String name;
	private String quotes;
	private String type;
	private String value;
	private String min;
	private String max;
	private String precision;
	private String length;
	private String cas;
	private String endcharacter;
	private String format;
	private String isNumeric;
	private String chooseone;
	private String dependence;
	
	private List<Field> fields;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getQuotes() {
		return quotes;
	}

	public void setQuotes(String quotes) {
		this.quotes = quotes;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getMin() {
		return min;
	}

	public void setMin(String min) {
		this.min = min;
	}

	public String getMax() {
		return max;
	}

	public void setMax(String max) {
		this.max = max;
	}

	public String getPrecision() {
		return precision;
	}

	public void setPrecision(String precision) {
		this.precision = precision;
	}

	public String getLength() {
		return length;
	}

	public void setLength(String length) {
		this.length = length;
	}

	public String getCas() {
		return cas;
	}

	public void setCas(String cas) {
		this.cas = cas;
	}

	public String getEndcharacter() {
		return endcharacter;
	}

	public void setEndcharacter(String endcharacter) {
		this.endcharacter = endcharacter;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public String getIsNumeric() {
		return isNumeric;
	}

	public void setIsNumeric(String isNumeric) {
		this.isNumeric = isNumeric;
	}

	public String getChooseone() {
		return chooseone;
	}

	public void setChooseone(String chooseone) {
		this.chooseone = chooseone;
	}

	public String getDependence() {
		return dependence;
	}

	public void setDependence(String dependence) {
		this.dependence = dependence;
	}

	public List<Field> getFields() {
		return fields;
	}

	public void setFields(List<Field> fields) {
		this.fields = fields;
	}
	
	
}
