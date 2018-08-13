package com.ioteg.builders;

import java.util.ArrayList;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;

import com.ioteg.model.Block;
import com.ioteg.model.EventType;

/**
 * This class is a builder which allows to build an EventType from its definition in a XML Document.
 * @author Antonio Vélez Estévez
 */
public class EventTypeBuilder {
	
	/**
	 * @param document The XML document obtained with JDOM.
	 * @return An instance of a EventType.
	 */
	public EventType build(Document document) {
		EventType eventType = new EventType();
		BlockBuilder blockBuilder = new BlockBuilder();
		
		List<Block> blocksOfTheEvent = new ArrayList<>();
		
		List<Element> blocks = document.getRootElement().getChildren("block");
		
		for(Element element : blocks)
		{
			Block block = blockBuilder.build(element);
			blocksOfTheEvent.add(block);
		}
		
		eventType.setBlocks(blocksOfTheEvent);

		return eventType;
	}
}
