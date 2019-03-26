package com.ioteg.builders;

import org.jdom2.Element;

import com.ioteg.model.Attribute;

/**
 * This class is a builder which allows to build an Attribute from its
 * definition in a XML Element.
 * 
 * @author Antonio Vélez Estévez
 */
public class AttributeBuilder {

	public Attribute build(Element attributeElement) {
		Attribute attribute = new Attribute();

		String type = attributeElement.getAttributeValue("type");
		String value = attributeElement.getAttributeValue("value");
		String min = attributeElement.getAttributeValue("min");
		String step = attributeElement.getAttributeValue("step");
		String unit = attributeElement.getAttributeValue("unit");
		String max = attributeElement.getAttributeValue("max");
		String precision = attributeElement.getAttributeValue("precision");
		String length = attributeElement.getAttributeValue("length");
		String strCase = attributeElement.getAttributeValue("case");
		String beginString = attributeElement.getAttributeValue("begin");
		String endString = attributeElement.getAttributeValue("end");
		String endcharacter = attributeElement.getAttributeValue("endcharacter");
		String format = attributeElement.getAttributeValue("format");
		String isNumeric = attributeElement.getAttributeValue("isnumeric");

		if (hasDefaultLength(type, length))
			length = "10";

		attribute.setType(type);
		attribute.setValue(value);
		attribute.setBegin(beginString);
		attribute.setEnd(endString);
		attribute.setStep(step);
		attribute.setUnit(unit);
		
		if (hasDefaultRangeFloat(type, min, max)) {
			min = "0";
			max = "10";
		} else if (hasDefaultRangeInteger(type, min, max) || hasDefaultRangeLong(type, min, max)) {
			min = "0";
			max = "9";
		}

		if (min != null)
			attribute.setMin(Double.valueOf(min));
		if (max != null)
			attribute.setMax(Double.valueOf(max));

		if (precision != null)
			attribute.setPrecision(Integer.valueOf(precision));

		if (length != null)
			attribute.setLength(Integer.valueOf(length));

		attribute.setCase(strCase);
		attribute.setEndcharacter(endcharacter);
		attribute.setFormat(format);
		attribute.setIsNumeric(Boolean.valueOf(isNumeric));

		return attribute;
	}

	private boolean hasDefaultLength(String type, String length) {
		return length == null && (type.equals("String") || type.equals("Alphanumeric"));
	}

	private boolean hasDefaultRangeFloat(String type, String min, String max) {
		return min == null && max == null && type.equals("Float");

	}

	private boolean hasDefaultRangeInteger(String type, String min, String max) {
		return min == null && max == null && type.equals("Integer");
	}
	
	private boolean hasDefaultRangeLong(String type, String min, String max) {
		return min == null && max == null && type.equals("Long");
	}
}
