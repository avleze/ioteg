package com.ioteg;

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

public class StringGeneratorTestCase {

	private static List<Element> fields;

	@BeforeAll
	public static void loadSchema() throws JDOMException, IOException {
		SAXBuilder builder = new SAXBuilder();
		ClassLoader classLoader = StringGeneratorTestCase.class.getClassLoader();
		File xmlFile = new File(classLoader.getResource("./generators/testRandomStringGenerator.xml").getFile());
		Document document = builder.build(xmlFile);

		List<Element> blocks = document.getRootElement().getChildren("block");
		fields = blocks.get(0).getChildren("field");
		EventGenerator.fieldvalues = new ArrayList<>();

	}

	@Test
	public void testRandomWithDefaultLength() throws JDOMException, IOException {
		Element field = fields.get(0);
		String strResult = EventGenerator.generateValueSimpleType(field.getAttributeValue("type"), field);

		assertTrue(strResult.matches("[ABCDEFGHIJKLMNOPQRSTUVWXYZ]*"));
		assertEquals(10, strResult.length());
	}

	@Test
	public void testRandomWithSpecifiedLength() throws JDOMException, IOException {
		Element field = fields.get(1);
		String strResult = EventGenerator.generateValueSimpleType(field.getAttributeValue("type"), field);

		assertTrue(strResult.matches("[ABCDEFGHIJKLMNOPQRSTUVWXYZ]*"));
		assertEquals(24, strResult.length());
	}

	@Test
	public void testRandomWithLowercase() throws JDOMException, IOException {

		Element field = fields.get(2);
		String strResult = EventGenerator.generateValueSimpleType(field.getAttributeValue("type"), field);

		assertTrue(strResult.matches("[abcdefghijklmnopqrstuvwxyz]*"));
		assertEquals(24, strResult.length());
	}

	@Test
	public void testRandomWithDefaultLengthAndLowercase() throws JDOMException, IOException {
		Element field = fields.get(3);
		String strResult = EventGenerator.generateValueSimpleType(field.getAttributeValue("type"), field);

		assertTrue(strResult.matches("[abcdefghijklmnopqrstuvwxyz]*"));
		assertEquals(10, strResult.length());
	}

	@Test
	public void testRandomWithDefaultLengthAndEndCharacter() throws JDOMException, IOException {
		Element field = fields.get(4);
		String strResult = EventGenerator.generateValueSimpleType(field.getAttributeValue("type"), field);

		assertTrue(strResult.matches("[abcdefg]*"));
		assertEquals(10, strResult.length());
	}

	@Test
	public void testRandomWithSpecifiedLengthAndEndCharacter() throws JDOMException, IOException {
		Element field = fields.get(5);
		String strResult = EventGenerator.generateValueSimpleType(field.getAttributeValue("type"), field);

		assertTrue(strResult.matches("[abcdefg]*"));
		assertEquals(24, strResult.length());
	}

	@Test
	public void testFixedValue() throws JDOMException, IOException {
		Element field = fields.get(6);
		String strResult = EventGenerator.generateValueSimpleType(field.getAttributeValue("type"), field);

		assertTrue(strResult.equals("ABC"));
		assertEquals(3, strResult.length());
	}
}
