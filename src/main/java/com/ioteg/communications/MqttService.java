package com.ioteg.communications;

import java.io.IOException;

import javax.annotation.PreDestroy;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ioteg.serializers.csv.CSVUtil;
import com.ioteg.serializers.xml.XMLSerializerMapper;
import com.ioteg.services.EntityNotFoundException;

@Service
@Profile({ "production", "development", "default" })
public class MqttService {
	private Logger logger = LoggerFactory.getLogger(MqttService.class);

	private MqttClient mqttClient;
	private ObjectMapper objectMapper;
	private XMLSerializerMapper xmlSerializerMapper;

	/**
	 * @param mqttClient
	 * @param objectMapper
	 * @param userService
	 * @param xmlSerializerMapper
	 */
	@Autowired
	public MqttService(MqttClient mqttClient, ObjectMapper objectMapper,
			XMLSerializerMapper xmlSerializerMapper) {
		super();
		this.mqttClient = mqttClient;
		this.objectMapper = objectMapper;
		this.xmlSerializerMapper = xmlSerializerMapper;
	}

	public void sendMessage(String topic, MqttResultEvent message) throws MqttException, IOException {
		String serializedMessage = "";
		
		try {
			serializedMessage = CSVUtil.serializeResultEvent(message.getMessage().getModel(),
					message.getMessage());
			mqttClient.publish(getCompleteTopic(topic, "csv", message.getApiKey()), new MqttMessage(serializedMessage.getBytes()));
		} catch (Exception e) {
			logger.error(e.fillInStackTrace().toString());
			e.printStackTrace();
		}

		try {
			serializedMessage = xmlSerializerMapper.writeValueAsString(message.getMessage());
			mqttClient.publish(getCompleteTopic(topic, "xml", message.getApiKey()), new MqttMessage(serializedMessage.getBytes()));
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		
		try {
			serializedMessage = objectMapper.writeValueAsString(message.getMessage());
			mqttClient.publish(getCompleteTopic(topic, "json", message.getApiKey()), new MqttMessage(serializedMessage.getBytes()));
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}
	
	private String getCompleteTopic(String parcialTopic, String format, String apiKey) throws EntityNotFoundException {
		return String.format("%s/%s/%s", parcialTopic, format, apiKey);
	}

	@PreDestroy
	private void closeMqttClient() throws MqttException {
		logger.info("Closing MQTT Client");
		mqttClient.close();
	}
}
