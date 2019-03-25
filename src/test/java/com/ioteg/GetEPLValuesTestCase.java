package com.ioteg;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

import org.jdom2.Document;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.ioteg.EventGenerator;
import com.ioteg.Trio;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.empty;

public class GetEPLValuesTestCase {
	private static File xmlFile;
	private static ClassLoader classLoader;

	@BeforeEach
	public void loadSchema() throws JDOMException, IOException {

		classLoader = GetEPLValuesTestCase.class.getClassLoader();
		xmlFile = new File(classLoader.getResource("./EPLSamples/testEplQuery.xml").getFile());
		EventGenerator.fieldvalues = new ArrayList<>();
	}

	@Test
	public void testEPLOperators() throws JDOMException, IOException, ParseException {
		SAXBuilder builder = new SAXBuilder();
		Document document = builder.build(xmlFile);
		EventGenerator.getEPLValues(classLoader.getResource("./EPLSamples/EPLQuery.epl").getPath(),
				document.getRootElement());

		Trio<String, String, String> trioToTest = EventGenerator.fieldvalues.get(0).get("field1").get(0);
		assertEquals("field1", trioToTest.getFirst());
		assertEquals("<", trioToTest.getSecond());
		assertEquals("3", trioToTest.getThird());

		trioToTest = EventGenerator.fieldvalues.get(0).get("field1").get(1);
		assertEquals("field1", trioToTest.getFirst());
		assertEquals(">", trioToTest.getSecond());
		assertEquals("4", trioToTest.getThird());

		trioToTest = EventGenerator.fieldvalues.get(0).get("field1").get(2);
		assertEquals("field1", trioToTest.getFirst());
		assertEquals("=", trioToTest.getSecond());
		assertEquals("5", trioToTest.getThird());

		trioToTest = EventGenerator.fieldvalues.get(0).get("field1").get(3);
		assertEquals("field1", trioToTest.getFirst());
		assertEquals("<=", trioToTest.getSecond());
		assertEquals("-6", trioToTest.getThird());

		trioToTest = EventGenerator.fieldvalues.get(0).get("field1").get(4);
		assertEquals("field1", trioToTest.getFirst());
		assertEquals(">=", trioToTest.getSecond());
		assertEquals("7", trioToTest.getThird());

		trioToTest = EventGenerator.fieldvalues.get(0).get("field1").get(5);
		assertEquals("field1", trioToTest.getFirst());
		assertEquals("!=", trioToTest.getSecond());
		assertEquals("8", trioToTest.getThird());
		
		trioToTest = EventGenerator.fieldvalues.get(0).get("field10").get(0);
		assertEquals("field10", trioToTest.getFirst());
		assertEquals("!=", trioToTest.getSecond());
		assertEquals("true", trioToTest.getThird());

	}

	@Test
	public void testEPLOrOperator() throws JDOMException, IOException, ParseException {
		SAXBuilder builder = new SAXBuilder();
		Document document = builder.build(xmlFile);
		EventGenerator.getEPLValues(classLoader.getResource("./EPLSamples/EPLQueryOrOperator.epl").getPath(),
				document.getRootElement());

		Trio<String, String, String> trioToTest = EventGenerator.fieldvalues.get(0).get("field1").get(0);
		assertEquals("field1", trioToTest.getFirst());
		assertEquals("<", trioToTest.getSecond());
		assertEquals("3", trioToTest.getThird());

		trioToTest = EventGenerator.fieldvalues.get(1).get("field1").get(0);
		assertEquals("field1", trioToTest.getFirst());
		assertEquals(">", trioToTest.getSecond());
		assertEquals("4", trioToTest.getThird());
	}

	@Test
	public void testEPLAndOperator() throws JDOMException, IOException, ParseException {
		SAXBuilder builder = new SAXBuilder();
		Document document = builder.build(xmlFile);
		EventGenerator.getEPLValues(classLoader.getResource("./EPLSamples/EPLQueryAndOperator.epl").getPath(),
				document.getRootElement());

		Trio<String, String, String> trioToTest = EventGenerator.fieldvalues.get(0).get("field1").get(0);
		assertEquals("field1", trioToTest.getFirst());
		assertEquals("<", trioToTest.getSecond());
		assertEquals("3", trioToTest.getThird());

		trioToTest = EventGenerator.fieldvalues.get(0).get("field1").get(1);
		assertEquals("field1", trioToTest.getFirst());
		assertEquals(">", trioToTest.getSecond());
		assertEquals("4", trioToTest.getThird());
	}

	@Test
	public void testEPLNested() throws JDOMException, IOException, ParseException {
		SAXBuilder builder = new SAXBuilder();
		Document document = builder.build(xmlFile);
		EventGenerator.getEPLValues(classLoader.getResource("./EPLSamples/EPLQueryNested.epl").getPath(),
				document.getRootElement());

		Trio<String, String, String> trioToTest = EventGenerator.fieldvalues.get(0).get("field1").get(0);
		assertThat("field1", equalTo(trioToTest.getFirst()));
		assertEquals("<", trioToTest.getSecond());
		assertEquals("3", trioToTest.getThird());

		trioToTest = EventGenerator.fieldvalues.get(0).get("field2").get(0);
		assertThat("field2", equalTo(trioToTest.getFirst()));
		assertEquals(">=", trioToTest.getSecond());
		assertEquals("50", trioToTest.getThird());
		
		trioToTest = EventGenerator.fieldvalues.get(0).get("field3").get(0);
		assertThat("field3", equalTo(trioToTest.getFirst()));
		assertEquals("!=", trioToTest.getSecond());
		assertEquals("A", trioToTest.getThird());
		
		trioToTest = EventGenerator.fieldvalues.get(0).get("field4").get(0);
		assertThat("field4", equalTo(trioToTest.getFirst()));
		assertEquals("=", trioToTest.getSecond());
		assertEquals("B", trioToTest.getThird());
		
		trioToTest = EventGenerator.fieldvalues.get(1).get("field1").get(0);
		assertThat("field1", equalTo(trioToTest.getFirst()));
		assertEquals("<", trioToTest.getSecond());
		assertEquals("3", trioToTest.getThird());

		trioToTest = EventGenerator.fieldvalues.get(1).get("field2").get(0);
		assertThat("field2", equalTo(trioToTest.getFirst()));
		assertEquals(">=", trioToTest.getSecond());
		assertEquals("50", trioToTest.getThird());
		
		trioToTest = EventGenerator.fieldvalues.get(1).get("field3").get(0);
		assertThat("field3", equalTo(trioToTest.getFirst()));
		assertEquals("!=", trioToTest.getSecond());
		assertEquals("A", trioToTest.getThird());
		
		trioToTest = EventGenerator.fieldvalues.get(1).get("field4").get(0);
		assertThat("field4", equalTo(trioToTest.getFirst()));
		assertEquals("=", trioToTest.getSecond());
		assertEquals("D", trioToTest.getThird());
	}
	
	@Test
	public void testEPLRemoveComplexType() throws JDOMException, IOException, ParseException {
		SAXBuilder builder = new SAXBuilder();
		Document document = builder.build(xmlFile);
		EventGenerator.getEPLValues(classLoader.getResource("./EPLSamples/ComplexFieldExamples/EPLComplexFieldQuery.epl").getPath(),
				document.getRootElement());

		assertThat(EventGenerator.fieldvalues, empty());
	}
}
