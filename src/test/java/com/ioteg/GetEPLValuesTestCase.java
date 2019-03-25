package com.ioteg;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

import org.jdom2.Document;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.ioteg.eplutils.EPLConditionsParser;
import com.ioteg.eplutils.Trio;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.empty;

public class GetEPLValuesTestCase {
	private static File xmlFile;
	private static ClassLoader classLoader;

	@BeforeEach
	public void loadSchema() throws JDOMException, IOException {

		classLoader = GetEPLValuesTestCase.class.getClassLoader();
		xmlFile = new File(classLoader.getResource("./EPLSamples/testEplQuery.xml").getFile());
	}

	@Test
	public void testEPLOperators() throws JDOMException, IOException, ParseException {
		SAXBuilder builder = new SAXBuilder();
		Document document = builder.build(xmlFile);
		EPLConditionsParser eplConditionsParser = new EPLConditionsParser(classLoader.getResource("./EPLSamples/EPLQuery.epl").getPath(), document.getRootElement());
		List<Map<String, List<Trio<String, String, String>>>> fieldvalues = eplConditionsParser.getRestrictions(100);
		
		Trio<String, String, String> trioToTest = fieldvalues.get(0).get("field1").get(0);
		assertEquals("field1", trioToTest.getFirst());
		assertEquals("<", trioToTest.getSecond());
		assertEquals("3", trioToTest.getThird());

		trioToTest = fieldvalues.get(0).get("field1").get(1);
		assertEquals("field1", trioToTest.getFirst());
		assertEquals(">", trioToTest.getSecond());
		assertEquals("4", trioToTest.getThird());

		trioToTest = fieldvalues.get(0).get("field1").get(2);
		assertEquals("field1", trioToTest.getFirst());
		assertEquals("=", trioToTest.getSecond());
		assertEquals("5", trioToTest.getThird());

		trioToTest = fieldvalues.get(0).get("field1").get(3);
		assertEquals("field1", trioToTest.getFirst());
		assertEquals("<=", trioToTest.getSecond());
		assertEquals("-6", trioToTest.getThird());

		trioToTest = fieldvalues.get(0).get("field1").get(4);
		assertEquals("field1", trioToTest.getFirst());
		assertEquals(">=", trioToTest.getSecond());
		assertEquals("7", trioToTest.getThird());

		trioToTest = fieldvalues.get(0).get("field1").get(5);
		assertEquals("field1", trioToTest.getFirst());
		assertEquals("!=", trioToTest.getSecond());
		assertEquals("8", trioToTest.getThird());
		
		trioToTest = fieldvalues.get(0).get("field10").get(0);
		assertEquals("field10", trioToTest.getFirst());
		assertEquals("!=", trioToTest.getSecond());
		assertEquals("true", trioToTest.getThird());

	}

	@Test
	public void testEPLOrOperator() throws JDOMException, IOException, ParseException {
		SAXBuilder builder = new SAXBuilder();
		Document document = builder.build(xmlFile);
		EPLConditionsParser eplConditionsParser = new EPLConditionsParser(classLoader.getResource("./EPLSamples/EPLQueryOrOperator.epl").getPath(), document.getRootElement());
		List<Map<String, List<Trio<String, String, String>>>> fieldvalues = eplConditionsParser.getRestrictions(100);
		

		Trio<String, String, String> trioToTest = fieldvalues.get(0).get("field1").get(0);
		assertEquals("field1", trioToTest.getFirst());
		assertEquals("<", trioToTest.getSecond());
		assertEquals("3", trioToTest.getThird());

		trioToTest = fieldvalues.get(1).get("field1").get(0);
		assertEquals("field1", trioToTest.getFirst());
		assertEquals(">", trioToTest.getSecond());
		assertEquals("4", trioToTest.getThird());
	}

