package com.ioteg.generators.normal;

import static org.hamcrest.MatcherAssert.assertThat;

import java.text.ParseException;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.ioteg.exprlang.ExprParser.ExprLangParsingException;
import com.ioteg.generators.Generable;
import com.ioteg.generators.GeneratorsFactory;
import com.ioteg.generators.exceptions.NotExistingGeneratorException;
import com.ioteg.model.Field;
import com.ioteg.resultmodel.ResultField;
import com.ioteg.resultmodel.ResultSimpleField;
import static org.hamcrest.Matchers.lessThanOrEqualTo;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.equalTo;

public class FloatGeneratorTestCase {

	private static final double DEFAULT_MAX = 10.0;
	private static final double DEFAULT_MIN = 0.0;

	@Test
	public void testRandomWithDefaultRange() throws NotExistingGeneratorException, ExprLangParsingException, ParseException {
		Field field = new Field("test", false);
		field.setMin(DEFAULT_MIN);
		field.setMax(DEFAULT_MAX);
		field.setPrecision(2);
		field.setType("Float");

		/* <field name="test" quotes="false" precision="2" type="Float"></field> */

		Generable generator = GeneratorsFactory.makeGenerator(field, null);
		List<ResultField> results = generator.generate(100);

		for (ResultField rF : results) {
			String strResult = ((ResultSimpleField) rF).getValue();
			Double result = Double.parseDouble(strResult);
			assertThat(result, greaterThanOrEqualTo(0.0));
			assertThat(result, lessThanOrEqualTo(10.0));
		}
	}

	@Test
	public void testRandomWithSpecifiedRange() throws NotExistingGeneratorException, ExprLangParsingException, ParseException {
		Field field = new Field("test", false);
		field.setMin(23.54);
		field.setMax(32.58);
		field.setPrecision(2);
		field.setType("Float");

		/*
		 * <field name="test" quotes="false" type="Float" min="23.54"
		 * max="32.58"></field>
		 */

		Generable generator = GeneratorsFactory.makeGenerator(field, null);
		List<ResultField> results = generator.generate(100);

		for (ResultField rF : results) {
			String strResult = ((ResultSimpleField) rF).getValue();
			Double result = Double.parseDouble(strResult);
			assertThat(result, greaterThanOrEqualTo(23.54));
			assertThat(result, lessThanOrEqualTo(32.58));
		}

	}

	@Test
	public void testFixedValue() throws NotExistingGeneratorException, ExprLangParsingException, ParseException {
		Field field = new Field("test", false);
		field.setValue("104.567");
		field.setType("Float");

		/* <field name="test" quotes="false" type="Float" value="104.567"></field> */

		Generable generator = GeneratorsFactory.makeGenerator(field, null);
		String strResult = ((ResultSimpleField) generator.generate(1).get(0)).getValue();
		Double result = Double.parseDouble(strResult);
		assertThat(result, equalTo(104.567));
	}

	@Test
	public void testSequential() throws NotExistingGeneratorException, ExprLangParsingException, ParseException {
		Field field = new Field("test", false);

		field.setBegin("2.25");
		field.setEnd("20.0");
		field.setStep("1.25");
		field.setType("Float");

		/*
		 * <field name="test" quotes="false" type="Float" min="2.25" step="1.25"
		 * max="20.0"></field>
		 */

		Generable generator = GeneratorsFactory.makeGenerator(field, null);
		List<ResultField> results = generator.generate(5);

		for (int i = 0; i < results.size(); ++i) {

			Double actual = Double.valueOf(((ResultSimpleField) results.get(i)).getValue());
			Double next = null;
			if (i != results.size() - 1)
				next = Double.valueOf(((ResultSimpleField) results.get(i + 1)).getValue());

			assertThat(actual, lessThanOrEqualTo(20.0));
			assertThat(actual, greaterThanOrEqualTo(2.25));
			if (next != null)
				assertThat(next - actual, equalTo(1.25));
		}
		
		
		field.setStep("-1.25");
		field.setBegin("20.0");
		field.setEnd("2.25");
		/*
		 * <field name="test" quotes="false" type="Float" min="2.25" step="-1.25"
		 * max="20.0"></field>
		 */

		generator = GeneratorsFactory.makeGenerator(field, null);
		results = generator.generate(5);

		for (int i = 0; i < results.size(); ++i) {

			Double actual = Double.valueOf(((ResultSimpleField) results.get(i)).getValue());
			Double next = null;
			if (i != results.size() - 1)
				next = Double.valueOf(((ResultSimpleField) results.get(i + 1)).getValue());

			assertThat(actual, lessThanOrEqualTo(20.0));
			assertThat(actual, greaterThanOrEqualTo(2.25));
			if (next != null)
				assertThat(next - actual, equalTo(-1.25));
		}

	}

}
