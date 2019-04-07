package com.ioteg.communications;

import javax.annotation.PreDestroy;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class MqttConfig {
	
	@Autowired
	Environment env;
	
	@Bean
	public MqttClient mqttClient() throws MqttException {
		MqttClient mqttClient = new MqttClient(env.getProperty("mqtt.server"), MqttClient.generateClientId(), new MemoryPersistence());
		MqttConnectOptions mqttOptions = new MqttConnectOptions();
		mqttOptions.setUserName(env.getProperty("mqtt.user"));
		mqttOptions.setPassword(env.getProperty("mqtt.password").toCharArray());
		mqttClient.connect(mqttOptions);
		return mqttClient;
	}
	
	@PreDestroy
	public void closeMqttClient() throws MqttException {
		mqttClient().close();
	}
}
