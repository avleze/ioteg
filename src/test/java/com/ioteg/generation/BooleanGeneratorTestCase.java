package com.ioteg.generation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.text.ParseException;

import org.junit.jupiter.api.Test;

import com.ioteg.exprlang.ExprParser.ExprLangParsingException;
import com.ioteg.generation.Generable;
import com.ioteg.generation.GenerationContext;
import com.ioteg.generation.GeneratorsFactory;
import com.ioteg.generation.NotExistingGeneratorException;
import com.ioteg.model.Field;
import com.ioteg.resultmodel.ResultSimpleField;

public class BooleanGeneratorTestCase {

	private static final boolean DEFAULT_IS_NUMERIC = false;

	@Test
	public void testRandomAndNumeric() throws NotExistingGeneratorException, ExprLangParsingException, ParseException {
		Field field = new Field("boolean", false, "Boolean");
		field.setIsNumeric(true);

		Generable generator = GeneratorsFactory.makeGenerator(field, new GenerationContext());

		/** The loops are necessary in order to cover all the code **/
		String strResult = ((ResultSimpleField) generator.generate(1).get(0)).getValue();
		while (strResult.contains("0")) {
			assertEquals("0", strResult);
			strResult = ((ResultSimpleField) generator.generate(1).get(0)).getValue();
		}
		assertEquals("1", strResult);

		while (strResult.contains("1")) {
			assertEquals("1", strResult);
			strResult = ((ResultSimpleField) generator.generate(1).get(0)).getValue();
		}

		assertEquals("0", strResult);
	}

	@Test
	public void testRandomAndNotNumeric() throws NotExistingGeneratorException, ExprLangParsingException, ParseException {
		Field field = new Field("boolean", false, "Boolean");
		field.setIsNumeric(false);

		Generable generator = GeneratorsFactory.makeGenerator(field, new GenerationContext());

		String strResult = ((ResultSimpleField) generator.generate(1).get(0)).getValue();

		assertTrue("true".equals(strResult) || "false".equals(strResult));
	}

	@Test
	public void testFixedValue() throws NotExistingGeneratorException, ExprLangParsingException, ParseException {
		Field field = new Field("boolean", false, "Boolean");
		field.setValue("true");
		field.setIsNumeric(DEFAULT_IS_NUMERIC);

		Generable generator = GeneratorsFactory.makeGenerator(field, new GenerationContext());

		String strResult = ((ResultSimpleField) generator.generate(1).get(0)).getValue();

		assertTrue("true".equals(strResult));
	}
}
