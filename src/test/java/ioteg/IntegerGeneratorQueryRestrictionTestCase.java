package ioteg;

import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.ioteg.EventGenerator;
import com.ioteg.Trio;

import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.lessThanOrEqualTo;
import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.Matchers.greaterThan;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;


public class IntegerGeneratorQueryRestrictionTestCase {

	private static File xmlFile;
	private static ClassLoader classLoader;
	private static List<Element> fields;

	@BeforeEach
	public void loadSchema() throws JDOMException, IOException {
		SAXBuilder builder = new SAXBuilder();
		classLoader = IntegerGeneratorQueryRestrictionTestCase.class.getClassLoader();
		xmlFile = new File(classLoader.getResource("./EPLSamples/testEplQuery.xml").getFile());
		Document document = builder.build(xmlFile);

		List<Element> blocks = document.getRootElement().getChildren("block");
		fields = blocks.get(0).getChildren("field");
		EventGenerator.fieldvalues = new ArrayList<List<Trio<String, String, String>>>();
	}

	@Test
	public void testIntegerQueryRestrictionLessOperator() throws IOException, JDOMException {
		SAXBuilder builder = new SAXBuilder();
		Document document = builder.build(xmlFile);
		EventGenerator.GetEPLValues(classLoader.getResource("./EPLSamples/IntegerOperatorExamples/EPLIntegerQueryLessOperator.epl").getPath(),
				document.getRootElement());
		
		Element field = fields.get(0);

		Integer result = Integer.parseInt(EventGenerator.GenerateValueSimpleType(field.getAttributeValue("type"), field));
		assertTrue(result < Integer.valueOf(24));
	}
	
	@Test
	public void testIntegerQueryRestrictionLessOperatorWithMinValue() throws IOException, JDOMException {
		SAXBuilder builder = new SAXBuilder();
		Document document = builder.build(xmlFile);
		EventGenerator.GetEPLValues(classLoader.getResource("./EPLSamples/IntegerOperatorExamples/EPLIntegerQueryLessOperator.epl").getPath(),
				document.getRootElement());
		
		Element field = fields.get(7);

		Integer result = Integer.parseInt(EventGenerator.GenerateValueSimpleType(field.getAttributeValue("type"), field));
		assertThat(result, lessThan(Integer.valueOf(24)));
		assertThat(result, greaterThanOrEqualTo(Integer.valueOf(-20)));
	}
	
	
	@Test
	public void testIntegerQueryRestrictionLessOperatorWithNegativeValue() throws IOException, JDOMException {
		SAXBuilder builder = new SAXBuilder();
		Document document = builder.build(xmlFile);
		EventGenerator.GetEPLValues(classLoader.getResource("./EPLSamples/IntegerOperatorExamples/EPLIntegerQueryLessOperatorWithNegativeValue.epl").getPath(),
				document.getRootElement());
		
		Element field = fields.get(0);

		Integer result = Integer.parseInt(EventGenerator.GenerateValueSimpleType(field.getAttributeValue("type"), field));
		assertTrue(result < Integer.valueOf(-24));
	}
	
	@Test
	public void testIntegerQueryRestrictionGreaterOperator() throws IOException, JDOMException {
		SAXBuilder builder = new SAXBuilder();
		Document document = builder.build(xmlFile);
		EventGenerator.GetEPLValues(classLoader.getResource("./EPLSamples/IntegerOperatorExamples/EPLIntegerQueryGreaterOperator.epl").getPath(),
				document.getRootElement());
		
		Element field = fields.get(0);

		Integer result = Integer.parseInt(EventGenerator.GenerateValueSimpleType(field.getAttributeValue("type"), field));
		assertThat(result, greaterThan(24));
	}
	
	
	@Test
	public void testIntegerQueryRestrictionGreaterOperatorWithMaxValue() throws IOException, JDOMException {
		SAXBuilder builder = new SAXBuilder();
		Document document = builder.build(xmlFile);
		EventGenerator.GetEPLValues(classLoader.getResource("./EPLSamples/IntegerOperatorExamples/EPLIntegerQueryGreaterOperator.epl").getPath(),
				document.getRootElement());
		
		Element field = fields.get(6);

		Integer result = Integer.parseInt(EventGenerator.GenerateValueSimpleType(field.getAttributeValue("type"), field));
		assertThat(result, greaterThan(24));
		assertThat(result, lessThanOrEqualTo(200));
	}
	
	@Test
	public void testIntegerQueryRestrictionEqualOperator() throws IOException, JDOMException {
		SAXBuilder builder = new SAXBuilder();
		Document document = builder.build(xmlFile);
		EventGenerator.GetEPLValues(classLoader.getResource("./EPLSamples/IntegerOperatorExamples/EPLIntegerQueryEqualOperator.epl").getPath(),
				document.getRootElement());
		
		Element field = fields.get(0);

		Integer result = Integer.parseInt(EventGenerator.GenerateValueSimpleType(field.getAttributeValue("type"), field));
		assertEquals(result, Integer.valueOf(24));
	}
	
