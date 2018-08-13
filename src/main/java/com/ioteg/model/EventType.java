package com.ioteg.model;

import java.util.List;

/**
 * This class represents an event type.
 * @author Antonio Vélez Estévez
 */
public class EventType {
	private List<Block> blocks;
	
	public EventType() {
		
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
