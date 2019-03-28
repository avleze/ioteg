package com.ioteg.controllers;

import java.text.ParseException;
import java.util.Calendar;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.ioteg.exprlang.ExprParser.ExprLangParsingException;
import com.ioteg.generators.context.GenerationContext;
import com.ioteg.generators.eventtype.EventTypeGenerationAlgorithm;
import com.ioteg.generators.eventtype.EventTypeGenerator;
import com.ioteg.generators.exceptions.NotExistingGeneratorException;
import com.ioteg.model.EventType;

public class PeriodicEventGenerator implements Runnable {
	private EventTypeGenerator eventTypeGenerator;
	private ObjectMapper jacksonObjectMapper;
	/**
	 * @param eventType
	 * @throws ParseException 
	 * @throws ExprLangParsingException 
	 * @throws NotExistingGeneratorException 
	 */
	public PeriodicEventGenerator(EventType eventType, ObjectMapper jacksonObjectMapper) throws NotExistingGeneratorException, ExprLangParsingException, ParseException {
		GenerationContext context = new GenerationContext();
		this.eventTypeGenerator = new EventTypeGenerator(new EventTypeGenerationAlgorithm(eventType, context), context);
		this.jacksonObjectMapper = jacksonObjectMapper;
		this.jacksonObjectMapper.enable(SerializationFeature.INDENT_OUTPUT);
	}

	@Override
	public void run() {
		try {
			Calendar cal = Calendar.getInstance();
			cal.setTimeInMillis(System.currentTimeMillis());
			System.out.println(cal.getTime().toString());
			System.out.println("==================================");
			System.out.println(jacksonObjectMapper.writeValueAsString(eventTypeGenerator.generate(1)));
			System.out.println("==================================");
		} catch (NotExistingGeneratorException | ExprLangParsingException | ParseException e) {
			e.printStackTrace();
			System.out.println(e);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e);
		}
	}
	
	


}
