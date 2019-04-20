package com.ioteg.model;

import javax.persistence.Entity;
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
public class InjectedField extends OwnedEntity{

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
	public InjectedField(@JsonProperty("id") Long id, @JsonProperty("name") String name) {
		super();
		this.id = id;
		this.name = name;
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
