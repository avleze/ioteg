package com.ioteg.resultmodel;

public class ResultSimpleField extends ResultField {
	protected String value;

	public ResultSimpleField(String name, String value) {
		super(name);
		this.name = name;
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
