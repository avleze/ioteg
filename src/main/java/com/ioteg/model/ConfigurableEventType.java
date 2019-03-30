package com.ioteg.model;

import java.util.concurrent.TimeUnit;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ConfigurableEventType {

	@NotNull
	@Valid
	private EventType eventType;
	@PositiveOrZero	
	private Integer delay;
	@NotNull
	@Positive
	private Integer period;
	private TimeUnit unit;

	public ConfigurableEventType(@NotNull @JsonProperty("eventtype") EventType eventType,
			@JsonProperty("delay") @PositiveOrZero Integer delay, @NotNull @Positive @JsonProperty("period") Integer period,
			@JsonProperty("unit") TimeUnit unit) {
		super();
		if (unit == null)
			unit = TimeUnit.SECONDS;
		if (delay == null)
			delay = 0;

		this.eventType = eventType;
		this.delay = delay;
		this.period = period;
		this.unit = unit;
	}

	public EventType getEventType() {
		return eventType;
	}

	public void setEventType(EventType eventType) {
		this.eventType = eventType;
	}

	public Integer getDelay() {
		return delay;
	}

	public void setDelay(Integer delay) {
		this.delay = delay;
	}

	public Integer getPeriod() {
		return period;
	}

	public void setPeriod(Integer period) {
		this.period = period;
	}

	public TimeUnit getUnit() {
		return unit;
	}

	public void setUnit(TimeUnit unit) {
		this.unit = unit;
	}

}
