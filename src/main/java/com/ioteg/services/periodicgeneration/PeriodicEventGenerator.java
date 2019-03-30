package com.ioteg.services.periodicgeneration;

import java.text.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
	private Logger logger = LoggerFactory.getLogger(PeriodicEventGenerator.class);
	private ObjectMapper objectMapper;
	/**
	 * @param eventType
	 * @throws ParseException 
	 * @throws ExprLangParsingException 
	 * @throws NotExistingGeneratorException 
	 */
	public PeriodicEventGenerator(EventType eventType, GenerationContext generationContext, ObjectMapper objectMapper) throws NotExistingGeneratorException, ExprLangParsingException, ParseException {
		this.eventTypeGenerator = new EventTypeGenerator(new EventTypeGenerationAlgorithm(eventType, generationContext), generationContext);
		this.objectMapper = objectMapper;
		this.objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
	}

	@Override
	public void run() {
		try {
			logger.info(objectMapper.writeValueAsString(eventTypeGenerator.generate(1)));
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}
	
	


}
