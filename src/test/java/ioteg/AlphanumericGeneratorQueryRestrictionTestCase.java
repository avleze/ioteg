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
import org.junit.jupiter.api.Test;

import com.ioteg.EventGenerator;
import com.ioteg.Trio;

import static org.hamcrest.Matchers.matchesPattern;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;



public class AlphanumericGeneratorQueryRestrictionTestCase {

	private static File xmlFile;
	private static ClassLoader classLoader;
	private static List<Element> fields;

	@BeforeEach
	public void loadSchema() throws JDOMException, IOException {
		SAXBuilder builder = new SAXBuilder();
		classLoader = AlphanumericGeneratorQueryRestrictionTestCase.class.getClassLoader();
		xmlFile = new File(classLoader.getResource("./EPLSamples/testEplQuery.xml").getFile());
		Document document = builder.build(xmlFile);

		List<Element> blocks = document.getRootElement().getChildren("block");
		fields = blocks.get(0).getChildren("field");
		EventGenerator.fieldvalues = new ArrayList<>();
	}

	@Test
	public void testAlphanumericQueryRestrictionEqualOperator() throws IOException, JDOMException {
		SAXBuilder builder = new SAXBuilder();
		Document document = builder.build(xmlFile);
		EventGenerator.GetEPLValues(classLoader.getResource("./EPLSamples/AlphanumericOperatorExamples/EPLAlphanumericQueryEqualOperator.epl").getPath(),
				document.getRootElement());
		
		Element field = fields.get(28);

		String result = EventGenerator.GenerateValueSimpleType(field.getAttributeValue("type"), field);
		assertThat(result, equalTo("HOLA ESTO ES UNA PRUEBA"));
		assertThat(result.length(), equalTo(23));
	}
	
	@Test
	public void testAlphanumericQueryRestrictionNotEqualOperator() throws IOException, JDOMException {
		SAXBuilder builder = new SAXBuilder();
		Document document = builder.build(xmlFile);
		EventGenerator.GetEPLValues(classLoader.getResource("./EPLSamples/AlphanumericOperatorExamples/EPLAlphanumericQueryNotEqualOperator.epl").getPath(),
				document.getRootElement());
		
		Element field = fields.get(28);

		String result = EventGenerator.GenerateValueSimpleType(field.getAttributeValue("type"), field);
		assertThat(result, not("HOLA ESTO ES UNA PRUEBA"));
		assertThat(result.length(), equalTo(10));
	}
	
	@Test
	public void testAlphanumericQueryRestrictionNotEqualOperatorWithEndCharacter() throws IOException, JDOMException {
		SAXBuilder builder = new SAXBuilder();
		Document document = builder.build(xmlFile);
		EventGenerator.GetEPLValues(classLoader.getResource("./EPLSamples/AlphanumericOperatorExamples/EPLAlphanumericQueryNotEqualOperator.epl").getPath(),
				document.getRootElement());
		
		Element field = fields.get(29);

		String result = EventGenerator.GenerateValueSimpleType(field.getAttributeValue("type"), field);
		assertThat(result, not("HOLA ESTO ES UNA PRUEBA"));
		assertThat(result.toUpperCase(), matchesPattern("[0123456789ABCDEF]*"));
		assertThat(result.length(), equalTo(10));
	}
	
	@Test
	public void testAlphanumericQueryRestrictionNotEqualOperatorWithLength() throws IOException, JDOMException {
		SAXBuilder builder = new SAXBuilder();
		Document document = builder.build(xmlFile);
		EventGenerator.GetEPLValues(classLoader.getResource("./EPLSamples/AlphanumericOperatorExamples/EPLAlphanumericQueryNotEqualOperator.epl").getPath(),
				document.getRootElement());
		
		Element field = fields.get(30);

		String result = EventGenerator.GenerateValueSimpleType(field.getAttributeValue("type"), field);
		assertThat(result, not("HOLA ESTO ES UNA PRUEBA"));
		assertThat(result.toUpperCase(), matchesPattern("[0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ]*"));
		assertThat(result.length(), equalTo(12));
	}
	
	@Test
	public void testAlphanumericQueryRestrictionNotEqualOperatorWithLengthAndCase() throws IOException, JDOMException {
		SAXBuilder builder = new SAXBuilder();
		Document document = builder.build(xmlFile);
		EventGenerator.GetEPLValues(classLoader.getResource("./EPLSamples/AlphanumericOperatorExamples/EPLAlphanumericQueryNotEqualOperator.epl").getPath(),
				document.getRootElement());
		
		Element field = fields.get(32);

		String result = EventGenerator.GenerateValueSimpleType(field.getAttributeValue("type"), field);
		assertThat(result, not("HOLA ESTO ES UNA PRUEBA"));
		assertThat(result.length(), equalTo(12));
		assertThat(result, matchesPattern("[0123456789abcdefghijklmnopqrstuvwxyz]*"));
	}
	
	@Test
	public void testAlphanumericQueryRestrictionNotEqualOperatorWithEndCharacterAndCase() throws IOException, JDOMException {
		SAXBuilder builder = new SAXBuilder();
		Document document = builder.build(xmlFile);
		EventGenerator.GetEPLValues(classLoader.getResource("./EPLSamples/AlphanumericOperatorExamples/EPLAlphanumericQueryNotEqualOperator.epl").getPath(),
				document.getRootElement());
		
		Element field = fields.get(33);

		String result = EventGenerator.GenerateValueSimpleType(field.getAttributeValue("type"), field);
		assertThat(result, not("HOLA ESTO ES UNA PRUEBA"));
		assertThat(result, matchesPattern("[0123456789abcdefghijklmnopqrstuvwxyz]*"));
	}
	
	@Test
	public void testAlphanumericQueryRestrictionNotEqualOperatorWithEndCharacterAndLength() throws IOException, JDOMException {
		SAXBuilder builder = new SAXBuilder();
		Document document = builder.build(xmlFile);
		EventGenerator.GetEPLValues(classLoader.getResource("./EPLSamples/AlphanumericOperatorExamples/EPLAlphanumericQueryNotEqualOperator.epl").getPath(),
				document.getRootElement());
		
		Element field = fields.get(31);

		String result = EventGenerator.GenerateValueSimpleType(field.getAttributeValue("type"), field);
		assertThat(result, not("HOLA ESTO ES UNA PRUEBA"));
		assertThat(result, matchesPattern("[0123456789ABCDEF]*"));
		assertThat(result.length(), equalTo(12));
	}


}