	@Test
	public void testIntegerQueryRestrictionNotEqualOperator() throws IOException, JDOMException {
		SAXBuilder builder = new SAXBuilder();
		Document document = builder.build(xmlFile);
		EventGenerator.GetEPLValues(classLoader.getResource("./EPLSamples/IntegerOperatorExamples/EPLIntegerQueryNotEqualOperator.epl").getPath(),
				document.getRootElement());
		
		Element field = fields.get(0);

		Integer result = Integer.parseInt(EventGenerator.GenerateValueSimpleType(field.getAttributeValue("type"), field));
		assertTrue(result != Integer.valueOf(24));
	}
	
	@Test
	public void testIntegerQueryRestrictionNotEqualOperatorWithMinAndMax() throws IOException, JDOMException {
		SAXBuilder builder = new SAXBuilder();
		Document document = builder.build(xmlFile);
		EventGenerator.GetEPLValues(classLoader.getResource("./EPLSamples/IntegerOperatorExamples/EPLIntegerQueryNotEqualOperator.epl").getPath(),
				document.getRootElement());
		
		Element field = fields.get(8);

		Integer result = Integer.parseInt(EventGenerator.GenerateValueSimpleType(field.getAttributeValue("type"), field));
		assertThat(result, not(Integer.valueOf(24)));
		assertThat(result, greaterThanOrEqualTo(Integer.valueOf(-100)));
		assertThat(result, lessThanOrEqualTo(Integer.valueOf(100)));

	}

	
	@Test
	public void testIntegerQueryRestrictionGreaterEqualOperator() throws IOException, JDOMException {
		SAXBuilder builder = new SAXBuilder();
		Document document = builder.build(xmlFile);
		EventGenerator.GetEPLValues(classLoader.getResource("./EPLSamples/IntegerOperatorExamples/EPLIntegerQueryGreaterEqualOperator.epl").getPath(),
				document.getRootElement());


		Element field = fields.get(0);

		Integer result = Integer.parseInt(EventGenerator.GenerateValueSimpleType(field.getAttributeValue("type"), field));
		assertThat(result, greaterThanOrEqualTo(Integer.valueOf(24)));
	}
	
	@Test
	public void testIntegerQueryRestrictionGreaterEqualOperatorWithMaxAttribute() throws IOException, JDOMException {
		SAXBuilder builder = new SAXBuilder();
		Document document = builder.build(xmlFile);
		EventGenerator.GetEPLValues(classLoader.getResource("./EPLSamples/IntegerOperatorExamples/EPLIntegerQueryGreaterEqualOperator.epl").getPath(),
				document.getRootElement());


		Element field = fields.get(6);

		Integer result = Integer.parseInt(EventGenerator.GenerateValueSimpleType(field.getAttributeValue("type"), field));
		assertThat(result, greaterThanOrEqualTo(Integer.valueOf(24)));
	}
	
	@Test
	public void testIntegerQueryRestrictionLessEqualOperator() throws IOException, JDOMException {
		SAXBuilder builder = new SAXBuilder();
		Document document = builder.build(xmlFile);
		EventGenerator.GetEPLValues(classLoader.getResource("./EPLSamples/IntegerOperatorExamples/EPLIntegerQueryLessEqualOperator.epl").getPath(),
				document.getRootElement());
		
		Element field = fields.get(0);
		Integer result = Integer.parseInt(EventGenerator.GenerateValueSimpleType(field.getAttributeValue("type"), field));

		assertThat(result, lessThanOrEqualTo(Integer.valueOf(24)));
	}
	
	@Test
	public void testIntegerQueryRestrictionLessEqualOperatorWithMinValue() throws IOException, JDOMException {
		SAXBuilder builder = new SAXBuilder();
		Document document = builder.build(xmlFile);
		EventGenerator.GetEPLValues(classLoader.getResource("./EPLSamples/IntegerOperatorExamples/EPLIntegerQueryLessEqualOperator.epl").getPath(),
				document.getRootElement());
		
		Element field = fields.get(7);
		Integer result = Integer.parseInt(EventGenerator.GenerateValueSimpleType(field.getAttributeValue("type"), field));

		assertThat(result, lessThanOrEqualTo(Integer.valueOf(24)));
		assertThat(result, greaterThanOrEqualTo(Integer.valueOf(-20)));

	}
	
	@Test
	public void testIntegerQueryRestrictionLessEqualOperatorWithNegativeValue() throws IOException, JDOMException {
		SAXBuilder builder = new SAXBuilder();
		Document document = builder.build(xmlFile);
		EventGenerator.GetEPLValues(classLoader.getResource("./EPLSamples/IntegerOperatorExamples/EPLIntegerQueryLessEqualOperatorWithNegativeValue.epl").getPath(),
				document.getRootElement());
		
		Element field = fields.get(0);
		for (List<Trio<String, String, String>> el : EventGenerator.fieldvalues) {
			System.out.println();
			for (Trio<String, String, String> elIn : el) {
				System.out.print(", [" + elIn.first + ", " + elIn.second + ", " + elIn.third + "]");
			}
		}
		Integer result = Integer.parseInt(EventGenerator.GenerateValueSimpleType(field.getAttributeValue("type"), field));
		System.out.println(result);

		assertThat(result, lessThanOrEqualTo(Integer.valueOf(-24)));
	}
}
