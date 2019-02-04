package com.ioteg.builders;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jdom2.Element;
import org.jdom2.JDOMException;

import com.ioteg.model.Field;
import com.ioteg.model.OptionalFields;

/**
 * This class is a builder which allows to build a OptionalFields from its
 * definition in a XML Element.
 * 
 * @author Antonio Vélez Estévez
 */
public class OptionalFieldsBuilder {

	private static final String FIELD_KEYWORD = "field";
	private static final String MANDATORY_KEYWORD = "mandatory";

	public OptionalFields build(Element optionalFieldsElement) throws JDOMException, IOException {

		OptionalFields optionalFields = new OptionalFields();
		FieldBuilder fieldBuilder = new FieldBuilder();

		String mandatory = optionalFieldsElement.getAttributeValue(MANDATORY_KEYWORD);

		optionalFields.setMandatory(mandatory);

		List<Element> fieldsElement = optionalFieldsElement.getChildren(FIELD_KEYWORD);
		List<Field> fieldsOfTheOptionalFields = new ArrayList<>();
		for (Element fieldElement : fieldsElement) {
			Field field = fieldBuilder.build(fieldElement);
			fieldsOfTheOptionalFields.add(field);
		}

		optionalFields.setFields(fieldsOfTheOptionalFields);

		return optionalFields;
	}

}
