package ioteg;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.ioteg.EventGenerator;
import com.ioteg.Trio;

public class IntegerGeneratorQueryRestrictionTestCase {

	private static File xmlFile;
	private static ClassLoader classLoader;
	private static List<Element> fields;

	@BeforeAll
	public static void loadSchema() throws JDOMException, IOException {
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
		EventGenerator.GetEPLValues(classLoader.getResource("./EPLSamples/ComparisonOperatorExamples/EPLIntegerQueryLessOperator.epl").getPath(),
				document.getRootElement());
		
		Element field = fields.get(0);

		Integer result = Integer.parseInt(EventGenerator.GenerateValueSimpleType(field.getAttributeValue("type"), fields.get(0)));
		assertTrue(result < Integer.valueOf(24));
	}
	
	@Test
	public void testIntegerQueryRestrictionLessOperatorWithNegativeValue() throws IOException, JDOMException {
		SAXBuilder builder = new SAXBuilder();
		Document document = builder.build(xmlFile);
		EventGenerator.GetEPLValues(classLoader.getResource("./EPLSamples/ComparisonOperatorExamples/EPLIntegerQueryLessOperatorWithNegativeValue.epl").getPath(),
				document.getRootElement());
		
		Element field = fields.get(0);

		Integer result = Integer.parseInt(EventGenerator.GenerateValueSimpleType(field.getAttributeValue("type"), fields.get(0)));
		assertTrue(result < Integer.valueOf(-24));
	}
	
	@Test
	public void testIntegerQueryRestrictionGreaterOperator() throws IOException, JDOMException {
		SAXBuilder builder = new SAXBuilder();
		Document document = builder.build(xmlFile);
		EventGenerator.GetEPLValues(classLoader.getResource("./EPLSamples/ComparisonOperatorExamples/EPLIntegerQueryGreaterOperator.epl").getPath(),
				document.getRootElement());
		
		Element field = fields.get(0);

		Integer result = Integer.parseInt(EventGenerator.GenerateValueSimpleType(field.getAttributeValue("type"), fields.get(0)));
		assertTrue(result > Integer.valueOf(24));
	}
	
	
	@Test
	public void testIntegerQueryRestrictionEqualOperator() throws IOException, JDOMException {
		SAXBuilder builder = new SAXBuilder();
		Document document = builder.build(xmlFile);
		EventGenerator.GetEPLValues(classLoader.getResource("./EPLSamples/ComparisonOperatorExamples/EPLIntegerQueryEqualOperator.epl").getPath(),
				document.getRootElement());
		
		Element field = fields.get(0);

		Integer result = Integer.parseInt(EventGenerator.GenerateValueSimpleType(field.getAttributeValue("type"), fields.get(0)));
		assertEquals(result, Integer.valueOf(24));
	}
	
	@Test
	public void testIntegerQueryRestrictionNotEqualOperator() throws IOException, JDOMException {
		SAXBuilder builder = new SAXBuilder();
		Document document = builder.build(xmlFile);
		EventGenerator.GetEPLValues(classLoader.getResource("./EPLSamples/ComparisonOperatorExamples/EPLIntegerQueryNotEqualOperator.epl").getPath(),
				document.getRootElement());
		
		Element field = fields.get(0);

		Integer result = Integer.parseInt(EventGenerator.GenerateValueSimpleType(field.getAttributeValue("type"), fields.get(0)));
		assertTrue(result != Integer.valueOf(24));
	}

	
	@Test
	public void testIntegerQueryRestrictionGreaterEqualOperator() throws IOException, JDOMException {
		SAXBuilder builder = new SAXBuilder();
		Document document = builder.build(xmlFile);
		EventGenerator.GetEPLValues(classLoader.getResource("./EPLSamples/ComparisonOperatorExamples/EPLIntegerQueryGreaterEqualOperator.epl").getPath(),
				document.getRootElement());


		Element field = fields.get(0);

		Integer result = Integer.parseInt(EventGenerator.GenerateValueSimpleType(field.getAttributeValue("type"), fields.get(0)));
		assertTrue(result >= Integer.valueOf(24));
	}
	
	@Test
	public void testIntegerQueryRestrictionLessEqualOperator() throws IOException, JDOMException {
		SAXBuilder builder = new SAXBuilder();
		Document document = builder.build(xmlFile);
		EventGenerator.GetEPLValues(classLoader.getResource("./EPLSamples/ComparisonOperatorExamples/EPLIntegerQueryLessEqualOperator.epl").getPath(),
				document.getRootElement());
		
		Element field = fields.get(0);
		Integer result = Integer.parseInt(EventGenerator.GenerateValueSimpleType(field.getAttributeValue("type"), fields.get(0)));

		assertTrue(result <= Integer.valueOf(24));
	}
	
	@Test
	public void testIntegerQueryRestrictionLessEqualOperatorWithNegativeValue() throws IOException, JDOMException {
		SAXBuilder builder = new SAXBuilder();
		Document document = builder.build(xmlFile);
		EventGenerator.GetEPLValues(classLoader.getResource("./EPLSamples/ComparisonOperatorExamples/EPLIntegerQueryLessEqualOperatorWithNegativeValue.epl").getPath(),
				document.getRootElement());
		
		Element field = fields.get(0);
		for (List<Trio<String, String, String>> el : EventGenerator.fieldvalues) {
			System.out.println();
			for (Trio<String, String, String> elIn : el) {
				System.out.print(", [" + elIn.first + ", " + elIn.second + ", " + elIn.third + "]");
			}
		}
		Integer result = Integer.parseInt(EventGenerator.GenerateValueSimpleType(field.getAttributeValue("type"), fields.get(0)));
		System.out.println(result);

		assertTrue(result <= Integer.valueOf(-24));
	}
}
