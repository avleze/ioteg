package com.ioteg.controllers;

import java.security.Principal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ioteg.exprlang.ExprParser.ExprLangParsingException;
import com.ioteg.generation.GenerationContext;
import com.ioteg.generation.GeneratorsFactory;
import com.ioteg.generation.NotExistingGeneratorException;
import com.ioteg.model.ChannelType;
import com.ioteg.model.EventType;
import com.ioteg.model.EventTypeList;
import com.ioteg.resultmodel.ResultEvent;
import com.ioteg.resultmodel.ResultField;
import com.ioteg.services.periodicgeneration.PeriodicEventGenerationService;
import com.ioteg.users.User;
import com.ioteg.users.UserRepository;

/**
 * <p>
 * EventGenerationController class.
 * </p>
 *
 * @author antonio
 * @version $Id: $Id
 */
@RestController
@RequestMapping("/api/events/")
public class EventGenerationController {

	@Autowired(required = false)
	private PeriodicEventGenerationService periodicEventGenerationService;

	@Autowired
	private UserRepository userRepository;

	/**
	 * <p>
	 * generateEvents.
	 * </p>
	 *
	 * @param eventTypes a {@link com.ioteg.model.EventTypeList} object.
	 * @return a {@link java.util.List} object.
	 * @throws com.ioteg.generation.NotExistingGeneratorException     if any.
	 * @throws com.ioteg.exprlang.ExprParser.ExprLangParsingException if any.
	 * @throws java.text.ParseException                               if any.
	 */
	@PostMapping("/generateEvents")
	@ResponseBody
	@CrossOrigin(origins = "*")
	public List<ResultEvent> generateEvents(@RequestBody @Valid EventTypeList eventTypes)
			throws NotExistingGeneratorException, ExprLangParsingException, ParseException {
		List<ResultEvent> results = new ArrayList<>();
		ConcurrentMap<String, ResultField> sharedConcurrentMap = new ConcurrentHashMap<>();
		for (EventType eventType : eventTypes.getEventTypes())
			results.add(GeneratorsFactory.makeEventTypeGenerator(eventType, new GenerationContext(sharedConcurrentMap))
					.generate(1).get(0));

		return results;
	}

	/**
	 * <p>
	 * generateConfigurableEvents.
	 * </p>
	 *
	 * @param channels a {@link com.ioteg.model.ChannelType} object.
	 * @throws com.ioteg.generation.NotExistingGeneratorException     if any.
	 * @throws com.ioteg.exprlang.ExprParser.ExprLangParsingException if any.
	 * @throws java.text.ParseException                               if any.
	 */
	@PostMapping(path = "/addChannel", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	@CrossOrigin(origins = "*")
	public ResponseEntity<List<String>> generateConfigurableEvents(@RequestBody @Valid ChannelType channel,
			Principal user) throws NotExistingGeneratorException, ExprLangParsingException, ParseException {
		ResponseEntity<List<String>> response = ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

		if (user != null) {
			User loggedUser = userRepository.findByUsernameWithChannels(user.getName()).get();
			loggedUser.getChannels().add(channel);
			userRepository.save(loggedUser);

			List<String> topicsCreated = periodicEventGenerationService.executeAsyncChannel(channel,
					loggedUser.getMqttApiKey());
			response = ResponseEntity.ok().body(topicsCreated);
		}

		return response;
	}

	@PostMapping("/stopEventGeneration")
	public ResponseEntity<Boolean> stopAsyncGeneration(@RequestParam("id") UUID id, Principal principal) {
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
	public ResponseEntity<Boolean> resumeAsyncGeneration(@RequestParam("id") UUID id, Principal principal) {
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
	}

}
