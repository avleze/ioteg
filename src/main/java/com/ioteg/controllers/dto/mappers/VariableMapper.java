package com.ioteg.controllers.dto.mappers;

import org.mapstruct.Mapper;

import com.ioteg.controllers.dto.VariableRequest;
import com.ioteg.controllers.dto.VariableResponse;
import com.ioteg.model.VariableCustomBehaviour;

@Mapper(componentModel="spring")
public interface VariableMapper {
	VariableCustomBehaviour variableRequestToVariable(VariableRequest variableRequest);
	VariableResponse variableToVariableResponse(VariableCustomBehaviour variable);
}
