package com.ioteg.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ioteg.controllers.dto.OptionalFieldsResponse;
import com.ioteg.controllers.dto.mappers.OptionalFieldsMapper;
import com.ioteg.services.BlockService;
import com.ioteg.services.EntityNotFoundException;
import com.ioteg.services.OptionalFieldsService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;

@RestController
@RequestMapping("/api/blocks/{blockId}/optionalFields")
@Api(tags = "OptionalFields", value = "This is the REST interface to manipulate the OptionalFields")
public class OptionalFieldsRestController {

	private BlockService blockService;
	private OptionalFieldsService optionalFieldsService;
	private OptionalFieldsMapper optionalFieldsMapper;

	/**
	 * @param blockService
	 * @param optionalFieldsService
	 * @param optionalFieldsMapper
	 */
	@Autowired
	public OptionalFieldsRestController(BlockService blockService, OptionalFieldsService optionalFieldsService,
			OptionalFieldsMapper optionalFieldsMapper) {
		super();
		this.blockService = blockService;
		this.optionalFieldsService = optionalFieldsService;
		this.optionalFieldsMapper = optionalFieldsMapper;
	}

	@GetMapping
	@ApiResponse(code = 200, message = "OK", responseContainer = "List", response = OptionalFieldsResponse.class)
	public ResponseEntity<List<OptionalFieldsResponse>> getAll(@PathVariable("blockId") Long blockId)
			throws EntityNotFoundException {
		List<OptionalFieldsResponse> response = blockService.loadByIdWithOptionalFields(blockId).getOptionalFields()
				.stream().map(optionalFields -> optionalFieldsMapper.optionalFieldsToOptionalFieldsResponse(optionalFields))
				.collect(Collectors.toList());

		return ResponseEntity.ok().body(response);
	}

}
