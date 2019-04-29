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

import com.ioteg.controllers.dto.BlockRequest;
import com.ioteg.controllers.dto.BlockResponse;
import com.ioteg.controllers.dto.mappers.BlockMapper;
import com.ioteg.model.Block;
import com.ioteg.services.BlockService;
import com.ioteg.services.EntityNotFoundException;
import com.ioteg.services.EventTypeService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;

@RestController
@RequestMapping("/api/events/{eventTypeId}/blocks")
@Api(tags = "Blocks", value = "This is the REST interface to manipulate the Blocks")
public class BlockRestController {

	private EventTypeService eventTypeService;
	private BlockService blockService;
	private BlockMapper blockMapper;

	/**
	 * @param eventTypeService
	 * @param blockService
	 * @param blockMapper
	 */
	@Autowired
	public BlockRestController(EventTypeService eventTypeService, BlockService blockService, BlockMapper blockMapper) {
		super();
		this.eventTypeService = eventTypeService;
		this.blockService = blockService;
		this.blockMapper = blockMapper;
	}

	@GetMapping
	@ApiResponse(code = 200, message = "OK", responseContainer = "List", response = BlockResponse.class)
	public ResponseEntity<List<BlockResponse>> getAll(@PathVariable("eventTypeId") Long eventTypeId)
			throws EntityNotFoundException {
		List<BlockResponse> response = eventTypeService.getAllBlocks(eventTypeId).stream().map(eventType -> {
			return blockMapper.blockToBlockResponse(eventType);
		}).collect(Collectors.toList());

		return ResponseEntity.ok().body(response);
	}

	@GetMapping("/{blockId}")
	public ResponseEntity<BlockResponse> getOne(@PathVariable("eventTypeId") Long eventTypeId,
			@PathVariable("blockId") Long blockId) throws EntityNotFoundException {
		return ResponseEntity.ok().body(blockMapper.blockToBlockResponse(blockService.loadById(blockId)));
	}

	@PostMapping
	public ResponseEntity<BlockResponse> saveOne(@PathVariable("eventTypeId") Long eventTypeId,
			@RequestBody @Valid BlockRequest blockRequest)
			throws EntityNotFoundException {
		Block block = blockMapper.blockRequestToBlock(blockRequest);
		return ResponseEntity.ok()
				.body(blockMapper.blockToBlockResponse(blockService.createBlock(eventTypeId, block)));
	}

	@PutMapping("/{blockId}")
	public ResponseEntity<BlockResponse> modifyOne(@PathVariable("blockId") Long blockId,
			@RequestBody @Valid BlockRequest blockRequest)
			throws EntityNotFoundException {
		Block block = blockMapper.blockRequestToBlock(blockRequest);
		return ResponseEntity.ok()
				.body(blockMapper.blockToBlockResponse(blockService.modifyBlock(blockId, block)));
	}

	@DeleteMapping("/{blockId}")
	public ResponseEntity<Void> deleteOne(@PathVariable("eventTypeId") Long eventTypeId,
			@PathVariable("blockId") Long blockId) throws EntityNotFoundException {
		blockService.removeBlockFromEventType(eventTypeId, blockId);
		return ResponseEntity.ok().build();
	}
}
