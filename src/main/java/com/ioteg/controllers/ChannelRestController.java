package com.ioteg.controllers;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ioteg.controllers.dto.ChannelTypeRequest;
import com.ioteg.controllers.dto.ChannelTypeResponse;
import com.ioteg.controllers.dto.mappers.ChannelTypeMapper;
import com.ioteg.model.ChannelType;
import com.ioteg.services.ChannelTypeService;
import com.ioteg.services.EntityNotFoundException;
import com.ioteg.services.ResourceNotFoundException;
import com.ioteg.services.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users/{userId}/channels")
@Api(tags="Channel Types", value = "This is the REST interface to manipulate Channel Types")
public class ChannelRestController {

	private UserService userService;
	private ChannelTypeService channelTypeService;
	private ChannelTypeMapper channelTypeMapper;

	/**
	 * @param userService
	 * @param channelTypeService
	 * @param channelTypeMapper
	 */
	public ChannelRestController(UserService userService, ChannelTypeService channelTypeService,
			ChannelTypeMapper channelTypeMapper) {
		super();
		this.userService = userService;
		this.channelTypeService = channelTypeService;
		this.channelTypeMapper = channelTypeMapper;
	}

	@GetMapping
	@ApiResponse(code = 200, message = "OK", responseContainer = "List", response = ChannelTypeResponse.class)
	public ResponseEntity<List<ChannelTypeResponse>> getAll(@PathVariable("userId") Long userId) {
		List<ChannelTypeResponse> channelTypeResponse = userService.getAllChannels(userId).stream().map((channelType) -> {
			return channelTypeMapper.channelTypeToChannelTypeResponse(channelType);
		}).collect(Collectors.toList());
		
		return ResponseEntity.ok().body(channelTypeResponse);
	}

	@GetMapping("/{channelId}")
	public ResponseEntity<ChannelTypeResponse> getOne(@PathVariable("userId") Long userId,
			@PathVariable("channelId") Long channelId) throws EntityNotFoundException {
		return ResponseEntity.ok()
				.body(channelTypeMapper.channelTypeToChannelTypeResponse(channelTypeService.loadById(channelId)));
	}

	@PostMapping
	public ResponseEntity<ChannelTypeResponse> saveOne(@PathVariable("userId") Long userId,
			@RequestBody @Valid ChannelTypeRequest channelRequest) {
		ChannelType channelType = channelTypeMapper.channelTypeRequestToChannelType(channelRequest);
		return ResponseEntity.ok().body(channelTypeMapper.channelTypeToChannelTypeResponse(channelTypeService.createChannel(userId, channelType)));
	}

	@PutMapping("/{channelId}")
	public ResponseEntity<ChannelTypeResponse> modifyOne(@PathVariable("userId") Long userId,
			@PathVariable("channelId") Long channelId, @RequestBody @Valid ChannelTypeRequest channelRequest) throws EntityNotFoundException
			 {
		ChannelType channelType = channelTypeMapper.channelTypeRequestToChannelType(channelRequest);
		return ResponseEntity.ok().body(channelTypeMapper.channelTypeToChannelTypeResponse(channelTypeService.modifyChannel(channelId, channelType)));
	}

	@DeleteMapping("/{channelId}")
	public ResponseEntity<Void> deleteOne(@PathVariable("userId") Long userId,
			@PathVariable("channelId") Long channelId) throws ResourceNotFoundException {
		channelTypeService.removeChannel(channelId);
		return ResponseEntity.ok().build();
	}

}
