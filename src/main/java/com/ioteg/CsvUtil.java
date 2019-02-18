package com.ioteg;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;

import com.ioteg.exprlang.ExprParser.ExprLangParsingException;
import com.ioteg.generators.exceptions.NotExistingGeneratorException;

public class CsvUtil extends EventGenerator {

	/**
	 * Read the xml structure to generate a csv file. The csv file contains feeds
	 * that are generated according to the defined structure
	 * 
	 * @param values   is a file that will contain the generated feeds
	 * @param document is the xml that has the structure
	 * @throws IOException   files exceptions
	 * @throws JDOMException
	 * @throws ExprLangParsingException 
	 * @throws NotExistingGeneratorException 
	 */
	public static void csvFormatValues(FileWriter values, Document document) throws IOException, JDOMException, NotExistingGeneratorException, ExprLangParsingException {

		BufferedWriter bw = new BufferedWriter(values);
		StringBuilder sb = new StringBuilder();

		List<Element> list = document.getRootElement().getChildren("block");

		EventGenerator.iteration = 0;

		for (int i = 0; i < list.size(); i++) {
			Element blck = list.get(i);
			Integer repetitions = 1;

			EventGenerator.totalnumevents = repetitions;
			if (blck.getAttributeValue("repeat") != null) {
				EventGenerator.totalnumevents = Integer.valueOf(blck.getAttributeValue("repeat"));
			}
			List<Element> fields = blck.getChildren(); // field or optionalfield

			// To print the head of the file

			for (int f = 0; f < fields.size(); f++) {
				if (fields.get(f).getName().equals("optionalfields")) {
					List<Element> optionals = fields.get(f).getChildren();
					for (int o = 0; o < optionals.size(); o++) {
						sb.append(optionals.get(o).getAttributeValue("name"));
						sb.append(",");

						if (o != optionals.size() - 1) {
							sb.append(",");
						}
					}
				} else {
					sb.append(normalFieldCsvHead(fields.get(f)));

					if (f != fields.size() - 1) {
						sb.append(",");
					}
				}
			}
			bw.write(sb.toString() + "\n");
			sb.setLength(0);

			for (int r = 0; r < EventGenerator.totalnumevents; r++) { // Number of values to repeat

				for (int e = 0; e < fields.size(); e++) {
					Element field = fields.get(e);

					if (field.getName().equals("optionalfields")) {
						StringBuilder aux = optionalFieldCsv(field);
						if (aux.length() == 0) {
							int length = sb.length();
							sb.deleteCharAt(length - 1);
						} else {
							sb.append(aux);
						}
					} else {
						sb.append(normalFieldCsv(field));
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
		bw.close();

	}

	/**
	 * This function evaluates the normal field type element and obtains the
	 * structure for the top of the csv file to write according to the field
	 * 
	 * @param field is a normal field type element
	 * @return the structure of the field for the top csv file
	 */
	private static StringBuilder normalFieldCsvHead(Element field) {
		StringBuilder sb = new StringBuilder();
		String type = field.getAttributeValue("type");

		if (!existType(type)) { // A non basic type
			List<Element> elements = field.getChildren();
			Element first = elements.get(0);
			if (first.getName().equals("field")) {
				for (int e = 0; e < elements.size(); e++) {
					sb.append(field.getAttributeValue("name") + "." + normalFieldCsvHead(elements.get(e)) + ",");
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
	 * @throws ExprLangParsingException 
	 * @throws NotExistingGeneratorException 
	 */
	static Object normalFieldCsv(Element field) throws JDOMException, IOException, NotExistingGeneratorException, ExprLangParsingException {
		StringBuilder sb = new StringBuilder();
		String quotes = field.getAttributeValue("quotes");
		String type = field.getAttributeValue("type");
		String value = "";

		if (!existType(type)) { // A non basic type
			sb.append(generateValueComplexType(field, "csv"));
		} else {// A basic type

			value = generateValueSimpleType(field);

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
	 * @throws ExprLangParsingException 
	 * @throws NotExistingGeneratorException 
	 */
	static StringBuilder optionalFieldCsv(Element field) throws JDOMException, IOException, NotExistingGeneratorException, ExprLangParsingException {

		StringBuilder sb = new StringBuilder();
		List<Element> optional = field.getChildren();
		int size = optional.size();
		String value = "";

		if (field.getAttributeValue("mandatory").equals("true")) {
			size--;
		}

		int chosen = r.nextInt(size);
		if (chosen != size) {
			Element elementop = optional.get(chosen);
			String quotes = elementop.getAttributeValue("quotes");
			String type = elementop.getAttributeValue("type");

			for (int comma = 0; comma < chosen; comma++) {
				sb.append(",");
			}

			if (!existType(type)) {
				sb.append(generateValueComplexType(elementop, "csv"));
			} else {
				value = generateValueSimpleType(elementop);
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
