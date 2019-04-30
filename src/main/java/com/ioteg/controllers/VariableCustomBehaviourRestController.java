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

import com.ioteg.controllers.dto.VariableRequest;
import com.ioteg.controllers.dto.VariableResponse;
import com.ioteg.controllers.dto.mappers.VariableMapper;
import com.ioteg.model.VariableCustomBehaviour;
import com.ioteg.services.CustomBehaviourService;
import com.ioteg.services.EntityNotFoundException;
import com.ioteg.services.VariableCustomBehaviourService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;

@RestController
@RequestMapping("/api/customBehaviour/{customBehaviourId}/variables")
@Api(tags = "Variable Custom Behaviour", value = "This is the REST interface to manipulate the Variables in Custom Behaviour")
public class VariableCustomBehaviourRestController {
	private CustomBehaviourService customBehaviourService;
	private VariableCustomBehaviourService variableCustomBehaviourService;
	private VariableMapper variableMapper;

	/**
	 * @param customBehaviourService
	 * @param variableCustomBehaviourService
	 * @param variableMapper
	 */
	@Autowired
	public VariableCustomBehaviourRestController(CustomBehaviourService customBehaviourService,
			VariableCustomBehaviourService variableCustomBehaviourService, VariableMapper variableMapper) {
		super();
		this.customBehaviourService = customBehaviourService;
		this.variableCustomBehaviourService = variableCustomBehaviourService;
		this.variableMapper = variableMapper;
	}

	@GetMapping
	@ApiResponse(code = 200, message = "OK", responseContainer = "List", response = VariableResponse.class)
	public ResponseEntity<List<VariableResponse>> getAll(@PathVariable("customBehaviourId") Long customBehaviourId) {
		List<VariableResponse> response = customBehaviourService.getAllVariables(customBehaviourId).stream()
				.map(variable -> variableMapper.variableToVariableResponse(variable)).collect(Collectors.toList());

		return ResponseEntity.ok().body(response);
	}

	@GetMapping("/{variableId}")
	public ResponseEntity<VariableResponse> getOne(@PathVariable("customBehaviourId") Long customBehaviourId,
			@PathVariable("variableId") Long variableId) throws EntityNotFoundException {
		return ResponseEntity.ok()
				.body(variableMapper.variableToVariableResponse(variableCustomBehaviourService.loadById(variableId)));
	}

	@PostMapping
	public ResponseEntity<VariableResponse> saveOne(@PathVariable("customBehaviourId") Long customBehaviourId,
			@RequestBody @Valid VariableRequest variableRequest) throws EntityNotFoundException {
		VariableCustomBehaviour variable = variableMapper.variableRequestToVariable(variableRequest);
		return ResponseEntity.ok().body(variableMapper.variableToVariableResponse(
				variableCustomBehaviourService.createVariableCustomBehaviour(customBehaviourId, variable)));
	}

	@PutMapping("/{variableId}")
	public ResponseEntity<VariableResponse> modifyOne(@PathVariable("variableId") Long variableId,
			@RequestBody @Valid VariableRequest variableRequest) throws EntityNotFoundException {
		VariableCustomBehaviour variable = variableMapper.variableRequestToVariable(variableRequest);
		return ResponseEntity.ok().body(variableMapper.variableToVariableResponse(
				variableCustomBehaviourService.modifyVariableCustomBehaviour(variableId, variable)));
	}

	@DeleteMapping("/{variableId}")
	public ResponseEntity<Void> deleteOne(@PathVariable("customBehaviourId") Long customBehaviourId,
			@PathVariable("variableId") Long variableId) throws EntityNotFoundException {
		variableCustomBehaviourService.removeVariableFromCustomBehaviour(customBehaviourId, variableId);
		return ResponseEntity.ok().build();
	}
}
