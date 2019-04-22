package com.ioteg.controllers.dto;

import java.util.concurrent.TimeUnit;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

public class ConfigurableEventTypeResponse {
	@NotNull(message="The id can not be null.")
	private Long id;
	
	@NotNull(message = "The eventType can not be null.")
	private EventTypeResponse eventType;
	@PositiveOrZero(message = "The delay must be positive or zero.")
	private Integer delay;
	@NotNull(message = "The period can not be null.")
	@Positive
	private Integer period;
	private TimeUnit unit;

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * @return the eventType
	 */
	public EventTypeResponse getEventType() {
		return eventType;
	}
	/**
	 * @param eventType the eventType to set
	 */
	public void setEventType(EventTypeResponse eventType) {
		this.eventType = eventType;
	}
	/**
	 * @return the delay
	 */
	public Integer getDelay() {
		return delay;
	}
	/**
	 * @param delay the delay to set
	 */
	public void setDelay(Integer delay) {
		this.delay = delay;
	}
	/**
	 * @return the period
	 */
	public Integer getPeriod() {
		return period;
	}
	/**
	 * @param period the period to set
	 */
	public void setPeriod(Integer period) {
		this.period = period;
	}
	/**
	 * @return the unit
	 */
	public TimeUnit getUnit() {
		return unit;
	}
	/**
	 * @param unit the unit to set
	 */
	public void setUnit(TimeUnit unit) {
		this.unit = unit;
	}

	
}
