package ioteg;

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
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import com.ioteg.EventGenerator;
import com.ioteg.Trio;

import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.lessThanOrEqualTo;
import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.matchesPattern;


import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;


public class FloatGeneratorQueryRestrictionTestCase {

	private static File xmlFile;
	private static ClassLoader classLoader;
	private static List<Element> fields;

	@BeforeEach
	public void loadSchema() throws JDOMException, IOException {
		SAXBuilder builder = new SAXBuilder();
		classLoader = FloatGeneratorQueryRestrictionTestCase.class.getClassLoader();
		xmlFile = new File(classLoader.getResource("./EPLSamples/testEplQuery.xml").getFile());
		Document document = builder.build(xmlFile);

		List<Element> blocks = document.getRootElement().getChildren("block");
		fields = blocks.get(0).getChildren("field");
		EventGenerator.fieldvalues = new ArrayList<>();
	}

	@Test
	public void testFloatQueryRestrictionLessOperator() throws IOException, JDOMException {
		SAXBuilder builder = new SAXBuilder();
		Document document = builder.build(xmlFile);
		EventGenerator.GetEPLValues(classLoader.getResource("./EPLSamples/FloatOperatorExamples/EPLFloatQueryLessOperator.epl").getPath(),
				document.getRootElement());
		
		Element field = fields.get(13);

		Float result = Float.parseFloat(EventGenerator.GenerateValueSimpleType(field.getAttributeValue("type"), field));
		assertThat(result, lessThan(Float.valueOf(-24)));
	}
	
	@Test
	public void testFloatQueryRestrictionLessOperatorWithMinValue() throws IOException, JDOMException {
		SAXBuilder builder = new SAXBuilder();
		Document document = builder.build(xmlFile);
		EventGenerator.GetEPLValues(classLoader.getResource("./EPLSamples/FloatOperatorExamples/EPLFloatQueryLessOperator.epl").getPath(),
				document.getRootElement());
		
		Element field = fields.get(15);

		Float result = Float.parseFloat(EventGenerator.GenerateValueSimpleType(field.getAttributeValue("type"), field));
		assertThat(result, lessThan(Float.valueOf(24)));
		assertThat(result, greaterThanOrEqualTo(Float.valueOf(-20.58f)));
	}
	
	
	@Test
	public void testFloatQueryRestrictionLessOperatorWithNegativeValue() throws IOException, JDOMException {
		SAXBuilder builder = new SAXBuilder();
		Document document = builder.build(xmlFile);
		EventGenerator.GetEPLValues(classLoader.getResource("./EPLSamples/FloatOperatorExamples/EPLFloatQueryLessOperatorWithNegativeValue.epl").getPath(),
				document.getRootElement());
		
		Element field = fields.get(13);

		Float result = Float.parseFloat(EventGenerator.GenerateValueSimpleType(field.getAttributeValue("type"), field));
		assertThat(result, lessThan(Float.valueOf(-24)));
	}
	
	@Test
	public void testFloatQueryRestrictionGreaterOperator() throws IOException, JDOMException {
		SAXBuilder builder = new SAXBuilder();
		Document document = builder.build(xmlFile);
		EventGenerator.GetEPLValues(classLoader.getResource("./EPLSamples/FloatOperatorExamples/EPLFloatQueryGreaterOperator.epl").getPath(),
				document.getRootElement());
		
		Element field = fields.get(13);

		Float result = Float.parseFloat(EventGenerator.GenerateValueSimpleType(field.getAttributeValue("type"), field));
		assertThat(result, greaterThan(24f));
	}
	
	
	@Test
	public void testFloatQueryRestrictionGreaterOperatorWithMaxValue() throws IOException, JDOMException {
		SAXBuilder builder = new SAXBuilder();
		Document document = builder.build(xmlFile);
		EventGenerator.GetEPLValues(classLoader.getResource("./EPLSamples/FloatOperatorExamples/EPLFloatQueryGreaterOperator.epl").getPath(),
				document.getRootElement());
		
		Element field = fields.get(14);

		Float result = Float.parseFloat(EventGenerator.GenerateValueSimpleType(field.getAttributeValue("type"), field));
		assertThat(result, greaterThan(24f));
		assertThat(result, lessThanOrEqualTo(200.34f));
	}
	
	@Test
	public void testFloatQueryRestrictionEqualOperator() throws IOException, JDOMException {
		SAXBuilder builder = new SAXBuilder();
		Document document = builder.build(xmlFile);
		EventGenerator.GetEPLValues(classLoader.getResource("./EPLSamples/FloatOperatorExamples/EPLFloatQueryEqualOperator.epl").getPath(),
				document.getRootElement());
		
		Element field = fields.get(13);

		Float result = Float.parseFloat(EventGenerator.GenerateValueSimpleType(field.getAttributeValue("type"), field));
		assertThat(result, equalTo(Float.valueOf(24f)));
	}
	
	@Test
	public void testFloatQueryRestrictionNotEqualOperator() throws IOException, JDOMException {
		SAXBuilder builder = new SAXBuilder();
		Document document = builder.build(xmlFile);
		EventGenerator.GetEPLValues(classLoader.getResource("./EPLSamples/FloatOperatorExamples/EPLFloatQueryNotEqualOperator.epl").getPath(),
				document.getRootElement());
		
		Element field = fields.get(13);

		Float result = Float.parseFloat(EventGenerator.GenerateValueSimpleType(field.getAttributeValue("type"), field));
		assertThat(result, not(Float.valueOf(24f)));
	}
	
