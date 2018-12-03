package ioteg;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.ioteg.EventGenerator;
import com.ioteg.Trio;

public class GetEPLValuesTestCase {
	private static File xmlFile;
	private static ClassLoader classLoader;

	@BeforeAll
	public static void loadSchema() throws JDOMException, IOException {

		classLoader = StringGeneratorTestCase.class.getClassLoader();
		xmlFile = new File(classLoader.getResource("./EPLSamples/testEplQuery.xml").getFile());
	}

	@Test
	public void testEPLOperators() throws JDOMException, IOException, ParseException {
		SAXBuilder builder = new SAXBuilder();
		Document document = builder.build(xmlFile);
		EventGenerator.GetEPLValues(classLoader.getResource("./EPLSamples/EPLQuery.epl").getPath(),
				document.getRootElement());

		Trio<String, String, String> trioToTest = EventGenerator.fieldvalues.get(0).get(0);
		assertEquals("field1", trioToTest.first);
		assertEquals("<", trioToTest.second);
		assertEquals("3", trioToTest.third);

		trioToTest = EventGenerator.fieldvalues.get(0).get(1);
		assertEquals("field1", trioToTest.first);
		assertEquals(">", trioToTest.second);
		assertEquals("4", trioToTest.third);

		trioToTest = EventGenerator.fieldvalues.get(0).get(2);
		assertEquals("field1", trioToTest.first);
		assertEquals("=", trioToTest.second);
		assertEquals("5", trioToTest.third);

		trioToTest = EventGenerator.fieldvalues.get(0).get(3);
		assertEquals("field1", trioToTest.first);
		assertEquals("<=", trioToTest.second);
		assertEquals("-6", trioToTest.third);

		trioToTest = EventGenerator.fieldvalues.get(0).get(4);
		assertEquals("field1", trioToTest.first);
		assertEquals(">=", trioToTest.second);
		assertEquals("7", trioToTest.third);

		trioToTest = EventGenerator.fieldvalues.get(0).get(5);
		assertEquals("field1", trioToTest.first);
		assertEquals("!=", trioToTest.second);
		assertEquals("8", trioToTest.third);

	}

	@Test
	public void testEPLOrOperator() throws JDOMException, IOException, ParseException {
		SAXBuilder builder = new SAXBuilder();
		Document document = builder.build(xmlFile);
		EventGenerator.fieldvalues = new ArrayList<List<Trio<String, String, String>>>();
		EventGenerator.GetEPLValues(classLoader.getResource("./EPLSamples/EPLQueryOrOperator.epl").getPath(),
				document.getRootElement());

		Trio<String, String, String> trioToTest = EventGenerator.fieldvalues.get(0).get(0);
		assertEquals("field1", trioToTest.first);
		assertEquals("<", trioToTest.second);
		assertEquals("3", trioToTest.third);

		trioToTest = EventGenerator.fieldvalues.get(1).get(0);
		assertEquals("field1", trioToTest.first);
		assertEquals(">", trioToTest.second);
		assertEquals("4", trioToTest.third);
	}

	@Test
	public void testEPLAndOperator() throws JDOMException, IOException, ParseException {
		SAXBuilder builder = new SAXBuilder();
		Document document = builder.build(xmlFile);
		EventGenerator.fieldvalues = new ArrayList<List<Trio<String, String, String>>>();
		EventGenerator.GetEPLValues(classLoader.getResource("./EPLSamples/EPLQueryAndOperator.epl").getPath(),
				document.getRootElement());

		Trio<String, String, String> trioToTest = EventGenerator.fieldvalues.get(0).get(0);
		assertEquals("field1", trioToTest.first);
		assertEquals("<", trioToTest.second);
		assertEquals("3", trioToTest.third);

		trioToTest = EventGenerator.fieldvalues.get(0).get(1);
		assertEquals("field1", trioToTest.first);
		assertEquals(">", trioToTest.second);
		assertEquals("4", trioToTest.third);
	}

	@Test
	public void testEPLNested() throws JDOMException, IOException, ParseException {
		SAXBuilder builder = new SAXBuilder();
		Document document = builder.build(xmlFile);
		EventGenerator.fieldvalues = new ArrayList<List<Trio<String, String, String>>>();
		EventGenerator.GetEPLValues(classLoader.getResource("./EPLSamples/EPLQueryNested.epl").getPath(),
				document.getRootElement());

		
	}
}
