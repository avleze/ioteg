package com.ioteg;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Random;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;

public class CsvUtil extends EventGenerator {

	/**
	 * Read the xml structure to generate a csv file. The csv file contains feeds
	 * that are generated according to the defined structure
	 * 
	 * @param values   is a file that will contain the generated feeds
	 * @param document is the xml that has the structure
	 * @throws IOException   files exceptions
	 * @throws JDOMException
	 */
	public static void CsvFormatValues(FileWriter values, Document document) throws IOException, JDOMException {

		BufferedWriter bw = new BufferedWriter(values);
		StringBuilder sb = new StringBuilder();

		List<Element> list = document.getRootElement().getChildren("block");

		EventGenerator.iteration = 0;

		for (int i = 0; i < list.size(); i++) {
			Element blck = list.get(i);

			if (blck.getAttributeValue("repeat") != null) {
				List<Element> fields = blck.getChildren(); // field or optionalfield

				// To print the head of the file

				for (int f = 0; f < fields.size(); f++) {
					if (fields.get(f).getName().equals("optionalfields")) {
						List<Element> optionals = fields.get(f).getChildren();
						for (int o = 0; o < optionals.size(); o++) {
							sb.append(optionals.get(o).getAttributeValue("name"));

							if (o != optionals.size() - 1) {
								sb.append(",");
							}
						}
					} else {
						sb.append(NormalFieldCsvHead(fields.get(f)));

						if (f != fields.size() - 1) {
							sb.append(",");
						}
					}
				}
				bw.write(sb.toString() + "\n");
				sb.setLength(0);

				EventGenerator.totalnumevents = Integer.parseInt(blck.getAttributeValue("repeat"));

				for (int r = 0; r < EventGenerator.totalnumevents; r++) { // Number of values to repeat

					for (int e = 0; e < fields.size(); e++) {
						Element field = fields.get(e);

						if (field.getName().equals("optionalfields")) {
							StringBuilder aux = OptionalFieldCsv(field);
							if (aux.length() == 0) {
								int length = sb.length();
								sb.deleteCharAt(length - 1);
							} else {
								sb.append(aux);
							}
						} else {
							sb.append(NormalFieldCsv(field));
						}
						if ((e < fields.size() - 1)) {
							sb.append(",");
						}
					}
					bw.write(sb.toString());
					sb.setLength(0);
					bw.write("\n"); // The end of a field
					EventGenerator.iteration += 1;
				}
			}
		}
		bw.close();

	}

	/**
	 * This function evaluates the normal field type element and obtains the
	 * structure for the top of the csv file to write according to the field
	 * 
	 * @param field is a normal field type element
	 * @return the structure of the field for the top csv file
	 */
	private static StringBuilder NormalFieldCsvHead(Element field) {
		StringBuilder sb = new StringBuilder();
		String type = field.getAttributeValue("type");

		if (!ExistType(type)) { // A non basic type
			List<Element> elements = field.getChildren();
			Element first = elements.get(0);
			if (first.getName().equals("field")) {
				for (int e = 0; e < elements.size(); e++) {
					sb.append(field.getAttributeValue("name") + "." + NormalFieldCsvHead(elements.get(e)) + ",");
					if (e == elements.size() - 1) {
						int length = sb.length();
						sb.deleteCharAt(length - 1);
					}
				}
			} else {
				sb.append(field.getAttributeValue("name"));
			}
		} else {// A basic type
			sb.append(field.getAttributeValue("name"));
		}
		return sb;
	}

	/**
	 * This function evaluates the normal field type element and obtains the
	 * structure of the csv file to generate according to the attributes of the
	 * field
	 * 
	 * @param field is a normal field type element
	 * @return the structure of the field for the csv
	 * @throws IOException
	 * @throws JDOMException
	 */
	static Object NormalFieldCsv(Element field) throws JDOMException, IOException {
		StringBuilder sb = new StringBuilder();
		String quotes = field.getAttributeValue("quotes");
		String type = field.getAttributeValue("type");
		String value = "";

		if (!ExistType(type)) { // A non basic type
			sb.append(GenerateValueComplexType(field, "csv"));
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
	 * structure of the csv file to generate according to the attributes of the
	 * field
	 * 
	 * @param field is a optional field type element
	 * @return the structure of the field for the csv
	 * @throws IOException
	 * @throws JDOMException
	 */
	static StringBuilder OptionalFieldCsv(Element field) throws JDOMException, IOException {

		StringBuilder sb = new StringBuilder();
		List<Element> optional = field.getChildren();
		int size = optional.size();
		Random rand = new Random();
		String value = "";

		if (field.getAttributeValue("mandatory").equals("true")) {
			size--;
		}

		// TODO Revisar cuando el número de fields dentro de optionalfield son 2
		if (size == 1) {
			size++;
		}

		int chosen = rand.nextInt(((size - 0) + 1) + 0);
		if (chosen != size) {
			Element elementop = optional.get(chosen);
			String quotes = elementop.getAttributeValue("quotes");
			String type = elementop.getAttributeValue("type");

			for (int comma = 0; comma < chosen; comma++) {
				sb.append(",");
			}

			if (!ExistType(type)) {
				sb.append(GenerateValueComplexType(elementop, "csv"));
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
