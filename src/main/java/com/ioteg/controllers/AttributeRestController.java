package com.ioteg.controllers;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ioteg.controllers.dto.AttributeRequest;
import com.ioteg.controllers.dto.AttributeResponse;
import com.ioteg.controllers.dto.mappers.AttributeMapper;
import com.ioteg.controllers.dto.validation.AttributeRequestValidator;
import com.ioteg.model.Attribute;
import com.ioteg.services.AttributeService;
import com.ioteg.services.EntityNotFoundException;
import com.ioteg.services.FieldService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;

@RestController
@RequestMapping("/api/fields/{fieldId}/attributes")
@Api(tags = "Attributes", value = "This is the REST interface to manipulate the Attributes")
public class AttributeRestController {

	private FieldService fieldService;
	private AttributeService attributeService;
	private AttributeMapper attributeMapper;

	/**
	 * @param fieldService
	 * @param attributeService
	 * @param attributeMapper
	 */
	@Autowired
	public AttributeRestController(FieldService fieldService, AttributeService attributeService,
			AttributeMapper attributeMapper) {
		super();
		this.fieldService = fieldService;
		this.attributeService = attributeService;
		this.attributeMapper = attributeMapper;
	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
	    binder.addValidators(new AttributeRequestValidator());
	}

	@GetMapping
	@ApiResponse(code = 200, message = "OK", responseContainer = "List", response = AttributeResponse.class)
	public ResponseEntity<List<AttributeResponse>> getAll(@PathVariable("fieldId") Long fieldId) {
		List<AttributeResponse> response = fieldService.getAllAttributes(fieldId).stream()
				.map(attribute -> attributeMapper.attributeToAttributeResponse(attribute)).collect(Collectors.toList());

		return ResponseEntity.ok().body(response);
	}

	@GetMapping("/{attributeId}")
	public ResponseEntity<AttributeResponse> getOne(@PathVariable("fieldId") Long fieldId,
			@PathVariable("attributeId") Long attributeId) throws EntityNotFoundException {
		return ResponseEntity.ok()
				.body(attributeMapper.attributeToAttributeResponse(attributeService.loadById(attributeId)));
	}

	@PostMapping
	public ResponseEntity<AttributeResponse> saveOne(@PathVariable("fieldId") Long fieldId,
			@RequestBody @Valid AttributeRequest attributeRequest) throws EntityNotFoundException {
		Attribute attribute = attributeMapper.attributeRequestToAttribute(attributeRequest);
		return ResponseEntity.ok().body(
				attributeMapper.attributeToAttributeResponse(attributeService.createAttribute(fieldId, attribute)));
	}

	@PutMapping("/{attributeId}")
	public ResponseEntity<AttributeResponse> modifyOne(@PathVariable("attributeId") Long attributeId,
			@RequestBody @Valid AttributeRequest attributeRequest) throws EntityNotFoundException {
		Attribute attribute = attributeMapper.attributeRequestToAttribute(attributeRequest);
		return ResponseEntity.ok().body(
				attributeMapper.attributeToAttributeResponse(attributeService.modifyAttribute(attributeId, attribute)));
	}

	@DeleteMapping("/{attributeId}")
	public ResponseEntity<Void> deleteOne(@PathVariable("fieldId") Long fieldId,
			@PathVariable("attributeId") Long attributeId) throws EntityNotFoundException {
		attributeService.removeAttributeFromField(fieldId, attributeId);
		return ResponseEntity.ok().build();
	}
}
