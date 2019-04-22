package com.ioteg.services;

public class PasswordNotMatchException extends Exception {

	private static final long serialVersionUID = 1L;

	@Override
	public String getMessage() {
		return "Passwords do not match.";
	}
	
}
