package com.ioteg.builders;

import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;

import com.ioteg.model.Block;
import com.ioteg.model.EventType;

public class EventTypeBuilder {
	
	public EventTypeBuilder() {
		
	}
	
	public EventType build(Document document) {
		EventType eventType = new EventType();
		BlockBuilder blockBuilder = new BlockBuilder();
		List<Element> blocks = document.getRootElement().getChildren("block");
		
		for(Element element : blocks)
		{
			Block block = blockBuilder.build(element);
			eventType.getBlocks().add(block);
		}
		
		return eventType;
	}
}
