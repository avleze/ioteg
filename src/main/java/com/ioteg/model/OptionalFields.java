package com.ioteg.model;

import java.util.List;

public class OptionalFields {
	private String mandatory;
	private List<Field> fields;
	
	public String getMandatory() {
		return mandatory;
	}
	public void setMandatory(String mandatory) {
		this.mandatory = mandatory;
	}
	public List<Field> getFields() {
		return fields;
	}
	public void setFields(List<Field> fields) {
		this.fields = fields;
	}
	
	
}
