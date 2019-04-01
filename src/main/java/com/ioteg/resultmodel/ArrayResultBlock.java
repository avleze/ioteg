package com.ioteg.resultmodel;

import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ioteg.resultmodel.jsonserializers.ArrayResultBlockSerializer;

/**
 * <p>ArrayResultBlock class.</p>
 *
 * @author antonio
 * @version $Id: $Id
 */
@JsonSerialize(using = ArrayResultBlockSerializer.class)
public class ArrayResultBlock {
	private List<ResultBlock> resultBlocks;
	private Boolean hasRepetitionTag;

	/**
	 * <p>Constructor for ArrayResultBlock.</p>
	 *
	 * @param resultBlocks a {@link java.util.List} object.
	 * @param hasRepetitionTag a {@link java.lang.Boolean} object.
	 */
	public ArrayResultBlock(List<ResultBlock> resultBlocks, Boolean hasRepetitionTag) {
		super();
		this.resultBlocks = resultBlocks;
		this.hasRepetitionTag = hasRepetitionTag;
	}

	/**
	 * <p>Getter for the field <code>resultBlocks</code>.</p>
	 *
	 * @return a {@link java.util.List} object.
	 */
	public List<ResultBlock> getResultBlocks() {
		return resultBlocks;
	}

	/**
	 * <p>Setter for the field <code>resultBlocks</code>.</p>
	 *
	 * @param resultBlocks a {@link java.util.List} object.
	 */
	public void setResultBlocks(List<ResultBlock> resultBlocks) {
		this.resultBlocks = resultBlocks;
	}

	/**
	 * <p>Getter for the field <code>hasRepetitionTag</code>.</p>
	 *
	 * @return the hasRepetitionTag
	 */
	public Boolean getHasRepetitionTag() {
		return hasRepetitionTag;
	}

	/**
	 * <p>Setter for the field <code>hasRepetitionTag</code>.</p>
	 *
	 * @param hasRepetitionTag the hasRepetitionTag to set
	 */
	public void setHasRepetitionTag(Boolean hasRepetitionTag) {
		this.hasRepetitionTag = hasRepetitionTag;
	}

}
