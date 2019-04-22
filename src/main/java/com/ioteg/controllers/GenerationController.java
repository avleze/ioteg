package com.ioteg.controllers;

import java.text.ParseException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ioteg.exprlang.ExprParser.ExprLangParsingException;
import com.ioteg.generation.EventTypeGenerator;
import com.ioteg.generation.GenerationContext;
import com.ioteg.generation.GeneratorsFactory;
import com.ioteg.generation.NotExistingGeneratorException;
import com.ioteg.model.EventType;
import com.ioteg.resultmodel.ResultEvent;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

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

	@PostMapping("event")
	@PreAuthorize("hasPermission(#event, 'OWNER')")
	@ApiResponses(@ApiResponse(response = void.class, code = 200, message = "OK"))
	public ResponseEntity<ResultEvent> generate(@RequestBody EventType event) throws NotExistingGeneratorException, ExprLangParsingException, ParseException {
		EventTypeGenerator eventTypeGenerator = GeneratorsFactory.makeEventTypeGenerator(event, new GenerationContext());
		return ResponseEntity.ok().body(eventTypeGenerator.generate(1).get(0));
	}
	
}
