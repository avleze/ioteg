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

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;



public class DateGeneratorQueryRestrictionTestCase {

	private static File xmlFile;
	private static ClassLoader classLoader;
	private static List<Element> fields;

	@BeforeEach
	public void loadSchema() throws JDOMException, IOException {
		SAXBuilder builder = new SAXBuilder();
		classLoader = DateGeneratorQueryRestrictionTestCase.class.getClassLoader();
		xmlFile = new File(classLoader.getResource("./EPLSamples/testEplQuery.xml").getFile());
		Document document = builder.build(xmlFile);

		List<Element> blocks = document.getRootElement().getChildren("block");
		fields = blocks.get(0).getChildren("field");
		EventGenerator.fieldvalues = new ArrayList<>();
	}

	@Test
	public void testDateQueryRestrictionEqualOperator() throws IOException, JDOMException {
		SAXBuilder builder = new SAXBuilder();
		Document document = builder.build(xmlFile);
		EventGenerator.GetEPLValues(classLoader.getResource("./EPLSamples/DateOperatorExamples/EPLDateQueryEqualOperator.epl").getPath(),
				document.getRootElement());
		
		Element field = fields.get(34);

		String result = EventGenerator.GenerateValueSimpleType(field.getAttributeValue("type"), field);
		assertThat(result, equalTo("96-05-10"));
	}
	
	@Test
	public void testDateQueryRestrictionNotEqualOperator() throws IOException, JDOMException {
		SAXBuilder builder = new SAXBuilder();
		Document document = builder.build(xmlFile);
		EventGenerator.GetEPLValues(classLoader.getResource("./EPLSamples/DateOperatorExamples/EPLDateQueryNotEqualOperator.epl").getPath(),
				document.getRootElement());
		
		Element field = fields.get(34);

		String result = EventGenerator.GenerateValueSimpleType(field.getAttributeValue("type"), field);
		assertThat(result, not("96-06-10"));
	}


}
