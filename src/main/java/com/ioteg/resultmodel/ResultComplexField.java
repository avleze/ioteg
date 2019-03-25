package com.ioteg.resultmodel;

import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ioteg.resultmodel.jsonserializers.ResultComplexFieldSerializer;

@JsonSerialize(using = ResultComplexFieldSerializer.class)
public class ResultComplexField extends ResultField {
	private List<ResultField> value;
	private Boolean isAComplexFieldFormedWithAttributes;

	public ResultComplexField(String name, String type, Boolean quotes, List<ResultField> value,
			Boolean isAComplexFieldFormedWithAttributes) {
		super(name, type, quotes);
		this.value = value;
		this.isAComplexFieldFormedWithAttributes = isAComplexFieldFormedWithAttributes;
	}

	public List<ResultField> getValue() {
		return value;
	}

	public void setValue(List<ResultField> value) {
		this.value = value;
	}

	public Boolean getIsAComplexFieldFormedWithAttributes() {
		return isAComplexFieldFormedWithAttributes;
	}

	public void setIsAComplexFieldFormedWithAttributes(Boolean isAComplexFieldFormedWithAttributes) {
		this.isAComplexFieldFormedWithAttributes = isAComplexFieldFormedWithAttributes;
	}
}
