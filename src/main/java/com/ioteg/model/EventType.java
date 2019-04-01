package com.ioteg.model;

import java.util.List;

import javax.validation.Valid;

/**
 * This class represents an event type.
 *
 * @author Antonio Vélez Estévez
 * @version $Id: $Id
 */
public class EventType {
	private String name;
	@Valid
	private List<Block> blocks;

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

	/**
	 * <p>Getter for the field <code>blocks</code>.</p>
	 *
	 * @return the blocks
	 */
	public List<Block> getBlocks() {
		return blocks;
	}

	/**
	 * <p>Setter for the field <code>blocks</code>.</p>
	 *
	 * @param blocks the blocks to set
	 */
	public void setBlocks(List<Block> blocks) {
		this.blocks = blocks;
	}

}
