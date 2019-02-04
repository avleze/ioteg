package com.ioteg.model;

public class VariableCustomBehaviour {
	private String name;
	private String min;
	private String max;
	private String value;
	
	public VariableCustomBehaviour(String name, String min, String max, String value) {
		super();
		this.name = name;
		this.min = min;
		this.max = max;
		this.value = value;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
}
