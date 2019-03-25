package com.ioteg.controllers;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ioteg.EventGenerator;
import com.ioteg.exprlang.ExprParser.ExprLangParsingException;
import com.ioteg.generators.exceptions.NotExistingGeneratorException;
import com.ioteg.model.EventType;
import com.ioteg.resultmodel.ResultEvent;

@Controller
public class EventGenerationController {
	
	private static Logger logger = LoggerFactory.getLogger(EventGenerationController.class);
	
	@RequestMapping("/generateEvent")
	@ResponseBody
    @CrossOrigin(origins = "*")
	public ResultEvent generateEvent(@RequestBody EventType eventType, HttpServletRequest request) throws NotExistingGeneratorException, ExprLangParsingException {
		logger.info("New petition from " + request.getRemoteAddr() + " " + request.getHeader("User-Agent"));
		return EventGenerator.generateEvent(eventType);
	}
}
