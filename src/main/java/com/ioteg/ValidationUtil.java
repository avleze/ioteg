package com.ioteg;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

public class ValidationUtil {

	private static final String CASE_ATTR = "case";
	private static final String ISNUMERIC_ATTR = "isnumeric";
	private static final String MIN_ATTR = "min";
	private static final String MAX_ATTR = "max";
	private static final String FORMAT_ATTR = "format";
	private static final String VALUE_ATTR = "value";
	private static final String TYPE_ATTR = "type";
	private static final String QUOTES_ATTR = "quotes";
	private static final String NAME_TAG = "name";
	private static final String FIELD_TAG = "field";
	private static final String OPTIONALFIELDS_TAG = "optionalfields";
	private static Logger logger = Logger.getRootLogger();

	public static Boolean validStandart(File xmlFile) throws JDOMException, IOException {

		SAXBuilder builder = new SAXBuilder();

		// To build a document from the xml
		Document document = builder.build(xmlFile);

		// To get the root
		Element rootNode = document.getRootElement();

		List<Element> list = rootNode.getChildren();

		Boolean valid = validateBlockElements(list);

		for (Element block : list)
			for (Element field : block.getChildren())
				valid = validateFieldElements(field) && valid;

		return valid;
	}

	private static Boolean validateBlockElements(List<Element> list) {
		Boolean valid = true;
		int repeat = 0;

		for (Element elem : list) {
			if (elem.getName().equals("block")) {
				if (elem.getAttributeValue(NAME_TAG) == null) {
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

	private static Boolean validateFieldElements(Element field) {

		Boolean valid = true;

		if (field.getName().equals(OPTIONALFIELDS_TAG))
			valid = validateOptionalFieldsElements(field, valid);
		else if (field.getName().equals(FIELD_TAG)) {
			valid = validateSingleField(field, valid);
		} else
			valid = false;

		return valid;
	}

	private static Boolean validateSingleField(Element field, Boolean valid) {
		if (field.getAttributeValue(NAME_TAG) == null) {
			logger.error("The \"field\" tag must have \"name\" attribute");
			valid = false;
		}
		if (field.getAttributeValue(QUOTES_ATTR) == null) {
			logger.error("The \"field\" tag must have \"quotes\" attribute");
			valid = false;
		}
		if (field.getAttributeValue(TYPE_ATTR) == null) {
			logger.error("The \"field\" tag must have \"type\" attribute");
			valid = false;
		} else {
			String type = field.getAttributeValue(TYPE_ATTR);

			if (EventGenerator.existType(type))
				valid = validateSimpleType(field, type) && valid;
			else
				valid = validateComplexType(field, type) && valid;
		}
		return valid;
	}

	private static Boolean validateOptionalFieldsElements(Element field, Boolean valid) {
		for (Element op : field.getChildren()) {
			if (op.getName().equals(FIELD_TAG))
				valid = validateFieldElements(op) && valid;
			else {
				logger.error("The children of the \"optionalfields\" must be \"field\"");
				valid = false;
			}
		}
		return valid;
	}

	private static Boolean validateComplexType(Element field, String type) {

		Boolean valid = true;

		if (!type.equals("ComplexType")) {
			logger.error("Use \"ComplexType\" value to define a complex type");
			valid = false;
		} else {
			for (Element element : field.getChildren()) {
				if (element.getName().equals(FIELD_TAG))
					valid = validateFieldElements(element);
				else if (element.getName().equals("attribute")) {
					if (element.getAttributeValue(TYPE_ATTR) != null) {
						String attype = element.getAttributeValue(TYPE_ATTR);
						valid = validateSimpleType(element, attype);
					} else {
						logger.error("It is needed the \"type\" attribute of an attribute");
						valid = false;
					}
				}
			}
		}

		return valid;
	}

	private static Boolean validateSimpleType(Element field, String type) {

		Boolean valid = true;

		switch (type) {
		case "Integer":
		case "Long":
		case "Float":
			valid = validateNumericField(field);
			break;
		case "String":
			valid = validateString(field);
			break;
		case "Boolean":
			valid = validateBoolean(field);
			break;
		case "Date":
			valid = validateDate(field);
			break;
		case "Time":
			valid = validateTime(field);
			break;
		}

		return valid;

	}

	private static Boolean validateTime(Element field) {
		Boolean valid = true;

		if (hasNoValueOrFormatAttribute(field)) {
			logger.error("The \"Time\" type must have a \"value\" or \"format\" attribute");
			valid = false;
		}

		return valid;
	}

	private static Boolean validateDate(Element field) {
		Boolean valid = true;

		if (hasNoValueOrFormatAttribute(field)) {
			logger.error("The \"Date\" type must have a \"value\" or \"mode\" attribute");
			valid = false;
		}

		return valid;
	}

	private static Boolean validateBoolean(Element field) {
		Boolean valid = true;

		if (field.getAttributeValue(VALUE_ATTR) == null && hasBadIsNumericValue(field)) {
			logger.error(
					"The default value of the isnumeric attribute works with characters (\"true\" or \"false\"), if you want numbers asign \"true\" to isnumeric");
			valid = false;
		}

		return valid;
	}

	private static Boolean validateString(Element field) {
		Boolean valid = true;

		if (field.getAttributeValue(VALUE_ATTR) == null && hasBadCaseValue(field)) {
			logger.error(
					"The default value of the case attribute works with capital letters, if you want lowercase asign \"low\" to case");
			valid = false;
		}

		return valid;
	}

	private static Boolean validateNumericField(Element field) {
		Boolean valid = true;

		if (field.getAttributeValue(VALUE_ATTR) == null) {
			if (hasMaxAttributeButNoMinAttribute(field)) {
				logger.error(String.format("It is needed a \"min\" attribute for the \"%s\" type",
						field.getAttributeValue(TYPE_ATTR)));
				valid = false;
			}
			if (hasMinAttributeButNoMaxAttribute(field)) {
				logger.error(String.format("It is needed a \"max\" attribute for the \"%s\" type",
						field.getAttributeValue(TYPE_ATTR)));
				valid = false;
			}
		}

		return valid;
	}

	private static boolean hasNoValueOrFormatAttribute(Element field) {
		return (field.getAttributeValue(VALUE_ATTR) == null) && (field.getAttributeValue(FORMAT_ATTR) == null);
	}

	private static boolean hasBadIsNumericValue(Element field) {
		return (field.getAttributeValue(ISNUMERIC_ATTR) != null)
				&& (!field.getAttributeValue(ISNUMERIC_ATTR).equals("true"));
	}

	private static boolean hasMinAttributeButNoMaxAttribute(Element field) {
		return field.getAttributeValue(MIN_ATTR) != null && field.getAttributeValue(MAX_ATTR) == null;
	}

	private static boolean hasMaxAttributeButNoMinAttribute(Element field) {
		return field.getAttributeValue(MAX_ATTR) != null && field.getAttributeValue(MIN_ATTR) == null;
	}

	private static boolean hasBadCaseValue(Element field) {
		return (field.getAttributeValue(CASE_ATTR) != null) && (!field.getAttributeValue(CASE_ATTR).equals("low"));
	}
}
