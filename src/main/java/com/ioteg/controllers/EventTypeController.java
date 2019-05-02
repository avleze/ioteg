package com.ioteg.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ioteg.controllers.dto.ConfigurableEventTypeResponse;
import com.ioteg.controllers.dto.mappers.ConfigurableEventTypeMapper;
import com.ioteg.repositories.ConfigurableEventTypeRepository;
import io.swagger.annotations.Api;

@RestController
@RequestMapping("/api/search/events")
@Api(tags = "Event Types", value = "This is the REST interface to search Event Types")
public class EventTypeController {
	@Autowired
	private ConfigurableEventTypeRepository configurableEventTypeRepository;
	@Autowired
	private ConfigurableEventTypeMapper mapper;

	@GetMapping
	public List<ConfigurableEventTypeResponse> getPublicEventsByName(@RequestParam("name") String name) {
		return configurableEventTypeRepository.findPublicEventsByName(name).stream().limit(10)
				.map(e -> mapper.configurableEventTypeToConfigurableEventTypeResponse(e)).collect(Collectors.toList());
	}
}
