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

import com.ioteg.controllers.dto.FieldRequest;
import com.ioteg.controllers.dto.FieldResponse;
import com.ioteg.controllers.dto.mappers.FieldMapper;
import com.ioteg.model.Field;
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
	public ResponseEntity<List<FieldResponse>> getAllFieldsInBlock(@PathVariable("blockId") Long blockId)
			throws EntityNotFoundException {
		List<FieldResponse> response = blockService.loadByIdWithFields(blockId).getFields().stream()
				.map(field -> fieldMapper.fieldToFieldResponse(field)).collect(Collectors.toList());

		return ResponseEntity.ok().body(response);
	}

	@GetMapping("/optionalFields/{optionalFieldsId}/fields")
	@ApiResponse(code = 200, message = "OK", responseContainer = "List", response = FieldResponse.class)
	public ResponseEntity<List<FieldResponse>> getAllFieldsInOptionalFields(
			@PathVariable("optionalFieldsId") Long optionalFieldsId) throws EntityNotFoundException {
		List<FieldResponse> response = optionalFieldsService.loadByIdWithFields(optionalFieldsId).getFields().stream()
				.map(field -> fieldMapper.fieldToFieldResponse(field)).collect(Collectors.toList());

		return ResponseEntity.ok().body(response);
	}

	@GetMapping("/fields/{fieldId}/fields")
	@ApiResponse(code = 200, message = "OK", responseContainer = "List", response = FieldResponse.class)
	public ResponseEntity<List<FieldResponse>> getAllFieldsInFields(@PathVariable("fieldId") Long fieldId)
			throws EntityNotFoundException {
		List<FieldResponse> response = fieldService.loadByIdWithFields(fieldId).getFields().stream()
				.map(field -> fieldMapper.fieldToFieldResponse(field)).collect(Collectors.toList());

		return ResponseEntity.ok().body(response);
	}

	@GetMapping("/blocks/{blockId}/fields/{fieldId}")
	public ResponseEntity<FieldResponse> getOneFieldFromBlock(@PathVariable("blockId") Long blockId,
			@PathVariable("fieldId") Long fieldId) throws EntityNotFoundException {
		return ResponseEntity.ok().body(fieldMapper.fieldToFieldResponse(fieldService.loadById(fieldId)));
	}

	@GetMapping("/optionalFields/{optionalFieldsId}/fields/{fieldId}")
	public ResponseEntity<FieldResponse> getOneFieldFromOptionalFields(
			@PathVariable("optionalFieldsId") Long optionalFieldsId, @PathVariable("fieldId") Long fieldId)
			throws EntityNotFoundException {
		return ResponseEntity.ok().body(fieldMapper.fieldToFieldResponse(fieldService.loadById(fieldId)));
	}

	@GetMapping("/fields/{fieldId1}/fields/{fieldId2}")
	public ResponseEntity<FieldResponse> getOneFieldFromField(@PathVariable("fieldId1") Long fieldId1,
			@PathVariable("fieldId2") Long fieldId2) throws EntityNotFoundException {
		return ResponseEntity.ok().body(fieldMapper.fieldToFieldResponse(fieldService.loadById(fieldId2)));
	}

	@PostMapping("/blocks/{blockId}/fields")
	public ResponseEntity<FieldResponse> saveOneInBlock(@PathVariable("blockId") Long blockId,
			@RequestBody @Valid FieldRequest fieldRequest) throws EntityNotFoundException {
		Field field = fieldMapper.fieldRequestToField(fieldRequest);
		return ResponseEntity.ok().body(fieldMapper.fieldToFieldResponse(fieldService.createField(blockId, field)));
	}

	@PostMapping("/optionalFields/{optionalFieldsId}/fields")
	public ResponseEntity<FieldResponse> saveOneInOptionalFields(
			@PathVariable("optionalFieldsId") Long optionalFieldsId, @RequestBody @Valid FieldRequest fieldRequest)
			throws EntityNotFoundException {
		Field field = fieldMapper.fieldRequestToField(fieldRequest);
		return ResponseEntity.ok().body(
				fieldMapper.fieldToFieldResponse(fieldService.createFieldInOptionalFields(optionalFieldsId, field)));
	}

	@PostMapping("/fields/{fieldId}/fields")
	public ResponseEntity<FieldResponse> saveOneInField(@PathVariable("fieldId") Long fieldId,
			@RequestBody @Valid FieldRequest fieldRequest) throws EntityNotFoundException {
		Field field = fieldMapper.fieldRequestToField(fieldRequest);
		return ResponseEntity.ok().body(fieldMapper.fieldToFieldResponse(fieldService.createSubField(fieldId, field)));
	}

	@PutMapping("/blocks/{blockId}/fields/{fieldId}")
	public ResponseEntity<FieldResponse> modifyOneInBlock(@PathVariable("blockId") Long blockId,
			@PathVariable("fieldId") Long fieldId, @RequestBody @Valid FieldRequest fieldRequest)
			throws EntityNotFoundException {
		Field field = fieldMapper.fieldRequestToField(fieldRequest);
		return ResponseEntity.ok().body(fieldMapper.fieldToFieldResponse(fieldService.modifyField(fieldId, field)));
	}

	@PutMapping("/optionalFields/{optionalFieldsId}/fields/{fieldId}")
	public ResponseEntity<FieldResponse> modifyOneInOptionalField(
			@PathVariable("optionalFieldsId") Long optionalFieldsId, @PathVariable("fieldId") Long fieldId,
			@RequestBody @Valid FieldRequest fieldRequest) throws EntityNotFoundException {
		Field field = fieldMapper.fieldRequestToField(fieldRequest);
		return ResponseEntity.ok().body(fieldMapper.fieldToFieldResponse(fieldService.modifyField(fieldId, field)));
	}

	@PutMapping("/fields/{fieldId1}/fields/{fieldId2}")
	public ResponseEntity<FieldResponse> modifyOneInField(@PathVariable("fieldId1") Long fieldId1,
			@PathVariable("fieldId2") Long fieldId2, @RequestBody @Valid FieldRequest fieldRequest)
			throws EntityNotFoundException {
		Field field = fieldMapper.fieldRequestToField(fieldRequest);
		return ResponseEntity.ok().body(fieldMapper.fieldToFieldResponse(fieldService.modifyField(fieldId2, field)));
	}

	@DeleteMapping("/blocks/{blockId}/fields/{fieldId}")
	public ResponseEntity<Void> deleteOneInBlock(@PathVariable("blockId") Long blockId,
			@PathVariable("fieldId") Long fieldId) throws EntityNotFoundException {
		fieldService.removeFieldFromBlock(blockId, fieldId);
		return ResponseEntity.ok().build();
	}

	@DeleteMapping("/optionalFields/{optionalFieldsId}/fields/{fieldId}")
	public ResponseEntity<Void> deleteOneInOptionalFields(@PathVariable("optionalFieldsId") Long optionalFieldsId,
			@PathVariable("fieldId") Long fieldId) throws EntityNotFoundException {
		fieldService.removeFieldFromOptionalFields(optionalFieldsId, fieldId);
		return ResponseEntity.ok().build();
	}

	@DeleteMapping("/fields/{fieldId1}/fields/{fieldId2}")
	public ResponseEntity<Void> deleteOneInField(@PathVariable("fieldId1") Long fieldId1,
			@PathVariable("fieldId2") Long fieldId2) throws EntityNotFoundException {
		fieldService.removeFieldFromField(fieldId1, fieldId2);
		return ResponseEntity.ok().build();
	}
}
