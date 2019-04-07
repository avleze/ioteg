package com.ioteg.services.periodicgeneration;

import java.text.ParseException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.ioteg.communications.MqttService;
import com.ioteg.exprlang.ExprParser.ExprLangParsingException;
import com.ioteg.generation.EventTypeGenerationAlgorithm;
import com.ioteg.generation.EventTypeGenerator;
import com.ioteg.generation.GenerationContext;
import com.ioteg.generation.NotExistingGeneratorException;
import com.ioteg.model.EventType;
import com.ioteg.resultmodel.ResultEvent;

/**
 * <p>PeriodicEventGenerator class.</p>
 *
 * @author antonio
 * @version $Id: $Id
 */
public class PeriodicEventGenerator implements Runnable {
	private EventTypeGenerator eventTypeGenerator;
	private ObjectMapper objectMapper;
	private MqttService mqttService;
	/**
	 * <p>Constructor for PeriodicEventGenerator.</p>
	 *
	 * @param eventType a {@link com.ioteg.model.EventType} object.
	 * @throws java.text.ParseException if any.
	 * @throws com.ioteg.exprlang.ExprParser.ExprLangParsingException if any.
	 * @throws com.ioteg.generation.NotExistingGeneratorException if any.
	 * @param generationContext a {@link com.ioteg.generation.GenerationContext} object.
	 * @param objectMapper a {@link com.fasterxml.jackson.databind.ObjectMapper} object.
	 */
	public PeriodicEventGenerator(EventType eventType, GenerationContext generationContext, ObjectMapper objectMapper, MqttService mqttService) throws NotExistingGeneratorException, ExprLangParsingException, ParseException {
		this.eventTypeGenerator = new EventTypeGenerator(new EventTypeGenerationAlgorithm(eventType, generationContext), generationContext);
		this.objectMapper = objectMapper;
		this.objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
		this.mqttService = mqttService;
	}

	/** {@inheritDoc} */
	@Override
	public void run() {
		try {
			ResultEvent resultEvent = eventTypeGenerator.generate(1).get(0);
			mqttService.sendMessage(resultEvent.getName(), objectMapper.writeValueAsString(resultEvent));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	


}
