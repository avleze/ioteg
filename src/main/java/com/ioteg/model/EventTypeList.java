package com.ioteg.model;

import java.util.List;

import javax.validation.Valid;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * <p>EventTypeList class.</p>
 *
 * @author antonio
 * @version $Id: $Id
 */
public class EventTypeList {
	@Valid
	private List<EventType> eventTypes;

	/**
	 * <p>Constructor for EventTypeList.</p>
	 *
	 * @param eventTypes a {@link java.util.List} object.
	 */
	public EventTypeList(@Valid @JsonProperty("eventTypes") List<EventType> eventTypes) {
		super();
		this.eventTypes = eventTypes;
	}

	/**
	 * <p>Getter for the field <code>eventTypes</code>.</p>
	 *
	 * @return a {@link java.util.List} object.
	 */
	public List<EventType> getEventTypes() {
		return eventTypes;
	}

	/**
	 * <p>Setter for the field <code>eventTypes</code>.</p>
	 *
	 * @param eventTypes a {@link java.util.List} object.
	 */
	public void setEventTypes(List<EventType> eventTypes) {
		this.eventTypes = eventTypes;
	}
}
