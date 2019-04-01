package com.ioteg.builders;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jdom2.Element;
import org.jdom2.JDOMException;

import com.ioteg.model.Block;
import com.ioteg.model.Field;
import com.ioteg.model.InjectedField;
import com.ioteg.model.OptionalFields;

/**
 * This class is a builder which allows to build an Block from its definition in
 * a XML Element.
 *
 * @author Antonio Vélez Estévez
 * @version $Id: $Id
 */
public class BlockBuilder {

	/**
	 * <p>build.</p>
	 *
	 * @param element The block element obtained with JDOM.
	 * @return A block.
	 * @throws java.io.IOException if any.
	 * @throws org.jdom2.JDOMException if any.
	 */
	public Block build(Element element) throws JDOMException, IOException {
		/* The block parameters are obtained and setted */
		String name = element.getAttributeValue("name");
		String value = element.getAttributeValue("value");
		String repetitionStr = element.getAttributeValue("repeat");
		Integer repetition = null;

		if (repetitionStr != null)
			repetition = Integer.valueOf(repetitionStr);

		/* Subfields and subOptionalFields are looked for */
		List<Field> subFields = buildSubFields(element);
		List<OptionalFields> subOptionalFields = buildSubOptionalsField(element);
		List<InjectedField> subInjectedFields = buildSubInjectedField(element);

		return new Block(name, value, repetition, subFields, subInjectedFields, subOptionalFields);
	}

	private List<Field> buildSubFields(Element element) throws JDOMException, IOException {
		FieldBuilder fieldBuilder = new FieldBuilder();
		List<Element> fields = element.getChildren("field");
		List<Field> fieldsOfTheBlock = new ArrayList<>();

		for (Element fieldElement : fields) {
			Field field = fieldBuilder.build(fieldElement);
			fieldsOfTheBlock.add(field);
		}

		return fieldsOfTheBlock;
	}

	private List<OptionalFields> buildSubOptionalsField(Element element) throws JDOMException, IOException {
		OptionalFieldsBuilder optionalFieldsBuilder = new OptionalFieldsBuilder();
		List<Element> optionalFields = element.getChildren("optionalfields");
		List<OptionalFields> optionalFieldsOfTheBlock = new ArrayList<>();

		for (Element optionalFieldElement : optionalFields) {
			OptionalFields optionalField = optionalFieldsBuilder.build(optionalFieldElement);
			optionalFieldsOfTheBlock.add(optionalField);
		}

		return optionalFieldsOfTheBlock;
	}

	private List<InjectedField> buildSubInjectedField(Element element) throws JDOMException, IOException {
		List<Element> injectedFields = element.getChildren("inject");
		List<InjectedField> injectedFieldsOfTheBlock = new ArrayList<>();

		for (Element injectedField : injectedFields)
			injectedFieldsOfTheBlock.add(new InjectedField(injectedField.getAttributeValue("name")));

		return injectedFieldsOfTheBlock;
	}

}
