package com.ioteg.services.periodicgeneration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ioteg.communications.MqttResultEvent;
import com.ioteg.communications.MqttService;
import com.ioteg.generation.EventTypeGenerator;
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
	private String topic;
	private String apiKey;
	
	/**
	 * @param eventTypeGenerator
	 * @param mqttService
	 * @param topic
	 * @param apiKey
	 */
	public PeriodicEventGenerator(EventTypeGenerator eventTypeGenerator, MqttService mqttService, String topic,
			String apiKey) {
		super();
		this.eventTypeGenerator = eventTypeGenerator;
		this.mqttService = mqttService;
		this.topic = topic;
		this.apiKey = apiKey;
	}



	/** {@inheritDoc} */
	@Override
	public void run() {
		try {
			ResultEvent resultEvent = eventTypeGenerator.generate(1).get(0);
			mqttService.sendMessage(this.topic, new MqttResultEvent(resultEvent, apiKey));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	

}
