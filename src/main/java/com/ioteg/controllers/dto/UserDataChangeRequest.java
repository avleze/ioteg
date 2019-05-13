package com.ioteg.controllers.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.ioteg.model.validation.UniqueEmail;
import com.ioteg.model.validation.UniqueUsername;

public class UserDataChangeRequest {
	@NotNull(message = "The username can not be null.")
	@NotEmpty(message = "The username can not be empty.")
	@UniqueUsername
	private String username;

	@NotNull(message = "The email can not be null.")
	@NotEmpty(message = "The email can not be empty.")
	@Email(message = "The email is not valid.")
	@UniqueEmail
	private String email;

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
}
