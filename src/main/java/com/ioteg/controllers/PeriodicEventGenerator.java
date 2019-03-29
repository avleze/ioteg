package com.ioteg.controllers;

import java.text.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ioteg.exprlang.ExprParser.ExprLangParsingException;
import com.ioteg.generators.context.GenerationContext;
import com.ioteg.generators.eventtype.EventTypeGenerationAlgorithm;
import com.ioteg.generators.eventtype.EventTypeGenerator;
import com.ioteg.generators.exceptions.NotExistingGeneratorException;
import com.ioteg.model.EventType;

public class PeriodicEventGenerator implements Runnable {
	private EventTypeGenerator eventTypeGenerator;
	private Logger logger = LoggerFactory.getLogger(PeriodicEventGenerator.class);

	/**
	 * @param eventType
	 * @throws ParseException 
	 * @throws ExprLangParsingException 
	 * @throws NotExistingGeneratorException 
	 */
	public PeriodicEventGenerator(EventType eventType) throws NotExistingGeneratorException, ExprLangParsingException, ParseException {
		GenerationContext context = new GenerationContext();
		this.eventTypeGenerator = new EventTypeGenerator(new EventTypeGenerationAlgorithm(eventType, context), context);
	}

	@Override
	public void run() {
		try {
			eventTypeGenerator.generate(1);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}
	
	


}
