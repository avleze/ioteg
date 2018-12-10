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


public class BooleanGeneratorQueryRestrictionTestCase {

	private static File xmlFile;
	private static ClassLoader classLoader;
	private static List<Element> fields;

	@BeforeEach
	public void loadSchema() throws JDOMException, IOException {
		SAXBuilder builder = new SAXBuilder();
		classLoader = BooleanGeneratorQueryRestrictionTestCase.class.getClassLoader();
		xmlFile = new File(classLoader.getResource("./EPLSamples/testEplQuery.xml").getFile());
		Document document = builder.build(xmlFile);

		List<Element> blocks = document.getRootElement().getChildren("block");
		fields = blocks.get(0).getChildren("field");
		EventGenerator.fieldvalues = new ArrayList<List<Trio<String, String, String>>>();
	}


	@Test
	public void testBooleanQueryRestrictionEqualOperator() throws IOException, JDOMException {
		SAXBuilder builder = new SAXBuilder();
		Document document = builder.build(xmlFile);
		EventGenerator.GetEPLValues(classLoader.getResource("./EPLSamples/BooleanOperatorExamples/EPLBooleanQueryEqualOperator.epl").getPath(),
				document.getRootElement());
		
		Element field = fields.get(9);

		Boolean result = Boolean.parseBoolean(EventGenerator.GenerateValueSimpleType(field.getAttributeValue("type"), field));
		assertThat(result, equalTo(Boolean.valueOf(true)));
	}
	
	@Test
	public void testBooleanQueryRestrictionNotEqualOperator() throws IOException, JDOMException {
		SAXBuilder builder = new SAXBuilder();
		Document document = builder.build(xmlFile);
		EventGenerator.GetEPLValues(classLoader.getResource("./EPLSamples/BooleanOperatorExamples/EPLBooleanQueryNotEqualOperator.epl").getPath(),
				document.getRootElement());
		
		Element field = fields.get(9);

		Boolean result = Boolean.parseBoolean(EventGenerator.GenerateValueSimpleType(field.getAttributeValue("type"), field));
		assertThat(result, not(Boolean.valueOf(true)));

		field = fields.get(10);
		
		result = Boolean.parseBoolean(EventGenerator.GenerateValueSimpleType(field.getAttributeValue("type"), field));
		assertThat(result, not(Boolean.valueOf(false)));
		
		field = fields.get(11);

		Integer resultNum = Integer.valueOf(EventGenerator.GenerateValueSimpleType(field.getAttributeValue("type"), field));
		assertThat(resultNum, not(Integer.valueOf(1)));
		
		field = fields.get(12);

		resultNum = Integer.valueOf(EventGenerator.GenerateValueSimpleType(field.getAttributeValue("type"), field));
		assertThat(resultNum, not(Integer.valueOf(0)));
	}
	


}