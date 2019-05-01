package com.ioteg.generation;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import com.ioteg.generation.Generable;
import com.ioteg.generation.GenerationContext;
import com.ioteg.generation.GeneratorsFactory;
import com.ioteg.model.Field;
import com.ioteg.resultmodel.ResultField;
import com.ioteg.resultmodel.ResultSimpleField;

public class DateGeneratorTestCase {

	@Test
	public void testRandom() throws Exception {
		Field field = new Field("date", true, "Date");
		field.setFormat("yy-MM-DD");

		Generable generator = GeneratorsFactory.makeGenerator(field, new GenerationContext());

		List<ResultField> results = generator.generate(100);

		/* <field name="date" quotes="true" type="Date" format="yy-MM-DD"></field> */

		for (ResultField rF : results) {
			String strResult = ((ResultSimpleField) rF).getValue();
			SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-DD");
			sdf.parse(strResult);
		}
	}

	@Test
	public void testFixedValue() throws Exception {
		Field field = new Field("date", true, "Date");
		field.setFormat("yy-mm-dd");
		field.setValue("08-05-01");

		/*
		 * <field name="date" quotes="true" type="Date" value="08-05-01"
		 * format="yy-mm-dd"></field>
		 */

		Generable generator = GeneratorsFactory.makeGenerator(field, new GenerationContext());

		String strResult = ((ResultSimpleField) generator.generate(1).get(0)).getValue();

		assertTrue(strResult.equals("08-05-01"));
	}

	@ParameterizedTest(name = "run #{index} with [{arguments}]")
	@CsvSource(value = { "1996-12,2000-12,1,YEAR,yyyy-MM,1, 1",
			"1996-06,1996-10,1,MONTH,yyyy-MM,1, 2",
			"1996-06-01,1996-10-05,1,DAY,yyyy-MM-DD,1, 5",
			"1996-06-01:06,1996-10-01:10,1,HOUR,yyyy-MM-DD:HH,1, 11",
			"1996-10-01:06:00,1996-10-01:06:08,2,MINUTE,yyyy-MM-DD:HH:mm,2, 12",
			"1996-10-01:06:08:00,1996-10-01:06:08:08,2,SECOND,yyyy-MM-DD:HH:mm:ss,2, 13",
			"1996-10-01:06:08:08:000,1996-10-01:06:08:08:008,2,MILLISECOND,yyyy-MM-DD:HH:mm:ss:SSS,2, 14",
			"2000-12,1996-12,-1,YEAR,yyyy-MM,-1, 1",
			"1996-10,1996-06,-1,MONTH,yyyy-MM,-1, 2",
			"1996-10-05,1996-06-01,-1,DAY,yyyy-MM-DD,-1, 5",
			"1996-10-01:10,1996-06-01:06,-1,HOUR,yyyy-MM-DD:HH,-1, 11",
			"1996-10-01:06:08,1996-10-01:06:00,-2,MINUTE,yyyy-MM-DD:HH:mm,-2, 12",
			"1996-10-01:06:08:08,1996-10-01:06:08:00,-2,SECOND,yyyy-MM-DD:HH:mm:ss,-2, 13",
			"1996-10-01:06:08:08:008,1996-10-01:06:08:08:000,-2,MILLISECOND,yyyy-MM-DD:HH:mm:ss:SSS,-2, 14"})

	public void testSequentialIncremental(String begin, String end,String step,String unit, String format, int difference, int type) throws Exception {
		Field field = new Field("date", true, "Date");
		field.setFormat(format);
		field.setBegin(begin);
		field.setEnd(end);
		field.setStep(step);
		field.setUnit(unit);

		/*
		 * <field name="date" quotes="true" type="Date" value="08-05-01"
		 * format="yy-mm-dd"></field>
		 */

		Generable generator = GeneratorsFactory.makeGenerator(field, new GenerationContext());
		List<ResultField> results = generator.generate(6);
		SimpleDateFormat parser = new SimpleDateFormat(field.getFormat());
		Calendar actual = Calendar.getInstance();
		Calendar next = Calendar.getInstance();
		for (int i = 0; i < results.size() - 2; ++i) {

			actual.setTime(parser.parse(((ResultSimpleField) results.get(i)).getValue()));
			next.setTime(parser.parse(((ResultSimpleField) results.get(i + 1)).getValue()));

			assertThat(next.get(type) - actual.get(type), is(difference));
		}
		
		next.setTime(parser.parse(field.getBegin()));
		actual.setTime(parser.parse(((ResultSimpleField) results.get(results.size() - 1)).getValue()));
		assertThat(actual.compareTo(next), is(0));

	}
	



}
