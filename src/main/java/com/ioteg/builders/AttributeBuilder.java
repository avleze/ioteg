package com.ioteg.builders;

import org.jdom2.Element;

import com.ioteg.model.Attribute;

/**
 * This class is a builder which allows to build an Attribute from its definition in a XML Element.
 * @author Antonio Vélez Estévez
 */
public class AttributeBuilder {

	public Attribute build(Element attributeElement) {
		Attribute attribute = new Attribute();
		
		String type = attributeElement.getAttributeValue("type");
		String value = attributeElement.getAttributeValue("value");
		String min = attributeElement.getAttributeValue("min");
		String max = attributeElement.getAttributeValue("max");
		String precision = attributeElement.getAttributeValue("precision");
		String length = attributeElement.getAttributeValue("length");
		String case_ = attributeElement.getAttributeValue("case"); 
		String endcharacter = attributeElement.getAttributeValue("endcharacter");
		String format = attributeElement.getAttributeValue("format");
		String isNumeric = attributeElement.getAttributeValue("isnumeric");
		
		attribute.setType(type);
		attribute.setValue(value);
		attribute.setMin(min);
		attribute.setMax(max);
		attribute.setPrecision(precision);
		attribute.setLength(length);
		attribute.setCase(case_);
		attribute.setEndcharacter(endcharacter);
		attribute.setFormat(format);
		attribute.setIsNumeric(isNumeric);
		
		return attribute;
	}
	
}
