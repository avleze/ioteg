package com.ioteg.model;

import java.util.List;

public class Block {
	private String name;
	private String value;
	private Integer repetition;
	private List<Field> fields;
	private List<OptionalFields> optionalFields;
	
	public Block() {
		
	}
	
	public Block(String name, String value, Integer repetition, List<Field> fields,
			List<OptionalFields> optionalFields) {
		super();
		this.name = name;
		this.value = value;
		this.repetition = repetition;
		this.fields = fields;
		this.optionalFields = optionalFields;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public Integer getRepetition() {
		return repetition;
	}
	public void setRepetition(Integer repetition) {
		this.repetition = repetition;
	}
	public List<Field> getFields() {
		return fields;
	}
	public void setFields(List<Field> fields) {
		this.fields = fields;
	}
	public List<OptionalFields> getOptionalFields() {
		return optionalFields;
	}
	public void setOptionalFields(List<OptionalFields> optionalFields) {
		this.optionalFields = optionalFields;
	}
	
	
	
}
