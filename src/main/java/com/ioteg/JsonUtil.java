package com.ioteg;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Random;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;

public class JsonUtil extends EventGenerator {
	/**
	 * Read the xml structure to generate a json file. The json file contains feeds
	 * that are generated according to the defined structure
	 * 
	 * @param values   is a file that will contain the generated feeds
	 * @param document is the xml that has the structure
	 * @throws IOException   files exceptions
	 * @throws JDOMException
	 */
	public static void JsonFormatValues(FileWriter values, Document document) throws IOException, JDOMException {

		BufferedWriter bw = new BufferedWriter(values);
		StringBuilder sb = new StringBuilder();

		List<Element> list = document.getRootElement().getChildren("block");
		bw.write("{"); // The beginning of a json
		EventGenerator.iteration = 0;

		for (int i = 0; i < list.size(); i++) {
			Element blck = list.get(i);
			String nameblck = blck.getAttributeValue("name");
			bw.write("\"" + nameblck + "\":");

			if (blck.getAttributeValue("value") != null) {

				bw.write("{" + blck.getAttributeValue("value") + "}");
			}

			if (blck.getChildren().size() != 0 && blck.getAttributeValue("repeat") == null) {
				List<Element> fields = blck.getChildren();
				sb.append("{"); // The beginning of a field
				for (int e = 0; e < fields.size(); e++) {
					Element field = fields.get(e);
					sb.append(NormalFieldJson(field));
					if ((e < fields.size() - 1)) {
						sb.append(",");
					}
				}
				bw.write(sb.toString());
				sb.setLength(0);
				bw.write("}");
			}

			if (blck.getAttributeValue("repeat") != null) {
				bw.write("[");
				List<Element> fields = blck.getChildren(); // field or optionalfield
				EventGenerator.totalnumevents = Integer.parseInt(blck.getAttributeValue("repeat"));

				for (int r = 0; r < EventGenerator.totalnumevents; r++) { // Number of values to repeat
					sb.append("{"); // The beginning of a field

					for (int e = 0; e < fields.size(); e++) {
						Element field = fields.get(e);

						if (field.getName().equals("optionalfields")) {
							StringBuilder aux = OptionalFieldsJson(field);
							if (aux.length() == 0) {
								int sizelenth = sb.length();
								sb.deleteCharAt(sizelenth - 1);
							} else {
								sb.append(aux.toString());
							}
						} else {
							sb.append(NormalFieldJson(field));
						}
						if ((e < fields.size() - 1)) {
							sb.append(",");
						}
					}
					bw.write(sb.toString());
					sb.setLength(0);
					bw.write("}"); // The end of a field
					if (r < EventGenerator.totalnumevents - 1) {
						bw.write(",");
					}
					EventGenerator.iteration += 1;
				}
				bw.write("]");
			}

			if (i < list.size() - 1) {
				bw.write(",");
			}
		}
		bw.write("}");
		bw.close();
	}

	/**
	 * This function evaluates the normal field type element and obtains the
	 * structure of the json file to generate according to the attributes of the
	 * field
	 * 
	 * @param field is a normal field type element
	 * @return the structure of the field for the json
	 * @throws IOException
	 * @throws JDOMException
	 */
	public static StringBuilder NormalFieldJson(Element field) throws JDOMException, IOException {

		StringBuilder sb = new StringBuilder();
		sb.append("\"" + field.getAttributeValue("name") + "\":");
		String quotes = field.getAttributeValue("quotes");
		String type = field.getAttributeValue("type");
		String value = "";

		if (!ExistType(type)) { // A non basic type
			sb.append(GenerateValueComplexType(field, "json"));
		} else {// A basic type

			value = GenerateValueSimpleType(type, field);

			if (quotes.equals("true")) {
				sb.append("\"" + value + "\"");
			} else {
				sb.append(value);
			}
		}

		return sb;
	}

	/**
	 * This function evaluates the optional field type element and obtains the
	 * structure of the json file to generate according to the attributes of the
	 * field
	 * 
	 * @param field is a optional field type element
	 * @return the structure of the field for the json
	 * @throws IOException
	 * @throws JDOMException
	 */
	public static StringBuilder OptionalFieldsJson(Element field) throws JDOMException, IOException {

		List<Element> optional = field.getChildren();
		int size = optional.size();
		Random rand = new Random();
		String value = "";
		StringBuilder sb = new StringBuilder();
		sb.setLength(0);

		if (field.getAttributeValue("mandatory").equals("true")) {
			size--;
		}

		int chosen = rand.nextInt(size);

		if (chosen != size) {
			Element elementop = optional.get(chosen);
			sb.append("\"" + elementop.getAttributeValue("name") + "\":");
			String quotes = elementop.getAttributeValue("quotes");
			String type = elementop.getAttributeValue("type");

			if (!ExistType(type)) {
				sb.append(GenerateValueComplexType(elementop, "json"));
			} else {
				value = GenerateValueSimpleType(type, elementop);
				if (quotes.equals("true")) {
					sb.append("\"" + value + "\"");
				} else {
					sb.append(value);
				}
			}
		} else {
			sb.setLength(0);
		}

		return sb;
	}
}
