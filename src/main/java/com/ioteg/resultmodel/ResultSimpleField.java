package com.ioteg.resultmodel;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ioteg.resultmodel.jsonserializers.ResultSimpleFieldSerializer;

@JsonSerialize(using = ResultSimpleFieldSerializer.class)
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
