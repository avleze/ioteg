package com.ioteg.controllers;

import java.text.ParseException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

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
	
	@PostMapping("/generateEvent")
	@ResponseBody
    @CrossOrigin(origins = "*")
	public ResultEvent generateEvent(@RequestBody @Valid EventType eventType, HttpServletRequest req) throws NotExistingGeneratorException, ExprLangParsingException, ParseException {
		return EventGenerator.generateEvent(eventType);
	}

}