package com.ioteg.resultmodel;

import java.util.List;

public class ResultBlock {
	private String name;
	private List<ResultField> resultFields;

	public ResultBlock(String name, List<ResultField> resultFields) {
		super();
		this.name = name;
		this.resultFields = resultFields;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<ResultField> getResultFields() {
		return resultFields;
	}

	public void setResultFields(List<ResultField> resultFields) {
		this.resultFields = resultFields;
	}

}
