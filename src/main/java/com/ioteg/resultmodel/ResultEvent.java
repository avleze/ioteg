package com.ioteg.resultmodel;

import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ioteg.model.EventType;
import com.ioteg.serializers.json.ResultEventSerializer;

/**
 * <p>ResultEvent class.</p>
 *
 * @author antonio
 * @version $Id: $Id
 */
@JsonSerialize(using = ResultEventSerializer.class)
public class ResultEvent {
	private String name;
	private List<ArrayResultBlock> arrayResultBlocks;
	private EventType model;
	
	/**
	 * <p>Constructor for ResultEvent.</p>
	 *
	 * @param name a {@link java.lang.String} object.
	 * @param arrayResultBlocks a {@link java.util.List} object.
	 */
	public ResultEvent(String name, List<ArrayResultBlock> arrayResultBlocks) {
		super();
		this.name = name;
		this.arrayResultBlocks = arrayResultBlocks;
	}

	/**
	 * <p>Getter for the field <code>name</code>.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getName() {
		return name;
	}

	/**
	 * <p>Setter for the field <code>name</code>.</p>
	 *
	 * @param name a {@link java.lang.String} object.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * <p>Getter for the field <code>arrayResultBlocks</code>.</p>
	 *
	 * @return a {@link java.util.List} object.
	 */
	public List<ArrayResultBlock> getArrayResultBlocks() {
		return arrayResultBlocks;
	}

	/**
	 * <p>Setter for the field <code>arrayResultBlocks</code>.</p>
	 *
	 * @param arrayResultBlocks a {@link java.util.List} object.
	 */
	public void setArrayResultBlocks(List<ArrayResultBlock> arrayResultBlocks) {
		this.arrayResultBlocks = arrayResultBlocks;
	}

	/**
	 * <p>Getter for the field <code>model</code>.</p>
	 *
	 * @return a {@link com.ioteg.model.EventType} object.
	 */
	public EventType getModel() {
		return model;
	}

	/**
	 * <p>Setter for the field <code>model</code>.</p>
	 *
	 * @param model a {@link com.ioteg.model.EventType} object.
	 */
	public void setModel(EventType model) {
		this.model = model;
	}

}
