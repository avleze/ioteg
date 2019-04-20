package com.ioteg.services;

public class ResourceNotFoundException extends Exception{

	private static final long serialVersionUID = 1724184743499558012L;
	
	private String resourceName;
	private String message;
	
	/**
	 * @param resourceName
	 * @param message
	 */
	public ResourceNotFoundException(String resourceName, String message) {
		super();
		this.resourceName = resourceName;
		this.message = message;
	}

	@Override
	public String getMessage() {
		return "Not found resource: " + resourceName + " Exception message is: " + message;
	}
	
}
