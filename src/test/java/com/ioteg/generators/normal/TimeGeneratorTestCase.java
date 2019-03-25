package com.ioteg.generators.normal;

import static org.hamcrest.MatcherAssert.assertThat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.junit.jupiter.api.Test;
import static org.hamcrest.Matchers.equalTo;

import com.ioteg.exprlang.ExprParser.ExprLangParsingException;
import com.ioteg.generators.Generable;
import com.ioteg.generators.GeneratorsFactory;
import com.ioteg.generators.exceptions.NotExistingGeneratorException;
import com.ioteg.model.Field;
import com.ioteg.resultmodel.ResultField;
import com.ioteg.resultmodel.ResultSimpleField;

public class TimeGeneratorTestCase {

	@Test
	public void testRandom() throws NotExistingGeneratorException, ExprLangParsingException, ParseException {
		Field field = new Field();
		field.setType("Time");
		field.setQuotes(true);
		field.setFormat("hh:mm");

		Generable generator = GeneratorsFactory.makeGenerator(field, null);

		List<ResultField> results = generator.generate(100);

		/* <field name="time" quotes="true" type="Time" format="hh:mm"></field> */

		for (ResultField rF : results) {
			String strResult = ((ResultSimpleField) rF).getValue();
			SimpleDateFormat sdf = new SimpleDateFormat("hh:mm");
			sdf.parse(strResult);
		}

	}

	@Test
	public void testFixedValue() throws NotExistingGeneratorException, ExprLangParsingException {
		Field field = new Field();
		field.setType("Time");
		field.setQuotes(true);
		field.setFormat("HH:mm");
		field.setValue("14:24");

		/*
		 * <field name="time" quotes="true" type="Time" value="14:24"
		 * format="HH:mm"></field>
		 */

		Generable generator = GeneratorsFactory.makeGenerator(field, null);

		String strResult = ((ResultSimpleField) generator.generate(1).get(0)).getValue();
		assertThat(strResult, equalTo("14:24"));
	}

}
