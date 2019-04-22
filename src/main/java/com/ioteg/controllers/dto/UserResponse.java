package com.ioteg.controllers.dto;

import javax.validation.constraints.NotNull;

public class UserResponse {
	@NotNull
	private Long id;
	@NotNull
	private String username;
	@NotNull
	private String email;
	@NotNull
	private String role;
	@NotNull
	private String mqttApiKey;

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

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the role
	 */
	public String getRole() {
		return role;
	}

	/**
	 * @param role the role to set
	 */
	public void setRole(String role) {
		this.role = role;
	}

	/**
	 * @return the mqttApiKey
	 */
	public String getMqttApiKey() {
		return mqttApiKey;
	}

	/**
	 * @param mqttApiKey the mqttApiKey to set
	 */
	public void setMqttApiKey(String mqttApiKey) {
		this.mqttApiKey = mqttApiKey;
	}
}
