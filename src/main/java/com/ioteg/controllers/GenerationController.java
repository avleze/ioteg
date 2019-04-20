package com.ioteg.controllers;

import java.text.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.ioteg.repositories.UserRepository;
import com.ioteg.resultmodel.ResultEvent;
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

	@Autowired(required = false)
	private PeriodicEventGenerationService periodicEventGenerationService;

	@Autowired
	private UserRepository userRepository;

	@PostMapping("event")
	@PreAuthorize("hasPermission(#event, 'OWNER')")
	public ResponseEntity<ResultEvent> generate(@RequestBody EventType event) throws NotExistingGeneratorException, ExprLangParsingException, ParseException {
		
		EventTypeGenerator eventTypeGenerator = GeneratorsFactory.makeEventTypeGenerator(event, new GenerationContext());
		
		
		return ResponseEntity.ok().body(eventTypeGenerator.generate(1).get(0));
	}
	
	/*
	@PostMapping("/stopEventGeneration")
	public ResponseEntity<Boolean> stopAsyncGeneration(@RequestParam("id") Long id, Principal principal) {
		ResponseEntity<Boolean> response = ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(false);
		Optional<User> owner = userRepository.findUserWithConfigurableEvent(id);

		if (owner.isPresent()) {
			if (owner.get().getUsername().equals(principal.getName())) {
				response = ResponseEntity.ok().body(true);
				try {
					periodicEventGenerationService.stopConfigurableEventType(id);
				} catch (Exception e) {
					response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(false);
				}
			}
		}

		return response;
	}

	@PostMapping("/resumeEventGeneration")
	public ResponseEntity<Boolean> resumeAsyncGeneration(@RequestParam("id") Long id, Principal principal) {
		ResponseEntity<Boolean> response = ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(false);
		Optional<User> owner = userRepository.findUserWithConfigurableEvent(id);

		if (owner.isPresent()) {
			if (owner.get().getUsername().equals(principal.getName())) {
				response = ResponseEntity.ok().body(true);
				try {
					periodicEventGenerationService.resumeConfigurableEventType(id);
				} catch (Exception e) {
					response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(false);
				}
			}
		}

		return response;
	}*/

}
