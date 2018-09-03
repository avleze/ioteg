package ioteg;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.junit.BeforeClass;
import org.junit.Test;

import com.ioteg.EventGenerator;

public class IntegerGeneratorTestCase {

	private static List<Element> fields;

	@BeforeClass
	public static void loadSchema() throws JDOMException, IOException {
		SAXBuilder builder = new SAXBuilder();
		ClassLoader classLoader = IntegerGeneratorTestCase.class.getClassLoader();
		File xmlFile = new File(classLoader.getResource("./generators/testRandomIntegerGenerator.xml").getFile());
		Document document = builder.build(xmlFile);

		List<Element> blocks = document.getRootElement().getChildren("block");
		fields = blocks.get(0).getChildren("field");
	}

	@Test
	public void testRandomWithSpecifiedRange() throws JDOMException, IOException {

		/** Test within a specified range **/
		Element field = fields.get(0);
		String strResult = EventGenerator.GenerateValueSimpleType(field.getAttributeValue("type"), field);
		Integer result = Integer.parseInt(strResult);

		assertTrue(100000 < result);
		assertTrue(result < 999999);
	}

	@Test
	public void testRandomWithDefaultRange() throws JDOMException, IOException {

		/** Test of default range **/
		Element field = fields.get(1);

		for (int i = 0; i < 100; ++i) {
			String strResult = EventGenerator.GenerateValueSimpleType(field.getAttributeValue("type"), field);
			Integer result = Integer.parseInt(strResult);
			assertTrue(0 <= result);
			assertTrue(result <= 9);
		}

	}

	@Test
	public void testFixedValue() throws JDOMException, IOException {

		/** Test of specified value **/
		Element field = fields.get(2);
		String strResult = EventGenerator.GenerateValueSimpleType(field.getAttributeValue("type"), field);
		Integer result = Integer.parseInt(strResult);
		assertEquals(Integer.valueOf(104), result);
	}

}
