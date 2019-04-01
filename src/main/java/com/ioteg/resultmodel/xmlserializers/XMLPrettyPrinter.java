package com.ioteg.resultmodel.xmlserializers;

import java.io.IOException;
import java.util.Collections;

/**
 * <p>XMLPrettyPrinter class.</p>
 *
 * @author antonio
 * @version $Id: $Id
 */
public class XMLPrettyPrinter {
	private Integer indentationLevel;
	private Boolean activated;

	/**
	 * <p>Constructor for XMLPrettyPrinter.</p>
	 *
	 * @param indentationLevel a {@link java.lang.Integer} object.
	 * @param activated a {@link java.lang.Boolean} object.
	 */
	public XMLPrettyPrinter(Integer indentationLevel, Boolean activated) {
		super();
		this.indentationLevel = indentationLevel;
		this.activated = activated;
	}

	/**
	 * <p>indent.</p>
	 *
	 * @param xmlGenerator a {@link com.ioteg.resultmodel.xmlserializers.XMLGenerator} object.
	 * @throws java.io.IOException if any.
	 */
	public void indent(XMLGenerator xmlGenerator) throws IOException {
		if (activated)
			xmlGenerator.writeRaw(String.join("", Collections.nCopies(indentationLevel, "  ")));
	}

	/**
	 * <p>incrementIndentation.</p>
	 */
	public void incrementIndentation() {
		if (activated)
			indentationLevel++;
	}

	/**
	 * <p>reduceIndentation.</p>
	 */
	public void reduceIndentation() {
		if (activated)
			indentationLevel--;
	}

	/**
	 * <p>nextLine.</p>
	 *
	 * @param xmlGenerator a {@link com.ioteg.resultmodel.xmlserializers.XMLGenerator} object.
	 * @throws java.io.IOException if any.
	 */
	public void nextLine(XMLGenerator xmlGenerator) throws IOException {
		if (activated)
			xmlGenerator.writeRaw("\n");
	}
}
