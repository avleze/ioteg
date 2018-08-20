package com.ioteg.builders;

import java.util.ArrayList;
import java.util.List;

import org.jdom2.Element;

import com.ioteg.model.Block;
import com.ioteg.model.Field;
import com.ioteg.model.OptionalFields;

/**
 * This class is a builder which allows to build an Block from its definition in a XML Element.
 * @author Antonio Vélez Estévez
 */
public class BlockBuilder {


	/**
	 * @param element The block element obtained with JDOM.
	 * @return A block.
	 */
	public Block build(Element element) {
		Block block = new Block();
		
		/* The block parameters are obtained and setted */
		String name = element.getAttributeValue("name");
		String value = element.getAttributeValue("value");
		String repetitionStr = element.getAttributeValue("repeat");
		Integer repetition = null;

		if(repetitionStr != null)
			repetition = Integer.valueOf(repetitionStr);
		
		block.setName(name);
		block.setValue(value);
		block.setRepetition(repetition);

		/* Subfields and subOptionalFields are looked for */
		buildSubFields(element, block);
		buildSubOptionalsField(element, block);
		
		return block;
	}

	private void buildSubFields(Element element, Block block) {
		FieldBuilder fieldBuilder = new FieldBuilder();
		List<Element> fields = element.getChildren("field");
		List<Field> fieldsOfTheBlock = new ArrayList<>();
		
		for(Element fieldElement : fields)
		{
			Field field = fieldBuilder.build(fieldElement);
			fieldsOfTheBlock.add(field);
		}
		
		block.setFields(fieldsOfTheBlock);
	}
	
	private void buildSubOptionalsField(Element element, Block block) {
		OptionalFieldsBuilder optionalFieldsBuilder = new OptionalFieldsBuilder();
		List<Element> optionalFields = element.getChildren("optionalfields");
		List<OptionalFields> optionalFieldsOfTheBlock = new ArrayList<>();

		for(Element optionalFieldElement : optionalFields)
		{
			OptionalFields optionalField = optionalFieldsBuilder.build(optionalFieldElement);
			optionalFieldsOfTheBlock.add(optionalField);
		}
		
		block.setOptionalFields(optionalFieldsOfTheBlock);
	}


}
