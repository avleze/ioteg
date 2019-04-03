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
 * @version $Id: $Id
 */
public class OptionalFieldsBuilder {

	private static final String FIELD_KEYWORD = "field";
	private static final String MANDATORY_KEYWORD = "mandatory";

	/**
	 * <p>build.</p>
	 *
	 * @param optionalFieldsElement a {@link org.jdom2.Element} object.
	 * @return a {@link com.ioteg.model.OptionalFields} object.
	 * @throws org.jdom2.JDOMException if any.
	 * @throws java.io.IOException if any.
	 */
	public OptionalFields build(Element optionalFieldsElement) throws JDOMException, IOException {

		FieldBuilder fieldBuilder = new FieldBuilder();

		String mandatory = optionalFieldsElement.getAttributeValue(MANDATORY_KEYWORD);
		Boolean mandatoryValue = Boolean.valueOf(mandatory);
		
		List<Element> fieldsElement = optionalFieldsElement.getChildren(FIELD_KEYWORD);
		List<Field> fieldsOfTheOptionalFields = new ArrayList<>();
		for (Element fieldElement : fieldsElement) {
			Field field = fieldBuilder.build(fieldElement);
			fieldsOfTheOptionalFields.add(field);
		}


		return new OptionalFields(null, mandatoryValue, fieldsOfTheOptionalFields);
	}

}
