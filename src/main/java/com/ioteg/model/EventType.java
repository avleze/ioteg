package com.ioteg.model;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ioteg.users.User;

/**
 * This class represents an event type.
 *
 * @author Antonio Vélez Estévez
 * @version $Id: $Id
 */

@Entity
public class EventType{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected Long id;

	@NotEmpty
	@NotNull
	private String name;

	@Valid
	@OneToMany(cascade = CascadeType.REMOVE)
	private List<Block> blocks;

	@OneToOne
	private User owner;
	
	@SuppressWarnings("unused")
	private EventType() {

	}

	/**
	 * @param id
	 * @param name
	 * @param blocks
	 */
	@JsonCreator
	public EventType(@JsonProperty("id") Long id, @JsonProperty("name") @NotEmpty @NotNull String name,
			@JsonProperty("blocks") @Valid List<Block> blocks) {
		super();
		if (blocks == null)
			blocks = new ArrayList<>();

		this.id = id;
		this.name = name;
		this.blocks = blocks;
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

	/**
	 * <p>
	 * Getter for the field <code>blocks</code>.
	 * </p>
	 *
	 * @return the blocks
	 */
	public List<Block> getBlocks() {
		return blocks;
	}

	/**
	 * <p>
	 * Setter for the field <code>blocks</code>.
	 * </p>
	 *
	 * @param blocks the blocks to set
	 */
	public void setBlocks(List<Block> blocks) {
		this.blocks = blocks;
	}



}