	@Test
	public void testFloatQueryRestrictionNotEqualOperatorWithMinAndMax() throws IOException, JDOMException {
		SAXBuilder builder = new SAXBuilder();
		Document document = builder.build(xmlFile);
		EventGenerator.GetEPLValues(classLoader.getResource("./EPLSamples/FloatOperatorExamples/EPLFloatQueryNotEqualOperator.epl").getPath(),
				document.getRootElement());
		
		Element field = fields.get(16);

		Float result = Float.parseFloat(EventGenerator.GenerateValueSimpleType(field.getAttributeValue("type"), field));
		assertThat(result, not(Float.valueOf(24f)));
		assertThat(result, greaterThanOrEqualTo(Float.valueOf(-100.134f)));
		assertThat(result, lessThanOrEqualTo(Float.valueOf(134.1f)));

	}

	
	@Test
	public void testFloatQueryRestrictionGreaterEqualOperator() throws IOException, JDOMException {
		SAXBuilder builder = new SAXBuilder();
		Document document = builder.build(xmlFile);
		EventGenerator.GetEPLValues(classLoader.getResource("./EPLSamples/FloatOperatorExamples/EPLFloatQueryGreaterEqualOperator.epl").getPath(),
				document.getRootElement());


		Element field = fields.get(13);

		Float result = Float.parseFloat(EventGenerator.GenerateValueSimpleType(field.getAttributeValue("type"), field));
		assertThat(result, greaterThanOrEqualTo(Float.valueOf(24)));
	}
	
	@Test
	public void testFloatQueryRestrictionGreaterEqualOperatorWithMaxAttribute() throws IOException, JDOMException {
		SAXBuilder builder = new SAXBuilder();
		Document document = builder.build(xmlFile);
		EventGenerator.GetEPLValues(classLoader.getResource("./EPLSamples/FloatOperatorExamples/EPLFloatQueryGreaterEqualOperator.epl").getPath(),
				document.getRootElement());


		Element field = fields.get(14);

		Float result = Float.parseFloat(EventGenerator.GenerateValueSimpleType(field.getAttributeValue("type"), field));
		assertThat(result, greaterThanOrEqualTo(Float.valueOf(24)));
	}
	
	@Test
	public void testFloatQueryRestrictionLessEqualOperator() throws IOException, JDOMException {
		SAXBuilder builder = new SAXBuilder();
		Document document = builder.build(xmlFile);
		EventGenerator.GetEPLValues(classLoader.getResource("./EPLSamples/FloatOperatorExamples/EPLFloatQueryLessEqualOperator.epl").getPath(),
				document.getRootElement());
		
		Element field = fields.get(13);
		Float result = Float.parseFloat(EventGenerator.GenerateValueSimpleType(field.getAttributeValue("type"), field));

		assertThat(result, lessThanOrEqualTo(Float.valueOf(24)));
	}
	
	@Test
	public void testFloatQueryRestrictionLessEqualOperatorWithMinValue() throws IOException, JDOMException {
		SAXBuilder builder = new SAXBuilder();
		Document document = builder.build(xmlFile);
		EventGenerator.GetEPLValues(classLoader.getResource("./EPLSamples/FloatOperatorExamples/EPLFloatQueryLessEqualOperator.epl").getPath(),
				document.getRootElement());
		
		Element field = fields.get(15);
		Float result = Float.parseFloat(EventGenerator.GenerateValueSimpleType(field.getAttributeValue("type"), field));

		assertThat(result, lessThanOrEqualTo(Float.valueOf(24f)));
		assertThat(result, greaterThanOrEqualTo(Float.valueOf(-20.58f)));

	}
	
	@Test
	public void testFloatQueryRestrictionLessEqualOperatorWithNegativeValue() throws IOException, JDOMException {
		SAXBuilder builder = new SAXBuilder();
		Document document = builder.build(xmlFile);
		EventGenerator.GetEPLValues(classLoader.getResource("./EPLSamples/FloatOperatorExamples/EPLFloatQueryLessEqualOperatorWithNegativeValue.epl").getPath(),
				document.getRootElement());
		
		Element field = fields.get(13);
		Float result = Float.parseFloat(EventGenerator.GenerateValueSimpleType(field.getAttributeValue("type"), field));


		assertThat(result, lessThanOrEqualTo(Float.valueOf(-24f)));
	}
	
	@RepeatedTest(10)
	public void testFloatQueryRestrictionWithPrecision() throws IOException, JDOMException {
		SAXBuilder builder = new SAXBuilder();
		Document document = builder.build(xmlFile);
		EventGenerator.GetEPLValues(classLoader.getResource("./EPLSamples/FloatOperatorExamples/EPLFloatQueryLessEqualOperatorWithNegativeValue.epl").getPath(),
				document.getRootElement());
		
		Element field = fields.get(17);
	
		String result = EventGenerator.GenerateValueSimpleType(field.getAttributeValue("type"), field);

		assertThat(Float.valueOf(result), lessThanOrEqualTo(Float.valueOf(-24f)));
		assertThat(Float.valueOf(result), greaterThanOrEqualTo(Float.valueOf(-100.134234f)));
		assertThat(result, matchesPattern("-?\\d+\\.\\d{3}"));
	}
}
