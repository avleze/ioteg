package com.ioteg.controllers;

import java.text.ParseException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ioteg.exprlang.ExprParser.ExprLangParsingException;
import com.ioteg.generation.EventTypeGenerator;
import com.ioteg.generation.GenerationContext;
import com.ioteg.generation.GeneratorsFactory;
import com.ioteg.generation.NotExistingGeneratorException;
import com.ioteg.model.EventType;
import com.ioteg.resultmodel.ResultEvent;
import com.ioteg.services.EntityNotFoundException;
import com.ioteg.services.GenerationService;
import com.ioteg.services.periodicgeneration.PeriodicEventGenerationService;

/**
 * <p>
 * EventGenerationController class.
 * </p>
 *
 * @author antonio
 * @version $Id: $Id
 */
@RestController
@RequestMapping("/api/generation/")
public class GenerationController {

	private GenerationService generationService;
	private PeriodicEventGenerationService periodicGenerationService;
	
	/**
	 * @param generationService
	 * @param periodicGenerationService
	 */
	
	public GenerationController(@Autowired GenerationService generationService,
			 @Autowired(required = false) PeriodicEventGenerationService periodicGenerationService) {
		super();
		this.generationService = generationService;
		this.periodicGenerationService = periodicGenerationService;
	}

	@GetMapping(path = "/{configurableEventTypeId}", produces = { "application/xml", "application/csv",
			"application/json" })
	public ResponseEntity<ResultEvent> getEventComplete(HttpServletRequest request, HttpServletResponse response,
			@PathVariable("configurableEventTypeId") Long configurableEventTypeId)
			throws NotExistingGeneratorException, ExprLangParsingException, ParseException {
		EventType eventType = generationService.loadConfigurableEventTypeDeeply(configurableEventTypeId).getEventType();
		EventTypeGenerator eventTypeGenerator = GeneratorsFactory.makeEventTypeGenerator(eventType,
				new GenerationContext());
		response.addHeader("content-disposition", "attachment; filename=" + eventType.getName());
		response.addHeader("Content-Type", response.getContentType());
		return ResponseEntity.ok().body(eventTypeGenerator.generate(1).get(0));
	}

	@PostMapping(path = "/channel/{channelId}/event/{eventId}/start")
	public void startAsync(@PathVariable("channelId") Long channelId, @PathVariable("eventId") Long eventId) throws EntityNotFoundException, NotExistingGeneratorException, ExprLangParsingException, ParseException {
		periodicGenerationService.executeAsyncEvent(channelId, eventId);
	
	}
	
	@PostMapping(path = "/channel/{channelId}/event/{eventId}/stop")
	public void stopAsync(@PathVariable("channelId") Long channelId, @PathVariable("eventId") Long eventId) throws EntityNotFoundException, NotExistingGeneratorException, ExprLangParsingException, ParseException {
		periodicGenerationService.stopAsyncEvent(channelId, eventId);
	}
}
