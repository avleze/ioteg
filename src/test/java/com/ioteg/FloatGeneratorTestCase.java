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
import com.ioteg.exprlang.ExprParser.ExprLangParsingException;
import com.ioteg.generators.exceptions.NotExistingGeneratorException;

public class FloatGeneratorTestCase {
	private static List<Element> fields;

	@BeforeAll
	public static void loadSchema() throws JDOMException, IOException {
		SAXBuilder builder = new SAXBuilder();
		ClassLoader classLoader = FloatGeneratorTestCase.class.getClassLoader();
		File xmlFile = new File(classLoader.getResource("./generators/testRandomFloatGenerator.xml").getFile());
		Document document = builder.build(xmlFile);

		List<Element> blocks = document.getRootElement().getChildren("block");
		fields = blocks.get(0).getChildren("field");
		EventGenerator.fieldvalues = new ArrayList<>();
	}

	@Test
	public void testRandomWithDefaultRange() throws JDOMException, IOException, NotExistingGeneratorException, ExprLangParsingException {

		Element field = fields.get(0);

		for (int i = 0; i < 10000; ++i) {
			String strResult = EventGenerator.generateValueSimpleType(field);
			Double result = Double.parseDouble(strResult);
			assertTrue(0.0 <= result);
			assertTrue(result <= 10);
		}
	}

	@Test
	public void testRandomWithSpecifiedRange() throws JDOMException, IOException, NotExistingGeneratorException, ExprLangParsingException {

		/** Test within a specified range **/
		Element field = fields.get(1);

		for (int i = 0; i < 10000; ++i) {
			String strResult = EventGenerator.generateValueSimpleType(field);
			Double result = Double.parseDouble(strResult);
			assertTrue(23.54 <= result);
			assertTrue(result <= 32.58);
		}

	}

	@Test
	public void testFixedValue() throws JDOMException, IOException, NotExistingGeneratorException, ExprLangParsingException {

		/** Test of specified value **/
		Element field = fields.get(2);
		String strResult = EventGenerator.generateValueSimpleType(field);
		Double result = Double.parseDouble(strResult);
		assertEquals(Double.valueOf("104.567"), result);
	}

}
