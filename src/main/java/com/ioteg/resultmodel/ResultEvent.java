package com.ioteg.resultmodel;

import java.util.List;

public class ResultEvent {
	private String name;
	private List<ArrayResultBlock> arrayResultBlocks;

	public ResultEvent(String name, List<ArrayResultBlock> arrayResultBlocks) {
		super();
		this.name = name;
		this.arrayResultBlocks = arrayResultBlocks;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<ArrayResultBlock> getArrayResultBlocks() {
		return arrayResultBlocks;
	}

	public void setArrayResultBlocks(List<ArrayResultBlock> arrayResultBlocks) {
		this.arrayResultBlocks = arrayResultBlocks;
	}

}
