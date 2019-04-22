package com.ioteg.controllers.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class ChannelTypeRequest {
	@NotNull(message = "The channelName can't be null.")
	@NotEmpty(message = "The channelName can't be emtpy.")
	private String channelName;

	/**
	 * @return the channelName
	 */
	public String getChannelName() {
		return channelName;
	}

	/**
	 * @param channelName the channelName to set
	 */
	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}
}
