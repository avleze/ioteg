package com.ioteg.resultmodel.xmlserializers;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.Map;
import java.util.Map.Entry;

/**
 * <p>XMLGenerator class.</p>
 *
 * @author antonio
 * @version $Id: $Id
 */
public class XMLGenerator {

	private Writer outputStreamWriter;
	private XMLPrettyPrinter xmlPrettyPrinter;

	/**
	 * <p>Constructor for XMLGenerator.</p>
	 *
	 * @param out a {@link java.io.OutputStream} object.
	 */
	public XMLGenerator(OutputStream out) {
		super();
		this.outputStreamWriter = new PrintWriter(out);
		this.xmlPrettyPrinter = new XMLPrettyPrinter(0, true);
	}

	/**
	 * <p>writeStartField.</p>
	 *
	 * @param fieldName a {@link java.lang.String} object.
	 * @param isObject a {@link java.lang.Boolean} object.
	 * @throws java.io.IOException if any.
	 */
	public void writeStartField(String fieldName, Boolean isObject) throws IOException {
		xmlPrettyPrinter.indent(this);
		outputStreamWriter.append("<" + fieldName + ">");
		if(isObject)
		{
			xmlPrettyPrinter.incrementIndentation();
			xmlPrettyPrinter.nextLine(this);
		}
	}

	/**
	 * <p>writeStartFieldWithAttributes.</p>
	 *
	 * @param fieldName a {@link java.lang.String} object.
	 * @param attrs a {@link java.util.Map} object.
	 * @param isObject a {@link java.lang.Boolean} object.
	 * @throws java.io.IOException if any.
	 */
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
			xmlPrettyPrinter.incrementIndentation();
			xmlPrettyPrinter.nextLine(this);
		}
	}

	private void writeAttribute(Entry<String, String> attr, StringBuilder sb) {
		sb.append(attr.getKey() + "=" + "\"" + attr.getValue() + "\"");
	}

	/**
	 * <p>writeEndField.</p>
	 *
	 * @param fieldName a {@link java.lang.String} object.
	 * @param isObject a {@link java.lang.Boolean} object.
	 * @throws java.io.IOException if any.
	 */
	public void writeEndField(String fieldName, Boolean isObject) throws IOException {
		
		if(isObject)
		{
			xmlPrettyPrinter.reduceIndentation();
			xmlPrettyPrinter.indent(this);
		}

		outputStreamWriter.append("</" + fieldName + ">");
		
		xmlPrettyPrinter.nextLine(this);
	}

	/**
	 * <p>writeRaw.</p>
	 *
	 * @param value a {@link java.lang.String} object.
	 * @throws java.io.IOException if any.
	 */
	public void writeRaw(String value) throws IOException {
		outputStreamWriter.append(value);
	}

	/**
	 * <p>close.</p>
	 *
	 * @throws java.io.IOException if any.
	 */
	public void close() throws IOException {
		outputStreamWriter.close();
	}

}
