package com.ioteg.generators.normal;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.ioteg.generation.Generable;
import com.ioteg.generation.GenerationContext;
import com.ioteg.generation.GeneratorsFactory;
import com.ioteg.model.Field;
import com.ioteg.resultmodel.ResultField;
import com.ioteg.resultmodel.ResultSimpleField;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.lessThanOrEqualTo;

public class IntegerGeneratorTestCase {

	private static final double DEFAULT_MAX = 10.0;
	private static final double DEFAULT_MIN = 0.0;

	@Test
	public void testRandomWithSpecifiedRange() throws Exception {

		Field field = new Field("test", false, "Integer");
		field.setMin(100000.0);
		field.setMax(999999.0);

		/*
		 * <field name="test" quotes="false" type="Integer" min="100000"
		 * max="999999"></field>
		 */

		Generable generator = GeneratorsFactory.makeGenerator(field, null, new GenerationContext());

		String strResult = ((ResultSimpleField) generator.generate(1).get(0)).getValue();
		Integer result = Integer.parseInt(strResult);

		assertThat(result.intValue(), greaterThan(100000));
		assertThat(result.intValue(), lessThan(999999));
	}

	@Test
	public void testRandomWithDefaultRange() throws Exception {

		Field field = new Field("testDefaultRange", true, "Integer");
		field.setMin(DEFAULT_MIN);
		field.setMax(DEFAULT_MAX);

		/*
		 * <field name="testDefaultRange" quotes="true" type="Integer"></field>
		 */

		Generable generator = GeneratorsFactory.makeGenerator(field, null, new GenerationContext());
		List<ResultField> results = generator.generate(100);

		for (ResultField rF : results) {
			String strResult = ((ResultSimpleField) rF).getValue();
			Integer result = Integer.parseInt(strResult);
			assertThat(result, greaterThanOrEqualTo(0));
			assertThat(result, lessThanOrEqualTo(10));
		}

	}

	@Test
	public void testFixedValue() throws Exception {

		/*<field name="testDefaultValue" quotes="false" type="Integer" value="104"></field>*/
		Field field = new Field("testDefaultValue", false, "Integer");
		field.setMin(DEFAULT_MIN);
		field.setMax(DEFAULT_MAX);
		field.setValue("104");
		
		Generable generator = GeneratorsFactory.makeGenerator(field, null, new GenerationContext());
		
		String strResult = ((ResultSimpleField) generator.generate(1).get(0)).getValue();
		Integer result = Integer.parseInt(strResult);
		assertEquals(Integer.valueOf(104), result);
	}
	
	@Test
	public void testSequential() throws Exception {
		Field field = new Field("test", false, "Integer");
		field.setBegin("2");
		field.setEnd("20");
		field.setStep("1");

		/*
		 * <field name="test" quotes="false" type="Float" begin="2" step="1"
		 * end="20"></field>
		 */

		Generable generator = GeneratorsFactory.makeGenerator(field, null, new GenerationContext());
		List<ResultField> results = generator.generate(5);

		for (int i = 0; i < results.size(); ++i) {

			Integer actual = Integer.valueOf(((ResultSimpleField) results.get(i)).getValue());
			Integer next = null;
			if (i != results.size() - 1)
				next = Integer.valueOf(((ResultSimpleField) results.get(i + 1)).getValue());

			assertThat(actual, lessThanOrEqualTo(20));
			assertThat(actual, greaterThanOrEqualTo(2));
			if (next != null)
				assertThat(next - actual, equalTo(1));
		}
		
		
		field.setStep("-1");
		field.setBegin("20");
		field.setEnd("2");
		/*
		 * <field name="test" quotes="false" type="Float" begin="20" step="-1"
		 * end="2"></field>
		 */

		generator = GeneratorsFactory.makeGenerator(field, null, new GenerationContext());
		results = generator.generate(5);

		for (int i = 0; i < results.size(); ++i) {

			Integer actual = Integer.valueOf(((ResultSimpleField) results.get(i)).getValue());
			Integer next = null;
			if (i != results.size() - 1)
				next = Integer.valueOf(((ResultSimpleField) results.get(i + 1)).getValue());

			assertThat(actual, lessThanOrEqualTo(20));
			assertThat(actual, greaterThanOrEqualTo(2));
			if (next != null)
				assertThat(next - actual, equalTo(-1));
		}

	}

}
