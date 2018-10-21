package com.ioteg.builders;

import java.util.ArrayList;
import java.util.List;

import org.jdom2.Element;

import com.ioteg.model.Attribute;
import com.ioteg.model.Field;

/**
 * This class is a builder which allows to build an Field from its definition in
 * a XML Element.
 * 
 * @author Antonio Vélez Estévez
 */
public class FieldBuilder {

	public Field build(Element fieldElement) {
		AttributeBuilder attributeBuilder = new AttributeBuilder();
		Field field = new Field(attributeBuilder.build(fieldElement));

		String name = fieldElement.getAttributeValue("name");
		String quotes = fieldElement.getAttributeValue("quotes");
		String chooseone = fieldElement.getAttributeValue("chooseone");
		String dependence = fieldElement.getAttributeValue("dependence");

		if (dependence == null)
			dependence = "false";

		field.setName(name);
		field.setQuotes(Boolean.valueOf(quotes));
		field.setChooseone(Boolean.valueOf(chooseone));
		field.setDependence(dependence);

		buildSubFields(fieldElement, field);
		buildAttributes(fieldElement, field);

		return field;
	}

	/**
	 * @param fieldElement The XML element.
	 * @param field        The field that is being built.
	 */
	private void buildAttributes(Element fieldElement, Field field) {
		AttributeBuilder attributeBuilder = new AttributeBuilder();
		List<Element> attributes = fieldElement.getChildren("attribute");
		List<Attribute> attributesOfTheField = new ArrayList<>();

		for (Element attributeEl : attributes) {
			Attribute attribute = attributeBuilder.build(attributeEl);
			attributesOfTheField.add(attribute);
		}

		field.setAttributes(attributesOfTheField);
	}

	/**
	 * @param fieldElement The XML element.
	 * @param field        The field that is being built.
	 */
	private void buildSubFields(Element fieldElement, Field field) {
		FieldBuilder fieldBuilder = new FieldBuilder();
		List<Element> fields = fieldElement.getChildren("field");
		List<Field> fieldsOfTheField = new ArrayList<>();

		for (Element fieldEl : fields) {
			Field subField = fieldBuilder.build(fieldEl);
			fieldsOfTheField.add(subField);
		}

		field.setFields(fieldsOfTheField);
	}

}
