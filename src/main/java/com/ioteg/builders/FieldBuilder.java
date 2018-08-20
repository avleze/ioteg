package com.ioteg.builders;

import java.util.ArrayList;
import java.util.List;

import org.jdom2.Element;

import com.ioteg.model.Field;
import com.ioteg.model.Attribute;


/**
 * This class is a builder which allows to build an Field from its definition in a XML Element.
 * @author Antonio Vélez Estévez
 */
public class FieldBuilder {
	
	public Field build(Element fieldElement) {
		Field field = new Field();

		String name = fieldElement.getAttributeValue("name");
		String quotes = fieldElement.getAttributeValue("quotes");
		String type = fieldElement.getAttributeValue("type");
		String value = fieldElement.getAttributeValue("value");
		String min = fieldElement.getAttributeValue("min");
		String max = fieldElement.getAttributeValue("max");
		String precision = fieldElement.getAttributeValue("precision");
		String length = fieldElement.getAttributeValue("length");
		String strCase = fieldElement.getAttributeValue("case"); 
		String endcharacter = fieldElement.getAttributeValue("endcharacter");
		String format = fieldElement.getAttributeValue("format");
		String isNumeric = fieldElement.getAttributeValue("isnumeric");
		String chooseone = fieldElement.getAttributeValue("chooseone");
		String dependence = fieldElement.getAttributeValue("dependence");
	
		if(dependence == null)
			dependence = "false";
		if(hasDefaultLength(type, length))
			length = "10";
		
		field.setName(name);
		field.setQuotes(Boolean.valueOf(quotes));
		field.setType(type);
		field.setValue(value);
		
		if(min != null)
			field.setMin(Double.valueOf(min));
		if(max != null)
			field.setMax(Double.valueOf(max));
		if(precision != null)
			field.setPrecision(Integer.valueOf(precision));
		if(length != null)
			field.setLength(Integer.valueOf(length));
		field.setCase(strCase);
		field.setEndcharacter(endcharacter);
		field.setFormat(format);
		field.setIsNumeric(Boolean.valueOf(isNumeric));
		field.setChooseone(Boolean.valueOf(chooseone));
		field.setDependence(dependence);
		
		buildSubFields(fieldElement, field);
		buildAttributes(fieldElement, field);
		
		return field;
	}

	/**
	 * @param fieldElement The XML element.
	 * @param field The field that is being built.
	 */
	private void buildAttributes(Element fieldElement, Field field) {
		AttributeBuilder attributeBuilder = new AttributeBuilder();
		List<Element> attributes = fieldElement.getChildren("attribute");
		List<Attribute> attributesOfTheField = new ArrayList<>();
		
		for(Element attributeEl : attributes) {
			Attribute attribute = attributeBuilder.build(attributeEl);
			attributesOfTheField.add(attribute);
		}
		
		field.setAttributes(attributesOfTheField);
	}

	/**
	 * @param fieldElement The XML element.
	 * @param field The field that is being built.
	 */
	private void buildSubFields(Element fieldElement, Field field) {
		FieldBuilder fieldBuilder = new FieldBuilder();
		List<Element> fields = fieldElement.getChildren("field");
		List<Field> fieldsOfTheField = new ArrayList<>();
		
		for(Element fieldEl : fields) {
			Field subField = fieldBuilder.build(fieldEl);
			fieldsOfTheField.add(subField);
		}
		
		field.setFields(fieldsOfTheField);
	}

	
	private boolean hasDefaultLength(String type, String length) {
		return length == null && (type.equals("String") || type.equals("Alphanumeric"));
	}
}
