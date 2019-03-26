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

		String type = attributeElement.getAttributeValue("type");
		String value = attributeElement.getAttributeValue("value");
		String min = attributeElement.getAttributeValue("min");
		String step = attributeElement.getAttributeValue("step");
		String unit = attributeElement.getAttributeValue("unit");
		String max = attributeElement.getAttributeValue("max");
		String precision = attributeElement.getAttributeValue("precision");
		String length = attributeElement.getAttributeValue("length");
		String strCase = attributeElement.getAttributeValue("case");
		String begin = attributeElement.getAttributeValue("begin");
		String end = attributeElement.getAttributeValue("end");
		String endcharacter = attributeElement.getAttributeValue("endcharacter");
		String format = attributeElement.getAttributeValue("format");
		String isNumeric = attributeElement.getAttributeValue("isnumeric");

		if (hasDefaultLength(type, length))
			length = "10";
		
		if (hasDefaultRangeFloat(type, min, max)) {
			min = "0";
			max = "10";
		} else if (hasDefaultRangeInteger(type, min, max) || hasDefaultRangeLong(type, min, max)) {
			min = "0";
			max = "9";
		}
		Double minValue = null;
		if (min != null)
			minValue = Double.valueOf(min);
		
		Double maxValue = null;
		if (max != null)
			maxValue = Double.valueOf(max);

		Integer precisionValue = null;
		if (precision != null)
			precisionValue = Integer.valueOf(precision);

		Integer lengthValue = null;
		if (length != null)
			lengthValue = Integer.valueOf(length);
		
		return new Attribute(type, value, minValue, step, unit, maxValue, precisionValue, lengthValue, strCase, begin, end, endcharacter, format, Boolean.valueOf(isNumeric));
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
