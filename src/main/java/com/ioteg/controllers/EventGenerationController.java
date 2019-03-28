package com.ioteg.controllers;

import java.text.ParseException;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ioteg.exprlang.ExprParser.ExprLangParsingException;
import com.ioteg.generators.exceptions.NotExistingGeneratorException;
import com.ioteg.model.EventType;
import com.ioteg.resultmodel.ResultEvent;

@RestController
public class EventGenerationController {
	
	private static ScheduledThreadPoolExecutor schThrPoolEx = new ScheduledThreadPoolExecutor(4);
	@Autowired 
	private ObjectMapper jacksonObjectMapper;
	
	@PostMapping("/generateEvent")
	@ResponseBody
    @CrossOrigin(origins = "*")
	public ResultEvent generateEvent(@RequestBody @Valid EventType eventType, HttpServletRequest req) throws NotExistingGeneratorException, ExprLangParsingException, ParseException {
		
		ScheduledFuture<?> tarea = schThrPoolEx.scheduleAtFixedRate(new PeriodicEventGenerator(eventType, jacksonObjectMapper), 0, 1, TimeUnit.SECONDS);
		
		while(!tarea.isDone()) {}
		return (ResultEvent) new Object();
	}

}
