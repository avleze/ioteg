package com.ioteg.communications;

import com.ioteg.resultmodel.ResultEvent;

public class MqttResultEvent {
	private ResultEvent message;
	private String type;
	
	/**
	 * @param message
	 * @param type
	 */
	public MqttResultEvent(ResultEvent message, String type) {
		super();
		this.message = message;
		this.type = type;
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
	 * @return the type
	 */
	public String getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}
	
}
