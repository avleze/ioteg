package com.ioteg.builders;

import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;

import com.ioteg.model.Block;
import com.ioteg.model.Field;
import com.ioteg.model.OptionalFields;

public class BlockBuilder {


	public BlockBuilder() {
	}

	public Block build(Element element) {
		Block block = new Block();
		FieldBuilder fieldBuilder = new FieldBuilder();
		OptionalFieldsBuilder optionalFieldBuilder = new OptionalFieldsBuilder();
		
		String name = element.getAttributeValue("name");
		String value = element.getAttributeValue("value");
		Integer repetition = Integer.valueOf(element.getAttributeValue("repeat"));
		
		block.setName(name);
		block.setValue(value);
		block.setRepetition(repetition);

		List<Element> fields = element.getChildren("field");
		
		for(Element fieldElement : fields)
		{
			Field field = fieldBuilder.build(fieldElement);
			block.getFields().add(field);
		}
		
		List<Element> optionalFields = element.getChildren("optionalfields");

		for(Element optionalFieldElement : optionalFields)
		{
			OptionalFields optionalField = optionalFieldBuilder.build(optionalFieldElement);
			block.getOptionalFields().add(optionalField);
		}
		
		return block;
	}
}
