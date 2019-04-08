package com.ioteg.model;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * <p>
 * InjectedField class.
 * </p>
 *
 * @author antonio
 * @version $Id: $Id
 */
@Entity
public class InjectedField {
	@Id
	private UUID id;
	private String name;

	@SuppressWarnings("unused")
	private InjectedField() {
		
	}

	/**
	 * <p>
	 * Constructor for InjectedField.
	 * </p>
	 *
	 * @param name a {@link java.lang.String} object.
	 */
	@JsonCreator
	public InjectedField(@JsonProperty("id") UUID id, @JsonProperty("name") String name) {
		super();
		if(id == null)
			id = UUID.randomUUID();
		this.id = id;
		this.name = name;
	}

	/**
	 * @return the id
	 */
	public UUID getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(UUID id) {
		this.id = id;
	}

	/**
	 * <p>
	 * Getter for the field <code>name</code>.
	 * </p>
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * <p>
	 * Setter for the field <code>name</code>.
	 * </p>
	 *
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

}
