package com.ioteg.generators.normal;

import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.ioteg.exprlang.ExprParser.ExprLangParsingException;
import com.ioteg.generators.Generable;
import com.ioteg.generators.GeneratorsFactory;
import com.ioteg.generators.exceptions.NotExistingGeneratorException;
import com.ioteg.model.Field;
import com.ioteg.resultmodel.ResultField;
import com.ioteg.resultmodel.ResultSimpleField;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.lessThanOrEqualTo;

public class IntegerGeneratorTestCase {

	private static final double DEFAULT_MAX = 9.0;
	private static final double DEFAULT_MIN = 0.0;

	@Test
	public void testRandomWithSpecifiedRange() throws NotExistingGeneratorException, ExprLangParsingException {

		Field field = new Field();
		field.setMin(100000.0);
		field.setMax(999999.0);
		field.setName("test");
		field.setQuotes(false);
		field.setType("Integer");

		/*
		 * <field name="test" quotes="false" type="Integer" min="100000"
		 * max="999999"></field>
		 */

		Generable generator = GeneratorsFactory.makeGenerator(field, null);

		String strResult = ((ResultSimpleField) generator.generate(1).get(0)).getValue();
		Integer result = Integer.parseInt(strResult);

		assertThat(result.intValue(), greaterThan(100000));
		assertThat(result.intValue(), lessThan(999999));
	}

	@Test
	public void testRandomWithDefaultRange() throws NotExistingGeneratorException, ExprLangParsingException {

		Field field = new Field();
		field.setMin(DEFAULT_MIN);
		field.setMax(DEFAULT_MAX);
		field.setName("testDefaultRange");
		field.setQuotes(true);
		field.setType("Integer");

		/*
		 * <field name="testDefaultRange" quotes="true" type="Integer"></field>
		 */

		Generable generator = GeneratorsFactory.makeGenerator(field, null);
		List<ResultField> results = generator.generate(100);

		for (ResultField rF : results) {
			String strResult = ((ResultSimpleField) rF).getValue();
			Integer result = Integer.parseInt(strResult);
			assertThat(result, greaterThanOrEqualTo(0));
			assertThat(result, lessThanOrEqualTo(9));
		}

	}

	@Test
	public void testFixedValue() throws NotExistingGeneratorException, ExprLangParsingException {

		/*<field name="testDefaultValue" quotes="false" type="Integer" value="104"></field>*/
		Field field = new Field();
		field.setMin(DEFAULT_MIN);
		field.setMax(DEFAULT_MAX);
		field.setName("testDefaultValue");
		field.setQuotes(false);
		field.setType("Integer");
		field.setValue("104");
		
		Generable generator = GeneratorsFactory.makeGenerator(field, null);
		
		String strResult = ((ResultSimpleField) generator.generate(1).get(0)).getValue();
		Integer result = Integer.parseInt(strResult);
		assertEquals(Integer.valueOf(104), result);
	}

}
