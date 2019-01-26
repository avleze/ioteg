package ioteg;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.Matchers.lessThanOrEqualTo;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;
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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.ioteg.EventGenerator;


public class LongGeneratorQueryRestrictionTestCase {

	private static File xmlFile;
	private static ClassLoader classLoader;
	private static List<Element> fields;

	@BeforeEach
	public void loadSchema() throws JDOMException, IOException {
		SAXBuilder builder = new SAXBuilder();
		classLoader = LongGeneratorQueryRestrictionTestCase.class.getClassLoader();
		xmlFile = new File(classLoader.getResource("./EPLSamples/testEplQuery.xml").getFile());
		Document document = builder.build(xmlFile);

		List<Element> blocks = document.getRootElement().getChildren("block");
		fields = blocks.get(0).getChildren("field");
		EventGenerator.fieldvalues = new ArrayList<>();
	}

	@Test
	public void testLongQueryRestrictionLessOperator() throws IOException, JDOMException {
		SAXBuilder builder = new SAXBuilder();
		Document document = builder.build(xmlFile);
		EventGenerator.GetEPLValues(classLoader.getResource("./EPLSamples/LongOperatorExamples/EPLLongQueryLessOperator.epl").getPath(),
				document.getRootElement());
		
		Element field = fields.get(18);

		Long result = Long.parseLong(EventGenerator.GenerateValueSimpleType(field.getAttributeValue("type"), field));
		assertTrue(result < Long.valueOf(24L));
	}
	
	@Test
	public void testLongQueryRestrictionLessOperatorWithMinValue() throws IOException, JDOMException {
		SAXBuilder builder = new SAXBuilder();
		Document document = builder.build(xmlFile);
		EventGenerator.GetEPLValues(classLoader.getResource("./EPLSamples/LongOperatorExamples/EPLLongQueryLessOperator.epl").getPath(),
				document.getRootElement());
		
		Element field = fields.get(20);

		Long result = Long.parseLong(EventGenerator.GenerateValueSimpleType(field.getAttributeValue("type"), field));
		assertThat(result, lessThan(Long.valueOf(24)));
		assertThat(result, greaterThanOrEqualTo(Long.valueOf(-20L)));
	}
	
	
	@Test
	public void testLongQueryRestrictionLessOperatorWithNegativeValue() throws IOException, JDOMException {
		SAXBuilder builder = new SAXBuilder();
		Document document = builder.build(xmlFile);
		EventGenerator.GetEPLValues(classLoader.getResource("./EPLSamples/LongOperatorExamples/EPLLongQueryLessOperatorWithNegativeValue.epl").getPath(),
				document.getRootElement());
		
		Element field = fields.get(18);

		Long result = Long.parseLong(EventGenerator.GenerateValueSimpleType(field.getAttributeValue("type"), field));
		assertThat(result, lessThan(Long.valueOf(-24)));
	}
	
	@Test
	public void testLongQueryRestrictionGreaterOperator() throws IOException, JDOMException {
		SAXBuilder builder = new SAXBuilder();
		Document document = builder.build(xmlFile);
		EventGenerator.GetEPLValues(classLoader.getResource("./EPLSamples/LongOperatorExamples/EPLLongQueryGreaterOperator.epl").getPath(),
				document.getRootElement());
		
		Element field = fields.get(18);

		Long result = Long.parseLong(EventGenerator.GenerateValueSimpleType(field.getAttributeValue("type"), field));
		assertThat(result, greaterThan(24L));
	}
	
	
	@Test
	public void testLongQueryRestrictionGreaterOperatorWithMaxValue() throws IOException, JDOMException {
		SAXBuilder builder = new SAXBuilder();
		Document document = builder.build(xmlFile);
		EventGenerator.GetEPLValues(classLoader.getResource("./EPLSamples/LongOperatorExamples/EPLLongQueryGreaterOperator.epl").getPath(),
				document.getRootElement());
		
		Element field = fields.get(19);

		Long result = Long.parseLong(EventGenerator.GenerateValueSimpleType(field.getAttributeValue("type"), field));
		assertThat(result, greaterThan(24L));
		assertThat(result, lessThanOrEqualTo(200L));
	}
	
	@Test
	public void testLongQueryRestrictionEqualOperator() throws IOException, JDOMException {
		SAXBuilder builder = new SAXBuilder();
		Document document = builder.build(xmlFile);
		EventGenerator.GetEPLValues(classLoader.getResource("./EPLSamples/LongOperatorExamples/EPLLongQueryEqualOperator.epl").getPath(),
				document.getRootElement());
		
		Element field = fields.get(18);

		Long result = Long.parseLong(EventGenerator.GenerateValueSimpleType(field.getAttributeValue("type"), field));
		assertEquals(result, Long.valueOf(24));
	}
	
