package com.ioteg.resultmodel;

public abstract class ResultField {
	protected String name;
	protected String type;
	protected Boolean quotes;
	
	public ResultField(String name, String type, Boolean quotes) {
		super();
		this.name = name;
		this.type = type;
		this.quotes = quotes;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Boolean getQuotes() {
		return quotes;
	}

	public void setQuotes(Boolean quotes) {
		this.quotes = quotes;
	}

}
