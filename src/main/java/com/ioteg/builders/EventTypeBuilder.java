package com.ioteg.builders;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;

import com.ioteg.model.Block;
import com.ioteg.model.EventType;

/**
 * This class is a builder which allows to build an EventType from its
 * definition in a XML Document.
 *
 * @author Antonio Vélez Estévez
 * @version $Id: $Id
 */
public class EventTypeBuilder {

	/**
	 * <p>build.</p>
	 *
	 * @param document The XML document obtained with JDOM.
	 * @return An instance of a EventType.
	 * @throws java.io.IOException if any.
	 * @throws org.jdom2.JDOMException if any.
	 */
	public EventType build(Document document) throws JDOMException, IOException {
		String eventTypeName = document.getRootElement().getAttributeValue("name");
		BlockBuilder blockBuilder = new BlockBuilder();

		List<Block> blocksOfTheEvent = new ArrayList<>();

		List<Element> blocks = document.getRootElement().getChildren("block");
		
		for (Element element : blocks) {
			Block block = blockBuilder.build(element);
			blocksOfTheEvent.add(block);
		}

		return new EventType(null, eventTypeName, blocksOfTheEvent);
	}
}
