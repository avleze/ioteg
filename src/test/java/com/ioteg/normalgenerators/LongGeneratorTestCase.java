package com.ioteg.normalgenerators;

import static org.junit.Assert.assertThat;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.ioteg.exprlang.ExprParser.ExprLangParsingException;
import com.ioteg.generators.Generable;
import com.ioteg.generators.GeneratorsFactory;
import com.ioteg.generators.exceptions.NotExistingGeneratorException;
import com.ioteg.model.Field;
import com.ioteg.resultmodel.ResultField;
import com.ioteg.resultmodel.ResultSimpleField;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.lessThanOrEqualTo;
import static org.hamcrest.Matchers.equalTo;

public class LongGeneratorTestCase {

	@Test
	public void testRandomWithSpecifiedRange() throws NotExistingGeneratorException, ExprLangParsingException {

		Field field = new Field();
		field.setMin(-10000000.0);
		field.setMax(0.0);
		field.setName("test");
		field.setQuotes(false);
		field.setType("Long");

		/*
		 * <field name="test" quotes="false" type="Long" min="-10000000"
		 * max="0"></field>
		 */

		Generable generator = GeneratorsFactory.makeGenerator(field, null);

		String strResult = ((ResultSimpleField) generator.generate(1).get(0)).getValue();

		Long result = Long.parseLong(strResult);
		assertThat(result, greaterThan(Long.valueOf("-10000000")));
		assertThat(result, lessThanOrEqualTo(Long.valueOf("0")));
	}

	@Test
	public void testRandomWithDefaultRange() throws NotExistingGeneratorException, ExprLangParsingException {
		Field field = new Field();
		field.setMin(0.0);
		field.setMax(10.0);
		field.setName("testDefaultRange");
		field.setQuotes(true);
		field.setType("Long");

		/*
		 * 	<field name="testDefaultRange" quotes="true" type="Long"></field>
		 */

		Generable generator = GeneratorsFactory.makeGenerator(field, null);
		List<ResultField> results = generator.generate(100);

		for (ResultField rF : results) {
			String strResult = ((ResultSimpleField) rF).getValue();
			Long result = Long.valueOf(strResult);
			assertThat(result, greaterThanOrEqualTo(0L));
			assertThat(result, lessThanOrEqualTo(9L));
		}
	}

	@Test
	public void testFixedValue() throws NotExistingGeneratorException, ExprLangParsingException {
		
		Field field = new Field();
		field.setMin(0.0);
		field.setMax(10.0);
		field.setName("testDefaultRange");
		field.setQuotes(true);
		field.setType("Long");
		field.setValue("9223372036854775807");

		/*<field name="testDefaultValue" quotes="false" type="Long" value="9223372036854775807"></field>*/

		Generable generator = GeneratorsFactory.makeGenerator(field, null);
		String strResult = ((ResultSimpleField) generator.generate(1).get(0)).getValue();
		Long result = Long.parseLong(strResult);
		assertThat(result, equalTo(9223372036854775807L));
	}
}
