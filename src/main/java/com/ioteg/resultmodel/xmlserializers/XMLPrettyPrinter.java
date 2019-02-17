package com.ioteg.resultmodel.xmlserializers;

import java.io.IOException;
import java.util.Collections;

public class XMLPrettyPrinter {
	private Integer indentationLevel;
	private Boolean activated;

	/**
	 * @param indentationLevel
	 * @param activated
	 */
	public XMLPrettyPrinter(Integer indentationLevel, Boolean activated) {
		super();
		this.indentationLevel = indentationLevel;
		this.activated = activated;
	}

	public void indent(XMLGenerator xmlGenerator) throws IOException {
		if (activated)
			xmlGenerator.writeRaw(String.join("", Collections.nCopies(indentationLevel, "  ")));
	}

	public void incrementIndentation() {
		if (activated)
			indentationLevel++;
	}

	public void reduceIndentation() {
		if (activated)
			indentationLevel--;
	}

	public void nextLine(XMLGenerator xmlGenerator) throws IOException {
		if (activated)
			xmlGenerator.writeRaw("\n");
	}
}
