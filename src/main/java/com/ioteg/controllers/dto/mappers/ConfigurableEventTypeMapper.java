package com.ioteg.controllers.dto.mappers;

import org.mapstruct.Mapper;

import com.ioteg.controllers.dto.ConfigurableEventTypeRequest;
import com.ioteg.controllers.dto.ConfigurableEventTypeResponse;
import com.ioteg.model.ConfigurableEventType;

@Mapper(componentModel = "spring")
public interface ConfigurableEventTypeMapper {
	
	ConfigurableEventTypeResponse configurableEventTypeToConfigurableEventTypeResponse(ConfigurableEventType configurableEventType);
	ConfigurableEventType configurableEventTypeRequestToConfigurableEventType(ConfigurableEventTypeRequest configurableEventTypeRequest);

}
