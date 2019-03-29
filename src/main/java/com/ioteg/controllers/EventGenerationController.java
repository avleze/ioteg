package com.ioteg.controllers;

import java.text.ParseException;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ioteg.exprlang.ExprParser.ExprLangParsingException;
import com.ioteg.generators.exceptions.NotExistingGeneratorException;
import com.ioteg.model.EventType;

@RestController
public class EventGenerationController {

	private static ScheduledThreadPoolExecutor schThrPoolEx = new ScheduledThreadPoolExecutor(4);

	@PostMapping("/generateEvent")
	@ResponseBody
	@CrossOrigin(origins = "*")
	public void generateEvent(@RequestBody @Valid EventType eventType, HttpServletRequest req)
			throws NotExistingGeneratorException, ExprLangParsingException, ParseException {

		schThrPoolEx.scheduleAtFixedRate(new PeriodicEventGenerator(eventType), 0, 1, TimeUnit.SECONDS);
	}

}
