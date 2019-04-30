package com.ioteg.controllers.dto.mappers;

import org.mapstruct.Mapper;

import com.ioteg.controllers.dto.RuleRequest;
import com.ioteg.controllers.dto.RuleResponse;
import com.ioteg.model.RuleCustomBehaviour;

@Mapper(componentModel="spring")
public interface RuleMapper {
	RuleCustomBehaviour ruleRequestToRule(RuleRequest ruleRequest);
	RuleResponse ruleToRuleResponse(RuleCustomBehaviour rule);
}
