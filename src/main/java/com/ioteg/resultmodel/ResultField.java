package com.ioteg.resultmodel;

public abstract class ResultField {
	protected String name;

	public ResultField(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
