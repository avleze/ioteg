package com.ioteg.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ioteg.EventGenerator;
import com.ioteg.exprlang.ExprParser.ExprLangParsingException;
import com.ioteg.generators.exceptions.NotExistingGeneratorException;
import com.ioteg.model.EventType;
import com.ioteg.resultmodel.ResultEvent;

@RestController
public class EventGenerationController {
	
	@PostMapping(name = "/generateEvent")
	@ResponseBody
    @CrossOrigin(origins = "*")
	public ResultEvent generateEvent(@RequestBody EventType eventType, HttpServletRequest request) throws NotExistingGeneratorException, ExprLangParsingException {
		return EventGenerator.generateEvent(eventType);
	}
}
