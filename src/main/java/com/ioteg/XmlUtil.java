package com.ioteg;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;

public class XmlUtil extends EventGenerator {

	/**
	 * Read the xml structure to generate a xml file. The generated xml file
	 * contains feeds that are generated according to the defined structure
	 * 
	 * @param values   is a file that will contain the generated feeds
	 * @param document is the xml that has the structure
	 * @throws IOException   files exceptions
	 * @throws JDOMException
	 */
	public static void XmlFormatValues(FileWriter values, Document document) throws IOException, JDOMException {

		BufferedWriter bw = new BufferedWriter(values);
		StringBuilder sb = new StringBuilder();

		List<Element> list = document.getRootElement().getChildren("block");
		bw.write("<xml>\n"); // The beginning of a xml
		EventGenerator.iteration = 0;

		for (int i = 0; i < list.size(); i++) {
			Element blck = list.get(i);
			String nameblck = blck.getAttributeValue("name");
			bw.write("<" + nameblck + ">\n");

			if (blck.getAttributeValue("value") != null) {

				bw.write(blck.getAttributeValue("value"));
				bw.write("\n</" + nameblck + ">\n");
			}

			if (blck.getChildren().size() != 0 && blck.getAttributeValue("repeat") == null) {
				List<Element> fields = blck.getChildren();
				for (int e = 0; e < fields.size(); e++) {
					Element field = fields.get(e);
					sb.append(NormalFieldsXml(field));
					if ((e == fields.size() - 1)) {
						int lng = sb.length();
						sb.delete(lng - 1, lng);
					}
				}
				bw.write(sb.toString());
				sb.setLength(0);
				bw.write("\n</" + nameblck + ">\n");
			}

			if (blck.getAttributeValue("repeat") != null) {
				List<Element> fields = blck.getChildren(); // field or optionalfield
				EventGenerator.totalnumevents = Integer.parseInt(blck.getAttributeValue("repeat"));
				bw.write("<feeds>\n");
				for (int r = 0; r < EventGenerator.totalnumevents; r++) { // Number of values to repeat
					sb.append("<feed>\n"); // The beginning of a field

					for (int e = 0; e < fields.size(); e++) {
						Element field = fields.get(e);

						if (field.getName().equals("optionalfields")) {
							sb.append(OptionalFieldsXml(field));
						} else {
							sb.append(NormalFieldsXml(field));
						}
					}
					bw.write(sb.toString());
					sb.setLength(0);
					bw.write("</feed>\n"); // The end of a field
					EventGenerator.iteration += 1;
				}
				bw.write("</feeds>\n");
			}
		}
		bw.write("</xml>");
		bw.close();

	}

	/**
	 * This function evaluates the normal field type element and obtains the
	 * structure of the xml file to generate according to the attributes of the
	 * field
	 * 
	 * @param field is a normal field type element
	 * @return the structure of the field for the xml
	 * @throws IOException
	 * @throws JDOMException
	 */
	static StringBuilder NormalFieldsXml(Element field) throws JDOMException, IOException {

		StringBuilder sb = new StringBuilder();
		String value = "";

		sb.append("<" + field.getAttributeValue("name"));
		String quotes = field.getAttributeValue("quotes");
		String type = field.getAttributeValue("type");

		if (!existType(type)) { // A non basic type
			sb.append(GenerateValueComplexType(field, "xml"));
		} else {// A basic type

			sb.append(" type=\"" + field.getAttributeValue("type") + "\">");
			value = GenerateValueSimpleType(type, field);

			if (quotes.equals("true")) {
				sb.append("\"" + value + "\"");
			} else {
				sb.append(value);
			}
		}
		sb.append("</" + field.getAttributeValue("name") + ">\n");

		return sb;
	}

	/**
	 * This function evaluates the optional field type element and obtains the
	 * structure of the xml file to generate according to the attributes of the
	 * field
	 * 
	 * @param field is a optional field type element
	 * @return the structure of the field for the xml
	 * @throws IOException
	 * @throws JDOMException
	 */
	public static StringBuilder OptionalFieldsXml(Element field) throws JDOMException, IOException {

		List<Element> optional = field.getChildren();
		int size = optional.size();
		String value = "";
		StringBuilder sb = new StringBuilder();

		if (field.getAttributeValue("mandatory").equals("true")) {
			size--;
		}

		int chosen = r.nextInt(size);
		if (chosen != size) {
			Element elementop = optional.get(chosen);
			sb.append("<" + elementop.getAttributeValue("name"));
			String quotes = elementop.getAttributeValue("quotes");
			String type = elementop.getAttributeValue("type");

			if (!existType(type)) {
				sb.append(GenerateValueComplexType(elementop, "xml"));
			} else {
				sb.append(" type=\"" + elementop.getAttributeValue("type") + "\">");
				value = GenerateValueSimpleType(type, elementop);
				if (quotes.equals("true")) {
					sb.append("\"" + value + "\"");
				} else {
					sb.append(value);
				}
			}
			sb.append("</" + elementop.getAttributeValue("name") + ">\n");
		} else {
			sb.setLength(0);
		}
		return sb;
	}

}
