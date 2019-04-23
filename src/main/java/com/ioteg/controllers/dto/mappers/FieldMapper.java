package com.ioteg.controllers.dto.mappers;

import org.mapstruct.Mapper;

import com.ioteg.controllers.dto.FieldRequest;
import com.ioteg.controllers.dto.FieldResponse;
import com.ioteg.model.Field;

@Mapper(componentModel = "spring")
public interface FieldMapper {
	
	FieldResponse fieldToFieldResponse(Field field);
	Field fieldRequestToField(FieldRequest fieldRequest);
}
