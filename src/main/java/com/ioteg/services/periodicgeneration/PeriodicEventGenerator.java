package com.ioteg.services.periodicgeneration;

import java.text.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ioteg.communications.MqttResultEvent;
import com.ioteg.communications.MqttService;
import com.ioteg.exprlang.ExprParser.ExprLangParsingException;
import com.ioteg.generation.EventTypeGenerationAlgorithm;
import com.ioteg.generation.EventTypeGenerator;
import com.ioteg.generation.GenerationContext;
import com.ioteg.generation.NotExistingGeneratorException;
import com.ioteg.model.ConfigurableEventType;
import com.ioteg.resultmodel.ResultEvent;

/**
 * <p>
 * PeriodicEventGenerator class.
 * </p>
 *
 * @author antonio
 * @version $Id: $Id
 */
public class PeriodicEventGenerator implements Runnable {
	private EventTypeGenerator eventTypeGenerator;
	private Logger logger = LoggerFactory.getLogger(PeriodicEventGenerator.class);
	private MqttService mqttService;
	private ConfigurableEventType configurableEventType;
	private String topic;

	/**
	 * <p>
	 * Constructor for PeriodicEventGenerator.
	 * </p>
	 * 
	 * @param generationContext     a {@link com.ioteg.generation.GenerationContext}
	 *                              object.
	 * @param configurableEventType a {@link com.ioteg.model.ConfigurableEventType}
	 *                              object.
	 * @throws java.text.ParseException                               if any.
	 * @throws com.ioteg.exprlang.ExprParser.ExprLangParsingException if any.
	 * @throws com.ioteg.generation.NotExistingGeneratorException     if any.
	 */
	public PeriodicEventGenerator(String topic, ConfigurableEventType configurableEventType,
			GenerationContext generationContext, MqttService mqttService)
			throws NotExistingGeneratorException, ExprLangParsingException, ParseException {
		this.topic = topic;
		this.configurableEventType = configurableEventType;
		this.eventTypeGenerator = new EventTypeGenerator(
				new EventTypeGenerationAlgorithm(configurableEventType.getEventType(), generationContext),
				generationContext);
		this.mqttService = mqttService;
	}

	/** {@inheritDoc} */
	@Override
	public void run() {
		try {
			ResultEvent resultEvent = eventTypeGenerator.generate(1).get(0);
			mqttService.sendMessage(resultEvent.getName(), new MqttResultEvent(resultEvent, "application/json"));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	public ConfigurableEventType getConfigurableEventType() {
		return configurableEventType;
	}

	/**
	 * @return the topic
	 */
	public String getTopic() {
		return topic;
	}

	/**
	 * @param topic the topic to set
	 */
	public void setTopic(String topic) {
		this.topic = topic;
	}

}
