package com.ioteg.resultmodel;

import java.util.List;

public class ResultComplexField extends ResultField {
	private List<ResultField> value;
	private Boolean isAComplexFieldFormedWithAttributes;

	public ResultComplexField(String name, List<ResultField> value, Boolean isAComplexFieldFormedWithAttributes) {
		super(name);
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
