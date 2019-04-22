package com.ioteg.controllers.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class ChannelTypeResponse {
	@NotNull
	private Long id;

	@NotNull
	@NotEmpty
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

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
}
