package com.ioteg.model;

import java.util.List;

import javax.validation.Valid;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * <p>ConfigurableEventList class.</p>
 *
 * @author antonio
 * @version $Id: $Id
 */
public class ConfigurableEventList {
	
	@Valid
	private List<ConfigurableEventType> configurableEventTypes;

	/**
	 * <p>Constructor for ConfigurableEventList.</p>
	 *
	 * @param configurableEventTypes a {@link java.util.List} object.
	 */
	public ConfigurableEventList(@Valid @JsonProperty("configurableEventTypes") List<ConfigurableEventType> configurableEventTypes) {
		super();
		this.configurableEventTypes = configurableEventTypes;
	}

	/**
	 * <p>Getter for the field <code>configurableEventTypes</code>.</p>
	 *
	 * @return a {@link java.util.List} object.
	 */
	public List<ConfigurableEventType> getConfigurableEventTypes() {
		return configurableEventTypes;
	}

	/**
	 * <p>Setter for the field <code>configurableEventTypes</code>.</p>
	 *
	 * @param configurableEventTypes a {@link java.util.List} object.
	 */
	public void setConfigurableEventTypes(List<ConfigurableEventType> configurableEventTypes) {
		this.configurableEventTypes = configurableEventTypes;
	}
	
}
