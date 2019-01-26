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

public class AlphanumericGeneratorTestCase {

	private static List<Element> fields;

	@BeforeAll
	public static void loadSchema() throws JDOMException, IOException {
		SAXBuilder builder = new SAXBuilder();
		ClassLoader classLoader = AlphanumericGeneratorTestCase.class.getClassLoader();
		File xmlFile = new File(classLoader.getResource("./generators/testAlphanumericGenerator.xml").getFile());
		Document document = builder.build(xmlFile);

		List<Element> blocks = document.getRootElement().getChildren("block");
		fields = blocks.get(0).getChildren("field");
		EventGenerator.fieldvalues = new ArrayList<>();
	}

	@Test
	public void testRandomWithSpecifiedLengthAndEndcharacter() throws JDOMException, IOException {
		Element field = fields.get(0);
		for (int i = 0; i < 10000; ++i) {

			String strResult = EventGenerator.GenerateValueSimpleType(field.getAttributeValue("type"), field);
			assertTrue(strResult.matches("[0123456789ABCDEF]*"));
			assertEquals(14, strResult.length());
		}
	}

	@Test
	public void testRandomWithDefaultLengthAndLowercase() throws JDOMException, IOException {
		Element field = fields.get(1);
		for (int i = 0; i < 10000; ++i) {

			String strResult = EventGenerator.GenerateValueSimpleType(field.getAttributeValue("type"), field);
			assertTrue(strResult.matches("[0123456789abcdefghijklmnopqrstuvwxyz]*"));
			assertEquals(10, strResult.length());
		}
	}

	@Test
	public void testFixed() throws JDOMException, IOException {
		Element field = fields.get(2);

		String strResult = EventGenerator.GenerateValueSimpleType(field.getAttributeValue("type"), field);
		assertEquals(String.valueOf("abc"), strResult);
	}

	@Test
	public void testRandomWithDefaultLength() throws JDOMException, IOException {
		Element field = fields.get(3);
		for (int i = 0; i < 10000; ++i) {

			String strResult = EventGenerator.GenerateValueSimpleType(field.getAttributeValue("type"), field);
			assertTrue(strResult.matches("[0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ]*"));
		}
	}

	@Test
	public void testRandomWithDefaultLengthAndEndcharacter() throws JDOMException, IOException {
		Element field = fields.get(4);
		for (int i = 0; i < 10000; ++i) {

			String strResult = EventGenerator.GenerateValueSimpleType(field.getAttributeValue("type"), field);
			assertTrue(strResult.matches("[0123456789ABCDEF]*"));
		}
	}

	@Test
	public void testRandomWithDefaultLengthAndLow() throws JDOMException, IOException {
		Element field = fields.get(5);
		for (int i = 0; i < 10000; ++i) {

			String strResult = EventGenerator.GenerateValueSimpleType(field.getAttributeValue("type"), field);
			assertTrue(strResult.matches("[0123456789abcdefghijklmnopqrstuvwxyz]*"));
		}
	}
}
