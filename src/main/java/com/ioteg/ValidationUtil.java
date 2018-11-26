package com.ioteg;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

public class ValidationUtil extends EventGenerator {

	public static Boolean ValidStandard(File xmlFile) throws JDOMException, IOException {

		SAXBuilder builder = new SAXBuilder();

		// To build a document from the xml
		Document document = (Document) builder.build(xmlFile);

		// To get the root
		Element rootNode = document.getRootElement();

		List<Element> list = rootNode.getChildren();

		Boolean valid = BlockElements(list);

		if (valid) {
			for (int i = 0; i < list.size() && valid; i++) {
				Element block = (Element) list.get(i);
				List<Element> fieldslist = block.getChildren();
				for (int j = 0; j < fieldslist.size() && valid; j++) {
					Element field = (Element) fieldslist.get(j);
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
			Element elem = (Element) list.get(i);
			if (elem.getName().equals("block")) {
				if (elem.getAttributeValue("name") == null) {
					System.err.println("The \"name\" attribute of the block tag is needed");
					valid = false;
				}
				if (elem.getAttribute("repeat") != null) {
					repeat++;
				}
			} else {
				System.err.println("The tags of the root element must be block");
				valid = false;
			}
		}

		if (repeat != 1) { // The attribute repeat means the definition of the feeds
			System.err.println("Just one \"repeat\" attribute per xml");
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
					Element op = (Element) optionalf.get(i);
					if (op.getName().equals("field")) {
						valid = (FieldElements(op) && valid);
					} else {
						System.err.println("The children of the \"optionalfields\" must be \"field\"");
						valid = false;
					}
				}
			} else {
				if (field.getAttributeValue("name") == null) {
					System.err.println("The \"field\" tag must have \"name\" attribute");
					valid = false;
				}
				if (field.getAttributeValue("quotes") == null) {
					System.err.println("The \"field\" tag must have \"quotes\" attribute");
					valid = false;
				}
				if (field.getAttributeValue("type") == null) {
					System.err.println("The \"field\" tag must have \"type\" attribute");
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
			System.err.println("Use \"ComplexType\" value to define a complex type");
			valid = false;
		} else {
			List<Element> complelems = field.getChildren();
			for (int i = 0; i < complelems.size() && valid; i++) {
				Element element = (Element) complelems.get(i);
				if (element.getName().equals("field")) {
					valid = FieldElements(element);
				}
				if (element.getName().equals("attribute")) {
					if (element.getAttributeValue("type") != null) {
						String attype = element.getAttributeValue("type");
						valid = SimpleType(element, attype);
					} else {
						System.err.println("It is needed the \"type\" attribute of an attribute");
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
			valid = ValidInteger(field);
			break;
		case "Float":
			valid = ValidFloat(field);
			break;
		case "Long":
			valid = ValidLong(field);
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
			System.err.println("The \"Time\" type must have a \"value\" or \"format\" attribute");
			valid = false;
		}

		return valid;
	}

	private static Boolean ValidDate(Element field) {
		Boolean valid = true;

		if ((field.getAttributeValue("value") == null) && (field.getAttributeValue("format") == null)) {
			System.err.println("The \"Date\" type must have a \"value\" or \"mode\" attribute");
			valid = false;
		}

		return valid;
	}

	private static Boolean ValidBoolean(Element field) {
		Boolean valid = true;

		if (field.getAttributeValue("value") == null) {
			if ((field.getAttributeValue("isnumeric") != null)
					&& (!field.getAttributeValue("isnumeric").equals("true"))) {
				System.err.println(
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
				System.err.println(
						"The default value of the case attribute works with capital letters, if you want lowercase asign \"low\" to case");
				valid = false;
			}
		}

		return valid;
	}

	private static Boolean ValidLong(Element field) {
		Boolean valid = true;

		if (field.getAttributeValue("value") == null) {
			if (field.getAttributeValue("max") != null) {
				if (field.getAttributeValue("min") == null) {
					System.err.println("It is needed a \"min\" attribute for the \"Long\" type");
					valid = false;
				}
			}
			if (field.getAttributeValue("min") != null) {
				if (field.getAttributeValue("max") == null) {
					System.err.println("It is needed a \"max\" attribute for the \"Long\" type");
					valid = false;
				}
			}
		}

		return valid;
	}

	private static Boolean ValidFloat(Element field) {
		Boolean valid = true;

		if (field.getAttributeValue("value") == null) {
			if (field.getAttributeValue("max") != null) {
				if (field.getAttributeValue("min") == null) {
					System.err.println("It is needed a \"min\" attribute for the \"Float\" type");
					valid = false;
				}
			}
			if (field.getAttributeValue("min") != null) {
				if (field.getAttributeValue("max") == null) {
					System.err.println("It is needed a \"max\" attribute for the \"Float\" type");
					valid = false;
				}
			}
		}

		return valid;
	}

	private static Boolean ValidInteger(Element field) {
		Boolean valid = true;

		if (field.getAttributeValue("value") == null) {
			if (field.getAttributeValue("max") != null) {
				if (field.getAttributeValue("min") == null) {
					System.err.println("It is needed a \"min\" attribute for the \"Integer\" type");
					valid = false;
				}
			}
			if (field.getAttributeValue("min") != null) {
				if (field.getAttributeValue("max") == null) {
					System.err.println("It is needed a \"max\" attribute for the \"Integer\" type");
					valid = false;
				}
			}
		}

		return valid;
	}
}
