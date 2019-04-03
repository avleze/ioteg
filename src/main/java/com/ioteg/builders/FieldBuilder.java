package com.ioteg.builders;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jdom2.Element;
import org.jdom2.JDOMException;

import com.ioteg.model.Attribute;
import com.ioteg.model.CustomBehaviour;
import com.ioteg.model.Field;

/**
 * This class is a builder which allows to build an Field from its definition in
 * a XML Element.
 *
 * @author Antonio Vélez Estévez
 * @version $Id: $Id
 */
public class FieldBuilder {

	/**
	 * <p>build.</p>
	 *
	 * @param fieldElement a {@link org.jdom2.Element} object.
	 * @return a {@link com.ioteg.model.Field} object.
	 * @throws org.jdom2.JDOMException if any.
	 * @throws java.io.IOException if any.
	 */
	public Field build(Element fieldElement) throws JDOMException, IOException {
		AttributeBuilder attributeBuilder = new AttributeBuilder();

		String name = fieldElement.getAttributeValue("name");
		String quotes = fieldElement.getAttributeValue("quotes");
		String chooseone = fieldElement.getAttributeValue("chooseone");
		String dependence = fieldElement.getAttributeValue("dependence");
		String injectable = fieldElement.getAttributeValue("injectable");
		Attribute attr = attributeBuilder.build(fieldElement);

		List<Field> subFields = buildSubFields(fieldElement);
		List<Attribute> attributes = buildAttributes(fieldElement);

		CustomBehaviourBuilder customBehaviourBuilder = new CustomBehaviourBuilder();
		CustomBehaviour customBehaviour = null;
		if (fieldElement.getAttributeValue("custom_behaviour") != null)
			customBehaviour = customBehaviourBuilder.build(fieldElement);

		Boolean chooseoneValue = null;
		if(chooseone != null)
			chooseoneValue = Boolean.valueOf(chooseone);
		
		Boolean quotesValue = null;
		if(quotes != null)
			quotesValue = Boolean.valueOf(quotes);
		
		return new Field(null, attr.getType(), attr.getValue(), attr.getMin(), attr.getStep(), attr.getUnit(), attr.getMax(),
				attr.getPrecision(), attr.getLength(), attr.getCase(), attr.getBegin(), attr.getEnd(),
				attr.getEndcharacter(), attr.getFormat(), attr.getIsNumeric(), name, quotesValue, chooseoneValue,
				dependence, Boolean.valueOf(injectable), subFields, attributes, customBehaviour);
	}

	/**
	 * @param fieldElement The XML element.
	 */
	private List<Attribute> buildAttributes(Element fieldElement) {
		AttributeBuilder attributeBuilder = new AttributeBuilder();
		List<Element> attributes = fieldElement.getChildren("attribute");
		List<Attribute> attributesOfTheField = new ArrayList<>();

		for (Element attributeEl : attributes) {
			Attribute attribute = attributeBuilder.build(attributeEl);
			attributesOfTheField.add(attribute);
		}

		return attributesOfTheField;
	}

	/**
	 * @param fieldElement The XML element.
	 * @throws IOException if any.
	 * @throws JDOMException if any.
	 */
	private List<Field> buildSubFields(Element fieldElement) throws JDOMException, IOException {
		FieldBuilder fieldBuilder = new FieldBuilder();
		List<Element> fields = fieldElement.getChildren("field");
		List<Field> fieldsOfTheField = new ArrayList<>();

		for (Element fieldEl : fields) {
			Field subField = fieldBuilder.build(fieldEl);
			fieldsOfTheField.add(subField);
		}

		return fieldsOfTheField;
	}
}
