package com.ioteg.resultmodel;

import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ioteg.model.EventType;
import com.ioteg.resultmodel.jsonserializers.ResultEventSerializer;

@JsonSerialize(using = ResultEventSerializer.class)
public class ResultEvent {
	private String name;
	private List<ArrayResultBlock> arrayResultBlocks;
	private EventType model;
	
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

	public EventType getModel() {
		return model;
	}

	public void setModel(EventType model) {
		this.model = model;
	}

}
