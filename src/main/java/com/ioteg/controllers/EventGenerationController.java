package com.ioteg.controllers;


import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ioteg.exprlang.ExprParser.ExprLangParsingException;
import com.ioteg.generators.GeneratorsFactory;
import com.ioteg.generators.context.GenerationContext;
import com.ioteg.generators.exceptions.NotExistingGeneratorException;
import com.ioteg.model.ConfigurableEventList;
import com.ioteg.model.EventType;
import com.ioteg.model.EventTypeList;
import com.ioteg.resultmodel.ResultEvent;
import com.ioteg.resultmodel.ResultField;
import com.ioteg.services.periodicgeneration.PeriodicEventGenerationService;

@RestController
public class EventGenerationController {

	@Autowired
	private PeriodicEventGenerationService periodicEventGenerationService;
	

	@PostMapping("/generateEvents")
	@ResponseBody
	@CrossOrigin(origins = "*")
	public List<ResultEvent> generateEvents(@RequestBody @Valid EventTypeList eventTypes)
			throws NotExistingGeneratorException, ExprLangParsingException, ParseException {
		List<ResultEvent> results = new ArrayList<>();
		ConcurrentMap<String, ResultField> sharedConcurrentMap = new ConcurrentHashMap<>();
		
		for(EventType eventType : eventTypes.getEventTypes())
			results.add(GeneratorsFactory.makeEventTypeGenerator(eventType, new GenerationContext(sharedConcurrentMap)).generate(1).get(0));
		
		return results;
	}
	
	@PostMapping("/generateConfigurableEvents")
	@ResponseBody
	@CrossOrigin(origins = "*")
	public void generateConfigurableEvents(@RequestBody @Valid ConfigurableEventList configurableEventTypes)
			throws NotExistingGeneratorException, ExprLangParsingException, ParseException {
		
		periodicEventGenerationService.executeConfigurableEventTypes(configurableEventTypes.getConfigurableEventTypes());
		
	}

}
