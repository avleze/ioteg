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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ioteg.controllers.dto.InjectedFieldRequest;
import com.ioteg.controllers.dto.InjectedFieldResponse;
import com.ioteg.controllers.dto.mappers.InjectedFieldMapper;
import com.ioteg.model.InjectedField;
import com.ioteg.services.BlockService;
import com.ioteg.services.EntityNotFoundException;
import com.ioteg.services.InjectedFieldService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;

@RestController
@RequestMapping("/api/blocks/{blockId}/injectedFields")
@Api(tags = "Injected Fields", value = "This is the REST interface to manipulate the Injected Fields")
public class InjectedFieldRestController {

	private BlockService blockService;
	private InjectedFieldService injectedFieldService;
	private InjectedFieldMapper injectedFieldMapper;

	/**
	 * @param blockService
	 * @param injectedFieldService
	 * @param injectedFieldMapper
	 */
	@Autowired
	public InjectedFieldRestController(BlockService blockService, InjectedFieldService injectedFieldService,
			InjectedFieldMapper injectedFieldMapper) {
		super();
		this.blockService = blockService;
		this.injectedFieldService = injectedFieldService;
		this.injectedFieldMapper = injectedFieldMapper;
	}

	@GetMapping
	@ApiResponse(code = 200, message = "OK", responseContainer = "List", response = InjectedFieldResponse.class)
	public ResponseEntity<List<InjectedFieldResponse>> getAll(@PathVariable("blockId") Long blockId)
			throws EntityNotFoundException {
		List<InjectedField> response = blockService.loadByIdWithInjectedFields(blockId).getInjectedFields();
		return ResponseEntity.ok()
				.body(response.stream().map(item -> injectedFieldMapper.injectedFieldToInjectedFieldResponse(item))
						.collect(Collectors.toList()));
	}

	@PostMapping
	public ResponseEntity<InjectedFieldResponse> saveOne(@PathVariable("blockId") Long blockId,
			@RequestBody @Valid InjectedFieldRequest injectedFieldRequest) throws EntityNotFoundException {
		InjectedField injectedField = injectedFieldMapper.injectedFieldRequestToInjectedField(injectedFieldRequest);
		return ResponseEntity.ok().body(injectedFieldMapper.injectedFieldToInjectedFieldResponse(injectedFieldService.createInjectedField(blockId, injectedField)));
	}

	@DeleteMapping("/{injectedFieldId}")
	public ResponseEntity<Void> deleteOne(@PathVariable("blockId") Long blockId,
			@PathVariable("injectedFieldId") Long injectedFieldId) throws EntityNotFoundException {
		injectedFieldService.removeInjectedFieldFromBlock(blockId, injectedFieldId);
		return ResponseEntity.ok().build();
	}

}