	@Test
	public void testEPLAndOperator() throws JDOMException, IOException, ParseException {
		SAXBuilder builder = new SAXBuilder();
		Document document = builder.build(xmlFile);

		EPLConditionsParser eplConditionsParser = new EPLConditionsParser(classLoader.getResource("./EPLSamples/EPLQueryAndOperator.epl").getPath(), document.getRootElement());
		List<Map<String, List<Trio<String, String, String>>>> fieldvalues = eplConditionsParser.getRestrictions(100);

		Trio<String, String, String> trioToTest = fieldvalues.get(0).get("field1").get(0);
		assertEquals("field1", trioToTest.getFirst());
		assertEquals("<", trioToTest.getSecond());
		assertEquals("3", trioToTest.getThird());

		trioToTest = fieldvalues.get(0).get("field1").get(1);
		assertEquals("field1", trioToTest.getFirst());
		assertEquals(">", trioToTest.getSecond());
		assertEquals("4", trioToTest.getThird());
	}

	@Test
	public void testEPLNested() throws JDOMException, IOException, ParseException {
		SAXBuilder builder = new SAXBuilder();
		Document document = builder.build(xmlFile);

		EPLConditionsParser eplConditionsParser = new EPLConditionsParser(classLoader.getResource("./EPLSamples/EPLQueryNested.epl").getPath(), document.getRootElement());
		List<Map<String, List<Trio<String, String, String>>>> fieldvalues = eplConditionsParser.getRestrictions(100);

		Trio<String, String, String> trioToTest = fieldvalues.get(0).get("field1").get(0);
		assertThat("field1", equalTo(trioToTest.getFirst()));
		assertEquals("<", trioToTest.getSecond());
		assertEquals("3", trioToTest.getThird());

		trioToTest = fieldvalues.get(0).get("field2").get(0);
		assertThat("field2", equalTo(trioToTest.getFirst()));
		assertEquals(">=", trioToTest.getSecond());
		assertEquals("50", trioToTest.getThird());
		
		trioToTest = fieldvalues.get(0).get("field3").get(0);
		assertThat("field3", equalTo(trioToTest.getFirst()));
		assertEquals("!=", trioToTest.getSecond());
		assertEquals("A", trioToTest.getThird());
		
		trioToTest = fieldvalues.get(0).get("field4").get(0);
		assertThat("field4", equalTo(trioToTest.getFirst()));
		assertEquals("=", trioToTest.getSecond());
		assertEquals("B", trioToTest.getThird());
		
		trioToTest = fieldvalues.get(1).get("field1").get(0);
		assertThat("field1", equalTo(trioToTest.getFirst()));
		assertEquals("<", trioToTest.getSecond());
		assertEquals("3", trioToTest.getThird());

		trioToTest = fieldvalues.get(1).get("field2").get(0);
		assertThat("field2", equalTo(trioToTest.getFirst()));
		assertEquals(">=", trioToTest.getSecond());
		assertEquals("50", trioToTest.getThird());
		
		trioToTest = fieldvalues.get(1).get("field3").get(0);
		assertThat("field3", equalTo(trioToTest.getFirst()));
		assertEquals("!=", trioToTest.getSecond());
		assertEquals("A", trioToTest.getThird());
		
		trioToTest = fieldvalues.get(1).get("field4").get(0);
		assertThat("field4", equalTo(trioToTest.getFirst()));
		assertEquals("=", trioToTest.getSecond());
		assertEquals("D", trioToTest.getThird());
	}
	
	@Test
	public void testEPLRemoveComplexType() throws JDOMException, IOException, ParseException {
		SAXBuilder builder = new SAXBuilder();
		Document document = builder.build(xmlFile);

		EPLConditionsParser eplConditionsParser = new EPLConditionsParser(classLoader.getResource("./EPLSamples/ComplexFieldExamples/EPLComplexFieldQuery.epl").getPath(), document.getRootElement());
		List<Map<String, List<Trio<String, String, String>>>> fieldvalues = eplConditionsParser.getRestrictions(100);

		assertThat(fieldvalues, empty());
	}
}
