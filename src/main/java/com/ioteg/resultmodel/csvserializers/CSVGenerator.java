package com.ioteg.resultmodel.csvserializers;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CSVGenerator {
	private Writer outputStreamWriter;
	private Map<String, Integer> headerPositions;
	private String[] values;

	/**
	 * @param stringBuilder
	 * @throws IOException 
	 */
	public CSVGenerator(OutputStream out, List<String> header) throws IOException {
		super();
		this.outputStreamWriter = new PrintWriter(out);
		this.headerPositions = new HashMap<>();
		int i = 0;
		for (String head : header)
			headerPositions.put(head, i++);
		values = new String[header.size()];
		
		
		for (i = 0; i < header.size() - 1; ++i)
			outputStreamWriter.append(header.get(i) + ",");
		
		outputStreamWriter.append(header.get(header.size() - 1) + "\n");
	}

	public void writeStartObject() {
		for (int i = 0; i < values.length; ++i)
			values[i] = null;
	}

	public void writeField(String fieldName, String value) {
		values[headerPositions.get(fieldName)] = value;
	}

	public void writeEndObject() throws IOException {

		for (int i = 0; i < values.length - 1; ++i) {
			String finalValueToWrite = "";
			if (values[i] != null)
				finalValueToWrite = values[i];
			outputStreamWriter.append(finalValueToWrite + ",");
		}

		String finalValueToWrite = "";
		int lastIndex = values.length - 1;
		if (values[lastIndex] != null)
			finalValueToWrite = values[lastIndex];

		outputStreamWriter.append(finalValueToWrite + "\n");
	}

	public void close() throws IOException {
		outputStreamWriter.close();
	}

}
