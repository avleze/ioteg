package com.ioteg.controllers.dto.mappers;

import org.mapstruct.Mapper;

import com.ioteg.controllers.dto.OptionalFieldsRequest;
import com.ioteg.controllers.dto.OptionalFieldsResponse;
import com.ioteg.model.Field;
import com.ioteg.model.OptionalFields;

@Mapper(componentModel = "spring")
public interface OptionalFieldsMapper {
	OptionalFields optionalFieldsRequestToOptionalFields(OptionalFieldsRequest optionalFieldsRequest);

	default OptionalFieldsResponse optionalFieldsToOptionalFieldsResponse(OptionalFields optionalFields) {
		OptionalFieldsResponse optionalFieldsResponse = new OptionalFieldsResponse();
		StringBuilder sB = new StringBuilder();
		for(Field field : optionalFields.getFields())
		{
			sB.append(field.getName());
			sB.append(", ");
		}
		
		if(!optionalFields.getFields().isEmpty())
			sB.setLength(sB.length() - 2);
		
		optionalFieldsResponse.setFields(sB.toString());
		optionalFieldsResponse.setId(optionalFields.getId());
		optionalFieldsResponse.setMandatory(optionalFields.getMandatory());
		return optionalFieldsResponse;
	}
	
}