	@Test
	public void testLongQueryRestrictionNotEqualOperator() throws IOException, JDOMException {
		SAXBuilder builder = new SAXBuilder();
		Document document = builder.build(xmlFile);
		EventGenerator.GetEPLValues(classLoader.getResource("./EPLSamples/LongOperatorExamples/EPLLongQueryNotEqualOperator.epl").getPath(),
				document.getRootElement());
		
		Element field = fields.get(18);

		Long result = Long.parseLong(EventGenerator.GenerateValueSimpleType(field.getAttributeValue("type"), field));
		assertTrue(result != Long.valueOf(24));
	}
	
	@Test
	public void testLongQueryRestrictionNotEqualOperatorWithMinAndMax() throws IOException, JDOMException {
		SAXBuilder builder = new SAXBuilder();
		Document document = builder.build(xmlFile);
		EventGenerator.GetEPLValues(classLoader.getResource("./EPLSamples/LongOperatorExamples/EPLLongQueryNotEqualOperator.epl").getPath(),
				document.getRootElement());
		
		Element field = fields.get(21);

		Long result = Long.parseLong(EventGenerator.GenerateValueSimpleType(field.getAttributeValue("type"), field));
		assertThat(result, not(Long.valueOf(20)));
		assertThat(result, greaterThanOrEqualTo(Long.valueOf(-100)));
		assertThat(result, lessThanOrEqualTo(Long.valueOf(100)));

	}

	
	@Test
	public void testLongQueryRestrictionGreaterEqualOperator() throws IOException, JDOMException {
		SAXBuilder builder = new SAXBuilder();
		Document document = builder.build(xmlFile);
		EventGenerator.GetEPLValues(classLoader.getResource("./EPLSamples/LongOperatorExamples/EPLLongQueryGreaterEqualOperator.epl").getPath(),
				document.getRootElement());


		Element field = fields.get(18);

		Long result = Long.parseLong(EventGenerator.GenerateValueSimpleType(field.getAttributeValue("type"), field));
		assertThat(result, greaterThanOrEqualTo(Long.valueOf(24)));
	}
	
	@Test
	public void testLongQueryRestrictionGreaterEqualOperatorWithMaxAttribute() throws IOException, JDOMException {
		SAXBuilder builder = new SAXBuilder();
		Document document = builder.build(xmlFile);
		EventGenerator.GetEPLValues(classLoader.getResource("./EPLSamples/LongOperatorExamples/EPLLongQueryGreaterEqualOperator.epl").getPath(),
				document.getRootElement());


		Element field = fields.get(19);

		Long result = Long.parseLong(EventGenerator.GenerateValueSimpleType(field.getAttributeValue("type"), field));
		assertThat(result, greaterThanOrEqualTo(Long.valueOf(24)));
	}
	
	@Test
	public void testLongQueryRestrictionLessEqualOperator() throws IOException, JDOMException {
		SAXBuilder builder = new SAXBuilder();
		Document document = builder.build(xmlFile);
		EventGenerator.GetEPLValues(classLoader.getResource("./EPLSamples/LongOperatorExamples/EPLLongQueryLessEqualOperator.epl").getPath(),
				document.getRootElement());
		
		Element field = fields.get(18);
		Long result = Long.parseLong(EventGenerator.GenerateValueSimpleType(field.getAttributeValue("type"), field));

		assertThat(result, lessThanOrEqualTo(Long.valueOf(24)));
	}
	
	@Test
	public void testLongQueryRestrictionLessEqualOperatorWithMinValue() throws IOException, JDOMException {
		SAXBuilder builder = new SAXBuilder();
		Document document = builder.build(xmlFile);
		EventGenerator.GetEPLValues(classLoader.getResource("./EPLSamples/LongOperatorExamples/EPLLongQueryLessEqualOperator.epl").getPath(),
				document.getRootElement());
		
		Element field = fields.get(20);
		Long result = Long.parseLong(EventGenerator.GenerateValueSimpleType(field.getAttributeValue("type"), field));

		assertThat(result, lessThanOrEqualTo(Long.valueOf(24)));
		assertThat(result, greaterThanOrEqualTo(Long.valueOf(-20)));

	}
	
	@Test
	public void testLongQueryRestrictionLessEqualOperatorWithNegativeValue() throws IOException, JDOMException {
		SAXBuilder builder = new SAXBuilder();
		Document document = builder.build(xmlFile);
		EventGenerator.GetEPLValues(classLoader.getResource("./EPLSamples/LongOperatorExamples/EPLLongQueryLessEqualOperatorWithNegativeValue.epl").getPath(),
				document.getRootElement());
		
		Element field = fields.get(18);
	
		Long result = Long.parseLong(EventGenerator.GenerateValueSimpleType(field.getAttributeValue("type"), field));

		assertThat(result, lessThanOrEqualTo(Long.valueOf(-24)));
	}
}
