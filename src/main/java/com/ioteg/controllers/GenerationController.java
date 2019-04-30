package com.ioteg.controllers;

import java.text.ParseException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ioteg.exprlang.ExprParser.ExprLangParsingException;
import com.ioteg.generation.EventTypeGenerator;
import com.ioteg.generation.GenerationContext;
import com.ioteg.generation.GeneratorsFactory;
import com.ioteg.generation.NotExistingGeneratorException;
import com.ioteg.model.EventType;
import com.ioteg.resultmodel.ResultEvent;
import com.ioteg.services.GenerationService;

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

	@Autowired
	private GenerationService generationService;

	@GetMapping(path = "/{configurableEventTypeId}", produces = { "application/xml", "application/csv",
			"application/json" })
	public ResponseEntity<ResultEvent> getEventComplete(HttpServletRequest request,
			HttpServletResponse response, @PathVariable("configurableEventTypeId") Long configurableEventTypeId)
			throws NotExistingGeneratorException, ExprLangParsingException, ParseException {
		EventType eventType = generationService.loadConfigurableEventTypeDeeply(configurableEventTypeId).getEventType();
		EventTypeGenerator eventTypeGenerator = GeneratorsFactory.makeEventTypeGenerator(eventType,
				new GenerationContext());
		response.addHeader("content-disposition", "attachment; filename=" + eventType.getName());
		response.addHeader("Content-Type", response.getContentType());
		return ResponseEntity.ok().body(eventTypeGenerator.generate(1).get(0));
	}
}
