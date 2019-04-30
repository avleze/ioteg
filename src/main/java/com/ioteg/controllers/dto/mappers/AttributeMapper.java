package com.ioteg.controllers.dto.mappers;

import org.mapstruct.Mapper;

import com.ioteg.controllers.dto.AttributeRequest;
import com.ioteg.controllers.dto.AttributeResponse;
import com.ioteg.model.Attribute;

@Mapper(componentModel = "spring")
public interface AttributeMapper {
	AttributeResponse attributeToAttributeResponse(Attribute attribute);
	Attribute attributeRequestToAttribute(AttributeRequest attributeRequest);
}
