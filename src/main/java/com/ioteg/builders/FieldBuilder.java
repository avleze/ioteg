package com.ioteg.builders;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jdom2.Element;
import org.jdom2.JDOMException;

import com.ioteg.model.Attribute;
import com.ioteg.model.Field;

/**
 * This class is a builder which allows to build an Field from its definition in
 * a XML Element.
 * 
 * @author Antonio Vélez Estévez
 */
public class FieldBuilder {

	public Field build(Element fieldElement) throws JDOMException, IOException {
		AttributeBuilder attributeBuilder = new AttributeBuilder();

		String name = fieldElement.getAttributeValue("name");
		String quotes = fieldElement.getAttributeValue("quotes");
		String chooseone = fieldElement.getAttributeValue("chooseone");
		String dependence = fieldElement.getAttributeValue("dependence");

		if (dependence == null)
			dependence = "false";

		Field field = new Field(name, Boolean.valueOf(quotes), attributeBuilder.build(fieldElement));

		field.setChooseone(Boolean.valueOf(chooseone));
		field.setDependence(dependence);

		buildSubFields(fieldElement, field);
		buildAttributes(fieldElement, field);

		CustomBehaviourBuilder customBehaviourBuilder = new CustomBehaviourBuilder();

		if (fieldElement.getAttributeValue("custom_behaviour") != null)
			field.setCustomBehaviour(customBehaviourBuilder.build(fieldElement));

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
	 * @throws IOException
	 * @throws JDOMException
	 */
	private void buildSubFields(Element fieldElement, Field field) throws JDOMException, IOException {
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
