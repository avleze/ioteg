package com.ioteg.communications;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MqttService {
	@Autowired
	private MqttClient mqttClient;
	
	public void sendMessage(String topic, String message) throws MqttPersistenceException, MqttException {
		mqttClient.publish(topic, new MqttMessage(message.getBytes()));
	}
}
