package com.ioteg.communications;

import com.ioteg.resultmodel.ResultEvent;

public class MqttResultEvent {
	private ResultEvent message;
	private String apiKey;
	
	/**
	 * @param message
	 * @param apiKey
	 */
	public MqttResultEvent(ResultEvent message, String apiKey) {
		super();
		this.message = message;
		this.apiKey = apiKey;
	}

	/**
	 * @return the message
	 */
	public ResultEvent getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(ResultEvent message) {
		this.message = message;
	}

	/**
	 * @return the apiKey
	 */
	public String getApiKey() {
		return apiKey;
	}

	/**
	 * @param apiKey the apiKey to set
	 */
	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}
}
