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

import com.ioteg.controllers.dto.ChannelTypeResponse;
import com.ioteg.controllers.dto.ConfigurableEventTypeRequest;
import com.ioteg.controllers.dto.ConfigurableEventTypeResponse;
import com.ioteg.controllers.dto.mappers.ConfigurableEventTypeMapper;
import com.ioteg.model.ConfigurableEventType;
import com.ioteg.services.ChannelTypeService;
import com.ioteg.services.ConfigurableEventTypeService;
import com.ioteg.services.EntityNotFoundException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;

@RestController
@RequestMapping("/api/channels/{channelId}/configurableEventTypes")
@Api(tags="Configurable Event Types", value = "This is the REST interface to manipulate the Configurable Event Types")
public class ConfigurableEventTypeRestController {

	private ChannelTypeService channelTypeService;
	private ConfigurableEventTypeService configurableEventTypeService;
	private ConfigurableEventTypeMapper configurableEventTypeMapper;

	/**
	 * @param channelTypeService
	 * @param configurableEventTypeService
	 * @param configurableEventTypeMapper
	 */
	@Autowired
	public ConfigurableEventTypeRestController(ChannelTypeService channelTypeService,
			ConfigurableEventTypeService configurableEventTypeService,
			ConfigurableEventTypeMapper configurableEventTypeMapper) {
		super();
		this.channelTypeService = channelTypeService;
		this.configurableEventTypeService = configurableEventTypeService;
		this.configurableEventTypeMapper = configurableEventTypeMapper;
	}

	@GetMapping
	@ApiResponse(code = 200, message = "OK", responseContainer = "List", response = ChannelTypeResponse.class)
	public ResponseEntity<List<ConfigurableEventTypeResponse>> getAll(@PathVariable("channelId") Long channelId) throws EntityNotFoundException {
		List<ConfigurableEventTypeResponse> channelTypeResponse = channelTypeService.getAllConfigurableEventTypes(channelId).stream().map((configurableEvent) -> {
			return configurableEventTypeMapper.configurableEventTypeToConfigurableEventTypeResponse(configurableEvent);
		}).collect(Collectors.toList());
		
		return ResponseEntity.ok().body(channelTypeResponse);
	}
	
	@GetMapping("/{configurableEventTypeId}")
	public ResponseEntity<ConfigurableEventTypeResponse> getOne(@PathVariable("channelId") Long channelId,
			@PathVariable("configurableEventTypeId") Long configurableEventTypeId) throws EntityNotFoundException {
		return ResponseEntity.ok()
				.body(configurableEventTypeMapper.configurableEventTypeToConfigurableEventTypeResponse(configurableEventTypeService.loadById(configurableEventTypeId)));
	}

	@PostMapping
	public ResponseEntity<ConfigurableEventTypeResponse> saveOne(@PathVariable("channelId") Long channelId,
			@RequestBody @Valid ConfigurableEventTypeRequest configurableEventTypeRequest) throws EntityNotFoundException {
		ConfigurableEventType configurableEventType = configurableEventTypeMapper.configurableEventTypeRequestToConfigurableEventType(configurableEventTypeRequest);
		return ResponseEntity.ok().body(configurableEventTypeMapper.configurableEventTypeToConfigurableEventTypeResponse(configurableEventTypeService.createConfigurableEventType(channelId, configurableEventType)));
	}

	@PutMapping("/{configurableEventTypeId}")
	public ResponseEntity<ConfigurableEventTypeResponse> modifyOne(@PathVariable("channelId") Long channelId,
			@PathVariable("configurableEventTypeId") Long configurableEventTypeId, @RequestBody @Valid ConfigurableEventTypeRequest configurableEventTypeRequest) throws EntityNotFoundException
			 {
		ConfigurableEventType configurableEventType = configurableEventTypeMapper.configurableEventTypeRequestToConfigurableEventType(configurableEventTypeRequest);
		return ResponseEntity.ok().body(configurableEventTypeMapper.configurableEventTypeToConfigurableEventTypeResponse(configurableEventTypeService.modifyConfigurableEventType(configurableEventTypeId, configurableEventType)));
	}

	@DeleteMapping("/{configurableEventTypeId}")
	public ResponseEntity<Void> deleteOne(@PathVariable("channelId") Long channelId,
			@PathVariable("configurableEventTypeId") Long configurableEventTypeId) {
		configurableEventTypeService.removeConfigurableEventType(configurableEventTypeId);
		return ResponseEntity.ok().build();
	}

}
