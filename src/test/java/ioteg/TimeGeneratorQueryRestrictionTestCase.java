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



public class TimeGeneratorQueryRestrictionTestCase {

	private static File xmlFile;
	private static ClassLoader classLoader;
	private static List<Element> fields;

	@BeforeEach
	public void loadSchema() throws JDOMException, IOException {
		SAXBuilder builder = new SAXBuilder();
		classLoader = TimeGeneratorQueryRestrictionTestCase.class.getClassLoader();
		xmlFile = new File(classLoader.getResource("./EPLSamples/testEplQuery.xml").getFile());
		Document document = builder.build(xmlFile);

		List<Element> blocks = document.getRootElement().getChildren("block");
		fields = blocks.get(0).getChildren("field");
		EventGenerator.fieldvalues = new ArrayList<>();
	}

	@Test
	public void testTimeQueryRestrictionEqualOperator() throws IOException, JDOMException {
		SAXBuilder builder = new SAXBuilder();
		Document document = builder.build(xmlFile);
		EventGenerator.GetEPLValues(classLoader.getResource("./EPLSamples/TimeOperatorExamples/EPLTimeQueryEqualOperator.epl").getPath(),
				document.getRootElement());
		
		Element field = fields.get(35);

		String result = EventGenerator.GenerateValueSimpleType(field.getAttributeValue("type"), field);
		assertThat(result, equalTo("12:43"));
	}
	
	@Test
	public void testTimeQueryRestrictionNotEqualOperator() throws IOException, JDOMException {
		SAXBuilder builder = new SAXBuilder();
		Document document = builder.build(xmlFile);
		EventGenerator.GetEPLValues(classLoader.getResource("./EPLSamples/TimeOperatorExamples/EPLTimeQueryNotEqualOperator.epl").getPath(),
				document.getRootElement());
		
		Element field = fields.get(35);

		String result = EventGenerator.GenerateValueSimpleType(field.getAttributeValue("type"), field);
		assertThat(result, not("12:43"));
	}


}
