package com.ioteg.controllers;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ioteg.controllers.dto.RuleRequest;
import com.ioteg.controllers.dto.RuleResponse;
import com.ioteg.controllers.dto.mappers.RuleMapper;
import com.ioteg.model.RuleCustomBehaviour;
import com.ioteg.services.CustomBehaviourService;
import com.ioteg.services.EntityNotFoundException;
import com.ioteg.services.RuleCustomBehaviourService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;

@RestController
@RequestMapping("/api/customBehaviour/{customBehaviourId}/rules")
@Api(tags = "Rules Custom Behaviour", value = "This is the REST interface to manipulate the Rules in Custom Behaviour")
public class RuleCustomBehaviourRestController {
	private CustomBehaviourService customBehaviourService;
	private RuleCustomBehaviourService ruleCustomBehaviourService;
	private RuleMapper ruleMapper;

	/**
	 * @param customBehaviourService
	 * @param ruleCustomBehaviourService
	 * @param ruleMapper
	 */
	@Autowired
	public RuleCustomBehaviourRestController(CustomBehaviourService customBehaviourService,
			RuleCustomBehaviourService ruleCustomBehaviourService, RuleMapper ruleMapper) {
		super();
		this.customBehaviourService = customBehaviourService;
		this.ruleCustomBehaviourService = ruleCustomBehaviourService;
		this.ruleMapper = ruleMapper;
	}

	@GetMapping
	@ApiResponse(code = 200, message = "OK", responseContainer = "List", response = RuleResponse.class)
	public ResponseEntity<List<RuleResponse>> getAll(@PathVariable("customBehaviourId") Long customBehaviourId)
			throws EntityNotFoundException {
		List<RuleResponse> response = customBehaviourService.getAllRules(customBehaviourId).stream()
				.map(rule -> ruleMapper.ruleToRuleResponse(rule)).collect(Collectors.toList());

		return ResponseEntity.ok().body(response);
	}

	@GetMapping("/{ruleId}")
	public ResponseEntity<RuleResponse> getOne(@PathVariable("customBehaviourId") Long customBehaviourId,
			@PathVariable("ruleId") Long ruleId) throws EntityNotFoundException {
		return ResponseEntity.ok()
				.body(ruleMapper.ruleToRuleResponse(ruleCustomBehaviourService.loadById(ruleId)));
	}

	@PostMapping
	public ResponseEntity<RuleResponse> saveOne(@PathVariable("customBehaviourId") Long customBehaviourId,
			@RequestBody @Valid RuleRequest ruleRequest) throws EntityNotFoundException {
		RuleCustomBehaviour rule = ruleMapper.ruleRequestToRule(ruleRequest);
		return ResponseEntity.ok().body(ruleMapper.ruleToRuleResponse(
				ruleCustomBehaviourService.createRuleCustomBehaviour(customBehaviourId, rule)));
	}

	@PutMapping("/{ruleId}")
	public ResponseEntity<RuleResponse> modifyOne(@PathVariable("ruleId") Long ruleId,
			@RequestBody @Valid RuleRequest ruleRequest) throws EntityNotFoundException {
		RuleCustomBehaviour rule = ruleMapper.ruleRequestToRule(ruleRequest);
		return ResponseEntity.ok().body(ruleMapper.ruleToRuleResponse(
				ruleCustomBehaviourService.modifyRuleCustomBehaviour(ruleId, rule)));
	}

	@DeleteMapping("/{ruleId}")
	public ResponseEntity<Void> deleteOne(@PathVariable("customBehaviourId") Long customBehaviourId,
			@PathVariable("ruleId") Long ruleId) throws EntityNotFoundException {
		ruleCustomBehaviourService.removeRuleFromCustomBehaviour(customBehaviourId, ruleId);
		return ResponseEntity.ok().build();
	}
}
