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

@Service
@Profile({ "production", "development", "default" })
public class MqttService {
	private Logger logger = LoggerFactory.getLogger(MqttService.class);

	@Autowired
	private MqttClient mqttClient;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private XMLSerializerMapper xmlSerializerMapper;

	public void sendMessage(String topic, MqttResultEvent message) throws MqttException, IOException {
		String serializedMessage = "";
		
		try {
			serializedMessage = CSVUtil.serializeResultEvent(message.getMessage().getModel(),
					message.getMessage());
			mqttClient.publish(String.format("%s/%s", topic, "csv"), new MqttMessage(serializedMessage.getBytes()));
		} catch (Exception e) {

		}

		try {
			serializedMessage = xmlSerializerMapper.writeValueAsString(message.getMessage());
			mqttClient.publish(String.format("%s/%s", topic, "xml"), new MqttMessage(serializedMessage.getBytes()));
		} catch (Exception e) {

		}

		try {
			serializedMessage = objectMapper.writeValueAsString(message.getMessage());
			mqttClient.publish(String.format("%s/%s", topic, "json"), new MqttMessage(serializedMessage.getBytes()));
		} catch (Exception e) {

		}

	}

	@PreDestroy
	private void closeMqttClient() throws MqttException {
		logger.info("Closing MQTT Client");
		mqttClient.close();
	}
}
