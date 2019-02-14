package com.ioteg.resultmodel;

public class ResultSimpleField extends ResultField {
	protected String value;

	public ResultSimpleField(String name, String type, Boolean quotes, String value) {
		super(name, type, quotes);
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
