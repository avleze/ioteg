package com.ioteg.resultmodel;

public class ResultSimpleField extends ResultField {
	protected String value;
	protected String type;
	
	public ResultSimpleField( String name, String value, String type) {
		super(name);
		this.value = value;
		this.type = type;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
}
