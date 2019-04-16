package com.ioteg.users;

import java.util.List;
import java.util.Set;
import com.ioteg.model.ChannelType;

public class UserDTO {

	private String username;
	private String email;
	private Set<Role> roles;
	private List<ChannelType> channels;
	private String mqttApiKey;

	protected UserDTO() {
		
	}
	
	public UserDTO(User user) {
		this.username = user.getUsername();
		this.email = user.getEmail();
		this.channels = user.getChannels();
		this.roles = user.getRoles();
		this.mqttApiKey = user.getMqttApiKey();
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
	 * @return the roles
	 */
	public Set<Role> getRoles() {
		return roles;
	}

	/**
	 * @param roles the roles to set
	 */
	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	/**
	 * @return the channels
	 */
	public List<ChannelType> getChannels() {
		return channels;
	}

	/**
	 * @param channels the channels to set
	 */
	public void setChannels(List<ChannelType> channels) {
		this.channels = channels;
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

}
