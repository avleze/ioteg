package com.ioteg.controllers.dto.mappers;

import java.util.stream.Collectors;

import org.mapstruct.Mapper;

import com.ioteg.controllers.dto.OptionalFieldsRequest;
import com.ioteg.controllers.dto.OptionalFieldsResponse;
import com.ioteg.model.OptionalFields;

@Mapper(componentModel = "spring")
public interface OptionalFieldsMapper {
	OptionalFields optionalFieldsRequestToOptionalFields(OptionalFieldsRequest optionalFieldsRequest);

	default OptionalFieldsResponse optionalFieldsToOptionalFieldsResponse(OptionalFields optionalFields) {
		OptionalFieldsResponse optionalFieldsResponse = new OptionalFieldsResponse();
		optionalFieldsResponse.setFields(
				optionalFields.getFields().stream().map(field -> field.getName()).collect(Collectors.toList()));

		optionalFieldsResponse.setId(optionalFields.getId());
		optionalFieldsResponse.setMandatory(optionalFields.getMandatory());
		return optionalFieldsResponse;
	}
}
