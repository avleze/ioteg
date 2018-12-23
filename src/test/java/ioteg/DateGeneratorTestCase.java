package ioteg;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

public class DateGeneratorTestCase {
	private static List<Element> fields;

	@BeforeAll
	public static void loadSchema() throws JDOMException, IOException {
		SAXBuilder builder = new SAXBuilder();
		ClassLoader classLoader = DateGeneratorTestCase.class.getClassLoader();
		File xmlFile = new File(classLoader.getResource("./generators/testDateGenerator.xml").getFile());
		Document document = builder.build(xmlFile);

		List<Element> blocks = document.getRootElement().getChildren("block");
		fields = blocks.get(0).getChildren("field");
		EventGenerator.fieldvalues = new ArrayList<List<Trio<String, String, String>>>();

	}

	@Test
	public void testRandom() throws JDOMException, IOException, ParseException {
		Element field = fields.get(0);

		for (int i = 0; i < 1000; ++i) {
			String strResult = EventGenerator.GenerateValueSimpleType(field.getAttributeValue("type"), field);
			SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-DD");
			sdf.parse(strResult);
		}
	}

	@Test
	public void testFixedValue() throws JDOMException, IOException {
		Element field = fields.get(1);
		String strResult = EventGenerator.GenerateValueSimpleType(field.getAttributeValue("type"), field);

		assertTrue(strResult.equals("08-05-01"));
	}
}
