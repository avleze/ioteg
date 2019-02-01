package com.ioteg;

import static org.junit.Assert.assertThat;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.ioteg.EventGenerator;

import static org.hamcrest.Matchers.matchesPattern;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;



public class StringGeneratorQueryRestrictionTestCase {

	private static File xmlFile;
	private static ClassLoader classLoader;
	private static List<Element> fields;

	@BeforeEach
	public void loadSchema() throws JDOMException, IOException {
		SAXBuilder builder = new SAXBuilder();
		classLoader = StringGeneratorQueryRestrictionTestCase.class.getClassLoader();
		xmlFile = new File(classLoader.getResource("./EPLSamples/testEplQuery.xml").getFile());
		Document document = builder.build(xmlFile);

		List<Element> blocks = document.getRootElement().getChildren("block");
		fields = blocks.get(0).getChildren("field");
		EventGenerator.fieldvalues = new ArrayList<>();
	}

	@Test
	public void testStringQueryRestrictionEqualOperator() throws IOException, JDOMException {
		SAXBuilder builder = new SAXBuilder();
		Document document = builder.build(xmlFile);
		EventGenerator.GetEPLValues(classLoader.getResource("./EPLSamples/StringOperatorExamples/EPLStringQueryEqualOperator.epl").getPath(),
				document.getRootElement());
		
		Element field = fields.get(22);

		String result = EventGenerator.GenerateValueSimpleType(field.getAttributeValue("type"), field);
		assertThat(result, equalTo("HOLA ESTO ES UNA PRUEBA"));
		assertThat(result.length(), equalTo(23));
	}
	
	@Test
	public void testStringQueryRestrictionNotEqualOperator() throws IOException, JDOMException {
		SAXBuilder builder = new SAXBuilder();
		Document document = builder.build(xmlFile);
		EventGenerator.GetEPLValues(classLoader.getResource("./EPLSamples/StringOperatorExamples/EPLStringQueryNotEqualOperator.epl").getPath(),
				document.getRootElement());
		
		Element field = fields.get(22);

		String result = EventGenerator.GenerateValueSimpleType(field.getAttributeValue("type"), field);
		assertThat(result, not("HOLA ESTO ES UNA PRUEBA"));
		assertThat(result.length(), equalTo(10));
	}
	
	@Test
	public void testStringQueryRestrictionNotEqualOperatorWithEndCharacter() throws IOException, JDOMException {
		SAXBuilder builder = new SAXBuilder();
		Document document = builder.build(xmlFile);
		EventGenerator.GetEPLValues(classLoader.getResource("./EPLSamples/StringOperatorExamples/EPLStringQueryNotEqualOperator.epl").getPath(),
				document.getRootElement());
		
		Element field = fields.get(23);

		String result = EventGenerator.GenerateValueSimpleType(field.getAttributeValue("type"), field);
		assertThat(result, not("HOLA ESTO ES UNA PRUEBA"));
		assertThat(result, matchesPattern("[ABCDEF]*"));
		assertThat(result.length(), equalTo(10));
	}
	
	@Test
	public void testStringQueryRestrictionNotEqualOperatorWithLength() throws IOException, JDOMException {
		SAXBuilder builder = new SAXBuilder();
		Document document = builder.build(xmlFile);
		EventGenerator.GetEPLValues(classLoader.getResource("./EPLSamples/StringOperatorExamples/EPLStringQueryNotEqualOperator.epl").getPath(),
				document.getRootElement());
		
		Element field = fields.get(24);

		String result = EventGenerator.GenerateValueSimpleType(field.getAttributeValue("type"), field);
		assertThat(result, not("HOLA ESTO ES UNA PRUEBA"));
		assertThat(result.length(), equalTo(12));
	}
	
	@Test
	public void testStringQueryRestrictionNotEqualOperatorWithLengthAndCase() throws IOException, JDOMException {
		SAXBuilder builder = new SAXBuilder();
		Document document = builder.build(xmlFile);
		EventGenerator.GetEPLValues(classLoader.getResource("./EPLSamples/StringOperatorExamples/EPLStringQueryNotEqualOperator.epl").getPath(),
				document.getRootElement());
		
		Element field = fields.get(26);

		String result = EventGenerator.GenerateValueSimpleType(field.getAttributeValue("type"), field);
		assertThat(result, not("HOLA ESTO ES UNA PRUEBA"));
		assertThat(result.length(), equalTo(12));
		assertThat(result, equalTo(result.toLowerCase()));
	}
	
	@Test
	public void testStringQueryRestrictionNotEqualOperatorWithEndCharacterAndCase() throws IOException, JDOMException {
		SAXBuilder builder = new SAXBuilder();
		Document document = builder.build(xmlFile);
		EventGenerator.GetEPLValues(classLoader.getResource("./EPLSamples/StringOperatorExamples/EPLStringQueryNotEqualOperator.epl").getPath(),
				document.getRootElement());
		
		Element field = fields.get(27);

		String result = EventGenerator.GenerateValueSimpleType(field.getAttributeValue("type"), field);
		assertThat(result, not("HOLA ESTO ES UNA PRUEBA"));
		assertThat(result, equalTo(result.toLowerCase()));
		assertThat(result, matchesPattern("[abcdef]*"));
	}
	
	@Test
	public void testStringQueryRestrictionNotEqualOperatorWithEndCharacterAndLength() throws IOException, JDOMException {
		SAXBuilder builder = new SAXBuilder();
		Document document = builder.build(xmlFile);
		EventGenerator.GetEPLValues(classLoader.getResource("./EPLSamples/StringOperatorExamples/EPLStringQueryNotEqualOperator.epl").getPath(),
				document.getRootElement());
		
		Element field = fields.get(25);

		String result = EventGenerator.GenerateValueSimpleType(field.getAttributeValue("type"), field);
		assertThat(result, not("HOLA ESTO ES UNA PRUEBA"));
		assertThat(result, matchesPattern("[ABCDEF]*"));
		assertThat(result.length(), equalTo(12));
	}


}
