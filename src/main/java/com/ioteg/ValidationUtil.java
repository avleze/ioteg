package com.ioteg;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

public class ValidationUtil extends EventGenerator {

	private static Logger logger = Logger.getRootLogger();
	
	public static Boolean ValidStandard(File xmlFile) throws JDOMException, IOException {

		SAXBuilder builder = new SAXBuilder();

		// To build a document from the xml
		Document document = builder.build(xmlFile);

		// To get the root
		Element rootNode = document.getRootElement();

		List<Element> list = rootNode.getChildren();

		Boolean valid = BlockElements(list);

		if (valid) {
			for (int i = 0; i < list.size() && valid; i++) {
				Element block = list.get(i);
				List<Element> fieldslist = block.getChildren();
				for (int j = 0; j < fieldslist.size() && valid; j++) {
					Element field = fieldslist.get(j);
					valid = FieldElements(field);
				}
			}
		}

		return valid;
	}

	private static Boolean BlockElements(List<Element> list) {
		Boolean valid = true;
		int repeat = 0;

		for (int i = 0; i < list.size() && valid; i++) {
			Element elem = list.get(i);
			if (elem.getName().equals("block")) {
				if (elem.getAttributeValue("name") == null) {
					logger.error("The \"name\" attribute of the block tag is needed");
					valid = false;
				}
				if (elem.getAttribute("repeat") != null) {
					repeat++;
				}
			} else {
				logger.error("The tags of the root element must be block");
				valid = false;
			}
		}

		if (repeat != 1) { // The attribute repeat means the definition of the feeds
			logger.error("Just one \"repeat\" attribute per xml");
			valid = false;
		}

		return valid;
	}

	private static Boolean FieldElements(Element field) {

		Boolean valid = true;

		if ((field.getName().equals("optionalfields")) || (field.getName().equals("field"))) {
			if (field.getName().equals("optionalfields")) {
				List<Element> optionalf = field.getChildren();
				for (int i = 0; i < optionalf.size(); i++) {
					Element op = optionalf.get(i);
					if (op.getName().equals("field")) {
						valid = (FieldElements(op) && valid);
					} else {
						logger.error("The children of the \"optionalfields\" must be \"field\"");
						valid = false;
					}
				}
			} else {
				if (field.getAttributeValue("name") == null) {
					logger.error("The \"field\" tag must have \"name\" attribute");
					valid = false;
				}
				if (field.getAttributeValue("quotes") == null) {
					logger.error("The \"field\" tag must have \"quotes\" attribute");
					valid = false;
				}
				if (field.getAttributeValue("type") == null) {
					logger.error("The \"field\" tag must have \"type\" attribute");
					valid = false;
				} else {
					String type = field.getAttributeValue("type");

					if (!ExistType(type)) {
						valid = (ComplexType(field, type) && valid);
					} else {
						valid = (SimpleType(field, type) && valid);
					}
				}
			}
		} else {
			valid = false;
		}

		return valid;
	}

	private static Boolean ComplexType(Element field, String type) {

		Boolean valid = true;

		if (!type.equals("ComplexType")) {
			logger.error("Use \"ComplexType\" value to define a complex type");
			valid = false;
		} else {
			List<Element> complelems = field.getChildren();
			for (int i = 0; i < complelems.size() && valid; i++) {
				Element element = complelems.get(i);
				if (element.getName().equals("field")) {
					valid = FieldElements(element);
				}
				if (element.getName().equals("attribute")) {
					if (element.getAttributeValue("type") != null) {
						String attype = element.getAttributeValue("type");
						valid = SimpleType(element, attype);
					} else {
						logger.error("It is needed the \"type\" attribute of an attribute");
						valid = false;
					}
				}
			}
		}

		return valid;
	}

	private static Boolean SimpleType(Element field, String type) {

		Boolean valid = true;

		switch (type) {
		case "Integer":
		case "Long":
		case "Float":
			valid = ValidNumericField(field);
			break;
		case "String":
			valid = ValidString(field);
			break;
		case "Boolean":
			valid = ValidBoolean(field);
			break;
		case "Date":
			valid = ValidDate(field);
			break;
		case "Time":
			valid = ValidTime(field);
			break;
		}

		return valid;

	}

	private static Boolean ValidTime(Element field) {
		Boolean valid = true;

		if ((field.getAttributeValue("value") == null) && (field.getAttributeValue("format") == null)) {
			logger.error("The \"Time\" type must have a \"value\" or \"format\" attribute");
			valid = false;
		}

		return valid;
	}

	private static Boolean ValidDate(Element field) {
		Boolean valid = true;

		if ((field.getAttributeValue("value") == null) && (field.getAttributeValue("format") == null)) {
			logger.error("The \"Date\" type must have a \"value\" or \"mode\" attribute");
			valid = false;
		}

		return valid;
	}

	private static Boolean ValidBoolean(Element field) {
		Boolean valid = true;

		if (field.getAttributeValue("value") == null) {
			if ((field.getAttributeValue("isnumeric") != null)
					&& (!field.getAttributeValue("isnumeric").equals("true"))) {
				logger.error(
						"The default value of the isnumeric attribute works with characters (\"true\" or \"false\"), if you want numbers asign \"true\" to isnumeric");
				valid = false;
			}
		}

		return valid;
	}

	private static Boolean ValidString(Element field) {
		Boolean valid = true;

		if (field.getAttributeValue("value") == null) {
			if ((field.getAttributeValue("case") != null) && (!field.getAttributeValue("case").equals("low"))) {
				logger.error(
						"The default value of the case attribute works with capital letters, if you want lowercase asign \"low\" to case");
				valid = false;
			}
		}

		return valid;
	}

	private static Boolean ValidNumericField(Element field) {
		Boolean valid = true;

		if (field.getAttributeValue("value") == null) {
			if (field.getAttributeValue("max") != null) {
				if (field.getAttributeValue("min") == null) {
					logger.error(String.format("It is needed a \"min\" attribute for the \"{1}\" type", field.getAttributeValue("type")));
					valid = false;
				}
			}
			if (field.getAttributeValue("min") != null) {
				if (field.getAttributeValue("max") == null) {
					logger.error(String.format("It is needed a \"max\" attribute for the \"{1}\" type", field.getAttributeValue("type")));
					valid = false;
				}
			}
		}

		return valid;
	}
}
