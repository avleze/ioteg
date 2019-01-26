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

public class BooleanGeneratorTestCase {

	private static List<Element> fields;

	@BeforeAll
	public static void loadSchema() throws JDOMException, IOException {
		SAXBuilder builder = new SAXBuilder();
		ClassLoader classLoader = BooleanGeneratorTestCase.class.getClassLoader();
		File xmlFile = new File(classLoader.getResource("./generators/testRandomBooleanGenerator.xml").getFile());
		Document document = builder.build(xmlFile);

		List<Element> blocks = document.getRootElement().getChildren("block");
		fields = blocks.get(0).getChildren("field");
		EventGenerator.fieldvalues = new ArrayList<>();
	}

	@Test
	public void testRandomAndNumeric() throws JDOMException, IOException {

		Element field = fields.get(0);
		/** The loops are necessary in order to cover all the code **/
		String strResult = EventGenerator.GenerateValueSimpleType(field.getAttributeValue("type"), field);
		while (strResult.contains("0")) {
			assertEquals("0", strResult);
			strResult = EventGenerator.GenerateValueSimpleType(field.getAttributeValue("type"), field);
		}
		assertEquals("1", strResult);

		while (strResult.contains("1")) {
			assertEquals("1", strResult);
			strResult = EventGenerator.GenerateValueSimpleType(field.getAttributeValue("type"), field);
		}

		assertEquals("0", strResult);
	}

	@Test
	public void testRandomAndNotNumeric() throws JDOMException, IOException {

		Element field = fields.get(1);
		String strResult = EventGenerator.GenerateValueSimpleType(field.getAttributeValue("type"), field);

		assertTrue("true".equals(strResult) || "false".equals(strResult));
	}

	@Test
	public void testFixedValue() throws JDOMException, IOException {

		Element field = fields.get(2);
		String strResult = EventGenerator.GenerateValueSimpleType(field.getAttributeValue("type"), field);

		assertTrue("true".equals(strResult));
	}
}
