package com.ioteg.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * <p>InjectedField class.</p>
 *
 * @author antonio
 * @version $Id: $Id
 */
public class InjectedField {
	private String name;

	/**
	 * <p>Constructor for InjectedField.</p>
	 *
	 * @param name a {@link java.lang.String} object.
	 */
	@JsonCreator
	public InjectedField(@JsonProperty("name") String name) {
		super();
		this.name = name;
	}

	/**
	 * <p>Getter for the field <code>name</code>.</p>
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * <p>Setter for the field <code>name</code>.</p>
	 *
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	
}
