package ioteg;


import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.ioteg.EventGenerator;

public class TimeGeneratorTestCase {
	private static List<Element> fields;

	@BeforeAll
	public static void loadSchema() throws JDOMException, IOException {
		SAXBuilder builder = new SAXBuilder();
		ClassLoader classLoader = StringGeneratorTestCase.class.getClassLoader();
		File xmlFile = new File(classLoader.getResource("./generators/testTimeGenerator.xml").getFile());
		Document document = builder.build(xmlFile);

		List<Element> blocks = document.getRootElement().getChildren("block");
		fields = blocks.get(0).getChildren("field");
	}

	@Test
	public void testRandom() throws JDOMException, IOException, ParseException {
		Element field = fields.get(0);

		for (int i = 0; i < 1000; ++i) {
			String strResult = EventGenerator.GenerateValueSimpleType(field.getAttributeValue("type"), field);
			SimpleDateFormat sdf = new SimpleDateFormat("hh:mm");
			sdf.parse(strResult);
		}
		
	}

	@Test
	public void testFixedValue() throws JDOMException, IOException {
		Element field = fields.get(1);
		String strResult = EventGenerator.GenerateValueSimpleType(field.getAttributeValue("type"), field);

		assertTrue(strResult.equals("14:24"));
	}

}
