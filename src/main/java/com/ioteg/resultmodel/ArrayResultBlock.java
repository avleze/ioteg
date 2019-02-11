package com.ioteg.resultmodel;

import java.util.List;

public class ArrayResultBlock {
	private List<ResultBlock> resultBlocks;

	public ArrayResultBlock(List<ResultBlock> resultBlocks) {
		super();
		this.resultBlocks = resultBlocks;
	}

	public List<ResultBlock> getResultBlocks() {
		return resultBlocks;
	}

	public void setResultBlocks(List<ResultBlock> resultBlocks) {
		this.resultBlocks = resultBlocks;
	}
	
	
}
