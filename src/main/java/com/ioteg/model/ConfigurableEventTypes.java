package com.ioteg.model;

import java.util.List;

import javax.validation.Valid;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ConfigurableEventTypes {
	
	@Valid
	private List<ConfigurableEventType> configurableEventTypes;

	public ConfigurableEventTypes(@Valid @JsonProperty("configurableEventTypes") List<ConfigurableEventType> configurableEventTypes) {
		super();
		this.configurableEventTypes = configurableEventTypes;
	}

	public List<ConfigurableEventType> getConfigurableEventTypes() {
		return configurableEventTypes;
	}

	public void setConfigurableEventTypes(List<ConfigurableEventType> configurableEventTypes) {
		this.configurableEventTypes = configurableEventTypes;
	}
	
}
