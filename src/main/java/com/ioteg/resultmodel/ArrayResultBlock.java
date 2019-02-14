package com.ioteg.resultmodel;

import java.util.List;

public class ArrayResultBlock {
	private List<ResultBlock> resultBlocks;
	private Boolean hasRepetitionTag;

	/**
	 * @param resultBlocks
	 * @param hasRepetitionTag
	 */
	public ArrayResultBlock(List<ResultBlock> resultBlocks, Boolean hasRepetitionTag) {
		super();
		this.resultBlocks = resultBlocks;
		this.hasRepetitionTag = hasRepetitionTag;
	}

	public List<ResultBlock> getResultBlocks() {
		return resultBlocks;
	}

	public void setResultBlocks(List<ResultBlock> resultBlocks) {
		this.resultBlocks = resultBlocks;
	}

	/**
	 * @return the hasRepetitionTag
	 */
	public Boolean getHasRepetitionTag() {
		return hasRepetitionTag;
	}

	/**
	 * @param hasRepetitionTag the hasRepetitionTag to set
	 */
	public void setHasRepetitionTag(Boolean hasRepetitionTag) {
		this.hasRepetitionTag = hasRepetitionTag;
	}

}
