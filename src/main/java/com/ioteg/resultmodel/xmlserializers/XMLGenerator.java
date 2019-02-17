package com.ioteg.resultmodel.xmlserializers;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.Map;
import java.util.Map.Entry;

public class XMLGenerator {

	private Writer outputStreamWriter;
	private XMLPrettyPrinter xmlPrettyPrinter;

	/**
	 * @param stringBuilder
	 */
	public XMLGenerator(OutputStream out) {
		super();
		this.outputStreamWriter = new PrintWriter(out);
		this.xmlPrettyPrinter = new XMLPrettyPrinter(0, true);
	}

	public void writeStartField(String fieldName, Boolean isObject) throws IOException {
		xmlPrettyPrinter.indent(this);
		outputStreamWriter.append("<" + fieldName + ">");
		if(isObject)
		{
			xmlPrettyPrinter.incrementIndentation(this);
			xmlPrettyPrinter.nextLine(this);
		}
	}

	public void writeStartFieldWithAttributes(String fieldName, Map<String, String> attrs, Boolean isObject)
			throws IOException {
		xmlPrettyPrinter.indent(this);

		StringBuilder sb = new StringBuilder();
		sb.append("<" + fieldName + " ");
		for (Entry<String, String> attr : attrs.entrySet()) {
			writeAttribute(attr, sb);
			sb.append(" ");
		}
		sb.deleteCharAt(sb.length() - 1);
		sb.append(">");

		outputStreamWriter.append(sb.toString());

		if(isObject)
		{
			xmlPrettyPrinter.incrementIndentation(this);
			xmlPrettyPrinter.nextLine(this);
		}
	}

	private void writeAttribute(Entry<String, String> attr, StringBuilder sb) {
		sb.append(attr.getKey() + "=" + "\"" + attr.getValue() + "\"");
	}

	public void writeEndField(String fieldName, Boolean isObject) throws IOException {
		
		if(isObject)
		{
			xmlPrettyPrinter.reduceIndentation();
			xmlPrettyPrinter.indent(this);
		}

		outputStreamWriter.append("</" + fieldName + ">");
		
		xmlPrettyPrinter.nextLine(this);
	}

	public void writeRaw(String value) throws IOException {
		outputStreamWriter.append(value);
	}

	public void close() throws IOException {
		outputStreamWriter.close();
	}

}
