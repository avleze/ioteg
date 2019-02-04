package com.ioteg.model;

public class RuleCustomBehaviour {
	private Double weight;
	private String value;
	private String min;
	private String max;
	private String sequence;

	public RuleCustomBehaviour(Double weight, String value, String min, String max, String sequence) {
		super();
		this.weight = weight;
		this.value = value;
		this.min = min;
		this.max = max;
		this.sequence = sequence;
	}
	
	public Double getWeight() {
		return weight;
	}
	public void setWeight(Double weight) {
		this.weight = weight;
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
	public String getSequence() {
		return sequence;
	}
	public void setSequence(String sequence) {
		this.sequence = sequence;
	}
	
	
}
