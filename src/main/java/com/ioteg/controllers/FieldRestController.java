package com.ioteg.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ioteg.controllers.dto.FieldResponse;
import com.ioteg.controllers.dto.mappers.FieldMapper;
import com.ioteg.services.BlockService;
import com.ioteg.services.EntityNotFoundException;
import com.ioteg.services.FieldService;
import com.ioteg.services.OptionalFieldsService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;

@RestController
@RequestMapping("/api/")
@Api(tags = "Fields", value = "This is the REST interface to manipulate the Fields")
public class FieldRestController {

	private BlockService blockService;
	private OptionalFieldsService optionalFieldsService;
	private FieldService fieldService;
	private FieldMapper fieldMapper;
	/**
	 * @param blockService
	 * @param optionalFieldsService
	 * @param fieldService
	 * @param fieldMapper
	 */
	@Autowired
	public FieldRestController(BlockService blockService, OptionalFieldsService optionalFieldsService,
			FieldService fieldService, FieldMapper fieldMapper) {
		super();
		this.blockService = blockService;
		this.optionalFieldsService = optionalFieldsService;
		this.fieldService = fieldService;
		this.fieldMapper = fieldMapper;
	}

	@GetMapping("/blocks/{blockId}/fields")
	@ApiResponse(code = 200, message = "OK", responseContainer = "List", response = FieldResponse.class)
	public ResponseEntity<List<FieldResponse>> getAllFieldsInBlock(@PathVariable("blockId") Long blockId) throws EntityNotFoundException {
		List<FieldResponse> response = blockService.loadByIdWithFields(blockId).getFields().stream().map((field) -> {
			return fieldMapper.fieldToFieldResponse(field);
		}).collect(Collectors.toList());
		
		return ResponseEntity.ok().body(response);
	}
	
	@GetMapping("/optionalfields/{optionalFieldsId}/fields")
	@ApiResponse(code = 200, message = "OK", responseContainer = "List", response = FieldResponse.class)
	public ResponseEntity<List<FieldResponse>> getAllFieldsInOptionalFields(@PathVariable("optionalFieldsId") Long optionalFieldsId) throws EntityNotFoundException {
		List<FieldResponse> response = optionalFieldsService.loadByIdWithFields(optionalFieldsId).getFields().stream().map((field) -> {
			return fieldMapper.fieldToFieldResponse(field);
		}).collect(Collectors.toList());
		
		return ResponseEntity.ok().body(response);
	}
	
	@GetMapping("/fields/{fieldId}/fields")
	@ApiResponse(code = 200, message = "OK", responseContainer = "List", response = FieldResponse.class)
	public ResponseEntity<List<FieldResponse>> getAllFieldsInFields(@PathVariable("fieldId") Long fieldId) throws EntityNotFoundException {
		List<FieldResponse> response = fieldService.loadByIdWithFields(fieldId).getFields().stream().map((field) -> {
			return fieldMapper.fieldToFieldResponse(field);
		}).collect(Collectors.toList());
		
		return ResponseEntity.ok().body(response);
	}
}
