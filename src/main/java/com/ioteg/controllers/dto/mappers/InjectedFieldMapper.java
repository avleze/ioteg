package com.ioteg.controllers.dto.mappers;

import org.mapstruct.Mapper;

import com.ioteg.controllers.dto.InjectedFieldRequest;
import com.ioteg.controllers.dto.InjectedFieldResponse;
import com.ioteg.model.InjectedField;

@Mapper(componentModel = "spring")
public interface InjectedFieldMapper {
	
	InjectedField injectedFieldRequestToInjectedField(InjectedFieldRequest injectedFieldRequest);
	InjectedFieldResponse injectedFieldToInjectedFieldResponse(InjectedField injectedField);
}
