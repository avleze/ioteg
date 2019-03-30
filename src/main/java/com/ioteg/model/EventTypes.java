package com.ioteg.model;

import java.util.List;

import javax.validation.Valid;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EventTypes {
	@Valid
	private List<EventType> eventTypes;

	public EventTypes(@Valid @JsonProperty("eventTypes") List<EventType> eventTypes) {
		super();
		this.eventTypes = eventTypes;
	}

	public List<EventType> getEventTypes() {
		return eventTypes;
	}

	public void setEventTypes(List<EventType> eventTypes) {
		this.eventTypes = eventTypes;
	}
}
