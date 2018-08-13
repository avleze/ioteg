package com.ioteg.builders;

import java.util.List;

import org.jdom2.Element;

import com.ioteg.model.Field;
import com.ioteg.model.OptionalFields;

/**
 * This class is a builder which allows to build a OptionalFields from its definition in a XML Element.
 * @author Antonio Vélez Estévez
 */
public class OptionalFieldsBuilder {

	public OptionalFields build(Element optionalFieldsElement) {
		
		OptionalFields optionalFields = new OptionalFields();
		FieldBuilder fieldBuilder = new FieldBuilder();
		
		String mandatory = optionalFieldsElement.getAttributeValue("mandatory");
		
		optionalFields.setMandatory(mandatory);
		
		List<Element> fieldsElement = optionalFieldsElement.getChildren("field");
		
		for(Element fieldElement : fieldsElement)
		{
			Field field = fieldBuilder.build(fieldElement);
			optionalFields.getFields().add(field);
		}
		
		return optionalFields;
	}

}
