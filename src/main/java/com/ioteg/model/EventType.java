package com.ioteg.model;

import java.util.List;

/**
 * This class represents an event type.
 * 
 * @author Antonio Vélez Estévez
 */
public class EventType {
	private String name;
	private List<Block> blocks;

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the blocks
	 */
	public List<Block> getBlocks() {
		return blocks;
	}

	/**
	 * @param blocks the blocks to set
	 */
	public void setBlocks(List<Block> blocks) {
		this.blocks = blocks;
	}

}
