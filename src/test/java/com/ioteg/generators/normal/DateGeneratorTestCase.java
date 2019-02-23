package com.ioteg.generators.normal;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.ioteg.exprlang.ExprParser.ExprLangParsingException;
import com.ioteg.generators.Generable;
import com.ioteg.generators.GeneratorsFactory;
import com.ioteg.generators.exceptions.NotExistingGeneratorException;
import com.ioteg.model.Field;
import com.ioteg.resultmodel.ResultField;
import com.ioteg.resultmodel.ResultSimpleField;

public class DateGeneratorTestCase {

	@Test
	public void testRandom() throws NotExistingGeneratorException, ExprLangParsingException, ParseException {
		Field field = new Field();
		field.setType("Date");
		field.setQuotes(true);
		field.setFormat("yy-MM-DD");

		Generable generator = GeneratorsFactory.makeGenerator(field, null);

		List<ResultField> results = generator.generate(100);

		/* <field name="date" quotes="true" type="Date" format="yy-MM-DD"></field> */

		for (ResultField rF : results) {
			String strResult = ((ResultSimpleField) rF).getValue();
			SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-DD");
			sdf.parse(strResult);
		}
	}

	@Test
	public void testFixedValue() throws NotExistingGeneratorException, ExprLangParsingException {
		Field field = new Field();
		field.setType("Date");
		field.setQuotes(true);
		field.setFormat("yy-mm-dd");
		field.setValue("08-05-01");

		/*
		 * <field name="date" quotes="true" type="Date" value="08-05-01"
		 * format="yy-mm-dd"></field>
		 */

		Generable generator = GeneratorsFactory.makeGenerator(field, null);

		String strResult = ((ResultSimpleField) generator.generate(1).get(0)).getValue();

		assertTrue(strResult.equals("08-05-01"));
	}
}
