package com.ioteg.generators.normal;

import static org.hamcrest.MatcherAssert.assertThat;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.ioteg.generators.Generable;
import com.ioteg.generators.GeneratorsFactory;
import com.ioteg.generators.context.GenerationContext;
import com.ioteg.model.Field;
import com.ioteg.resultmodel.ResultField;
import com.ioteg.resultmodel.ResultSimpleField;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.lessThanOrEqualTo;
import static org.hamcrest.Matchers.equalTo;

public class LongGeneratorTestCase {

	private static final double DEFAULT_MAX = 9.0;
	private static final double DEFAULT_MIN = 0.0;

	@Test
	public void testRandomWithSpecifiedRange() throws Exception {

		Field field = new Field("test", false, "Long");
		field.setMin(-10000000.0);
		field.setMax(0.0);

		/*
		 * <field name="test" quotes="false" type="Long" min="-10000000"
		 * max="0"></field>
		 */

		Generable generator = GeneratorsFactory.makeGenerator(field, null, new GenerationContext());

		String strResult = ((ResultSimpleField) generator.generate(1).get(0)).getValue();

		Long result = Long.parseLong(strResult);
		assertThat(result, greaterThan(Long.valueOf("-10000000")));
		assertThat(result, lessThanOrEqualTo(Long.valueOf("0")));
	}

	@Test
	public void testRandomWithDefaultRange() throws Exception {
		Field field = new Field("testDefaultRange", true, "Long");
		field.setMin(DEFAULT_MIN);
		field.setMax(DEFAULT_MAX);

		/*
		 * 	<field name="testDefaultRange" quotes="true" type="Long"></field>
		 */

		Generable generator = GeneratorsFactory.makeGenerator(field, null, new GenerationContext());
		List<ResultField> results = generator.generate(100);

		for (ResultField rF : results) {
			String strResult = ((ResultSimpleField) rF).getValue();
			Long result = Long.valueOf(strResult);
			assertThat(result, greaterThanOrEqualTo(0L));
			assertThat(result, lessThanOrEqualTo(9L));
		}
	}

	@Test
	public void testFixedValue() throws Exception {
		Field field = new Field("testFixedValue", true, "Long");
		field.setMin(DEFAULT_MIN);
		field.setMax(DEFAULT_MAX);
		field.setValue("9223372036854775807");

		/*<field name="testDefaultValue" quotes="false" type="Long" value="9223372036854775807"></field>*/

		Generable generator = GeneratorsFactory.makeGenerator(field, null, new GenerationContext());
		String strResult = ((ResultSimpleField) generator.generate(1).get(0)).getValue();
		Long result = Long.parseLong(strResult);
		assertThat(result, equalTo(9223372036854775807L));
	}
}
