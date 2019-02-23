package com.ioteg.generators.queryrestriction;

import static org.junit.Assert.assertThat;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import com.ioteg.Trio;
import com.ioteg.exprlang.ExprParser.ExprLangParsingException;
import com.ioteg.generators.Generable;
import com.ioteg.generators.GeneratorsFactory;
import com.ioteg.generators.exceptions.NotExistingGeneratorException;
import com.ioteg.model.Field;
import com.ioteg.resultmodel.ResultSimpleField;

import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.lessThanOrEqualTo;
import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.matchesPattern;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;

public class FloatGeneratorQueryRestrictionTestCase {

	@Test
	public void testFloatQueryRestrictionLessOperator() throws NotExistingGeneratorException, ExprLangParsingException {
		Field field = new Field();
		field.setName("field14");
		field.setType("Float");
		field.setMin(-50.0);
		field.setMax(50.0);
		field.setQuotes(false);

		List<Trio<String, String, String>> restrictions = new ArrayList<>();
		restrictions.add(new Trio<>("field14", "<", "24"));

		Generable generator = GeneratorsFactory.makeQueryRestrictionGenerator(field, restrictions);
		ResultSimpleField rF = (ResultSimpleField) generator.generate(1).get(0);

		Float result = Float.parseFloat(rF.getValue());
		assertThat(result, lessThan(24f));
		assertThat(result, greaterThanOrEqualTo(-50f));
	}

	@Test
	public void testFloatQueryRestrictionLessOperatorWithMinValue()
			throws NotExistingGeneratorException, ExprLangParsingException {
		Field field = new Field();
		field.setName("field16");
		field.setType("Float");
		field.setMin(-20.58);
		field.setMax(50.0);
		field.setQuotes(false);

		List<Trio<String, String, String>> restrictions = new ArrayList<>();
		restrictions.add(new Trio<>("field16", "<", "24"));

		Generable generator = GeneratorsFactory.makeQueryRestrictionGenerator(field, restrictions);
		ResultSimpleField rF = (ResultSimpleField) generator.generate(1).get(0);

		Float result = Float.parseFloat(rF.getValue());
		assertThat(result, lessThan(24f));
		assertThat(result, greaterThanOrEqualTo(-20.58f));
	}

	@Test
	public void testFloatQueryRestrictionLessOperatorWithNegativeValue()
			throws NotExistingGeneratorException, ExprLangParsingException {
		Field field = new Field();
		field.setName("field14");
		field.setType("Float");
		field.setMin(-50.0);
		field.setMax(50.0);
		field.setQuotes(false);

		List<Trio<String, String, String>> restrictions = new ArrayList<>();
		restrictions.add(new Trio<>("field14", "<", "-24"));

		Generable generator = GeneratorsFactory.makeQueryRestrictionGenerator(field, restrictions);
		ResultSimpleField rF = (ResultSimpleField) generator.generate(1).get(0);

		Float result = Float.parseFloat(rF.getValue());
		assertThat(result, lessThan(-24f));
		assertThat(result, greaterThanOrEqualTo(-50f));
	}

	@Test
	public void testFloatQueryRestrictionGreaterOperator()
			throws NotExistingGeneratorException, ExprLangParsingException {
		Field field = new Field();
		field.setName("field14");
		field.setType("Float");
		field.setMin(-50.0);
		field.setMax(50.0);
		field.setQuotes(false);

		List<Trio<String, String, String>> restrictions = new ArrayList<>();
		restrictions.add(new Trio<>("field14", ">", "24"));

		Generable generator = GeneratorsFactory.makeQueryRestrictionGenerator(field, restrictions);
		ResultSimpleField rF = (ResultSimpleField) generator.generate(1).get(0);

		Float result = Float.parseFloat(rF.getValue());
		assertThat(result, greaterThan(24f));
		assertThat(result, lessThanOrEqualTo(50f));
	}

	@Test
	public void testFloatQueryRestrictionGreaterOperatorWithMaxValue()
			throws NotExistingGeneratorException, ExprLangParsingException {
		Field field = new Field();
		field.setName("field15");
		field.setType("Float");
		field.setMax(200.34);
		field.setQuotes(false);

		List<Trio<String, String, String>> restrictions = new ArrayList<>();
		restrictions.add(new Trio<>("field15", ">", "24"));

		Generable generator = GeneratorsFactory.makeQueryRestrictionGenerator(field, restrictions);
		ResultSimpleField rF = (ResultSimpleField) generator.generate(1).get(0);

		Float result = Float.parseFloat(rF.getValue());
		assertThat(result, greaterThan(24f));
		assertThat(result, lessThanOrEqualTo(200.34f));
	}

	@Test
	public void testFloatQueryRestrictionEqualOperator()
			throws NotExistingGeneratorException, ExprLangParsingException {
		Field field = new Field();
		field.setName("field14");
		field.setType("Float");
		field.setMin(-50.0);
		field.setMax(50.0);
		field.setQuotes(false);

		List<Trio<String, String, String>> restrictions = new ArrayList<>();
		restrictions.add(new Trio<>("field14", "=", "24"));

		Generable generator = GeneratorsFactory.makeQueryRestrictionGenerator(field, restrictions);
		ResultSimpleField rF = (ResultSimpleField) generator.generate(1).get(0);

		Float result = Float.parseFloat(rF.getValue());
		assertThat(result, equalTo(24f));
	}

	@Test
	public void testFloatQueryRestrictionNotEqualOperator()
			throws NotExistingGeneratorException, ExprLangParsingException {
		Field field = new Field();
		field.setName("field14");
		field.setType("Float");
		field.setMin(-50.0);
		field.setMax(50.0);
		field.setQuotes(false);

		List<Trio<String, String, String>> restrictions = new ArrayList<>();
		restrictions.add(new Trio<>("field14", "!=", "24"));

		Generable generator = GeneratorsFactory.makeQueryRestrictionGenerator(field, restrictions);
		ResultSimpleField rF = (ResultSimpleField) generator.generate(1).get(0);

		Float result = Float.parseFloat(rF.getValue());
		assertThat(result, not(24f));
	}

	@Test
	public void testFloatQueryRestrictionNotEqualOperatorWithMinAndMax()
			throws NotExistingGeneratorException, ExprLangParsingException {
		Field field = new Field();
		field.setName("field17");
		field.setType("Float");
		field.setMin(-100.134);
		field.setMax(134.1);
		field.setQuotes(false);

		List<Trio<String, String, String>> restrictions = new ArrayList<>();
		restrictions.add(new Trio<>("field14", "!=", "20"));

		Generable generator = GeneratorsFactory.makeQueryRestrictionGenerator(field, restrictions);
		ResultSimpleField rF = (ResultSimpleField) generator.generate(1).get(0);

		Float result = Float.parseFloat(rF.getValue());
		assertThat(result, greaterThanOrEqualTo(-100.134f));
		assertThat(result, lessThanOrEqualTo(134.1f));
		assertThat(result, not(20f));
	}

	@Test
	public void testFloatQueryRestrictionGreaterEqualOperator()
			throws NotExistingGeneratorException, ExprLangParsingException {
		Field field = new Field();
		field.setName("field14");
		field.setType("Float");
		field.setMin(-50.0);
		field.setMax(50.0);
		field.setQuotes(false);

		List<Trio<String, String, String>> restrictions = new ArrayList<>();
		restrictions.add(new Trio<>("field14", ">=", "24"));

		Generable generator = GeneratorsFactory.makeQueryRestrictionGenerator(field, restrictions);
		ResultSimpleField rF = (ResultSimpleField) generator.generate(1).get(0);

		Float result = Float.parseFloat(rF.getValue());
		assertThat(result, greaterThanOrEqualTo(24f));
		assertThat(result, lessThanOrEqualTo(50f));

	}

	@Test
	public void testFloatQueryRestrictionGreaterEqualOperatorWithMaxAttribute()
			throws NotExistingGeneratorException, ExprLangParsingException {
		Field field = new Field();
		field.setName("field15");
		field.setType("Float");
		field.setMax(200.34);
		field.setQuotes(false);

		List<Trio<String, String, String>> restrictions = new ArrayList<>();
		restrictions.add(new Trio<>("field15", ">=", "24"));

		Generable generator = GeneratorsFactory.makeQueryRestrictionGenerator(field, restrictions);
		ResultSimpleField rF = (ResultSimpleField) generator.generate(1).get(0);

		Float result = Float.parseFloat(rF.getValue());
		assertThat(result, greaterThanOrEqualTo(24f));
		assertThat(result, lessThanOrEqualTo(200.34f));
	}

	@Test
	public void testFloatQueryRestrictionLessEqualOperator()
			throws NotExistingGeneratorException, ExprLangParsingException {
		Field field = new Field();
		field.setName("field14");
		field.setType("Float");
		field.setMin(-50.0);
		field.setMax(50.0);
		field.setQuotes(false);

		List<Trio<String, String, String>> restrictions = new ArrayList<>();
		restrictions.add(new Trio<>("field14", "<=", "24"));

		Generable generator = GeneratorsFactory.makeQueryRestrictionGenerator(field, restrictions);
		ResultSimpleField rF = (ResultSimpleField) generator.generate(1).get(0);

		Float result = Float.parseFloat(rF.getValue());

		assertThat(result, lessThanOrEqualTo(24f));
		assertThat(result, greaterThanOrEqualTo(-50f));
	}

	@Test
	public void testFloatQueryRestrictionLessEqualOperatorWithMinValue()
			throws NotExistingGeneratorException, ExprLangParsingException {
		Field field = new Field();
		field.setName("field16");
		field.setType("Float");
		field.setMin(-20.58);
		field.setQuotes(false);

		List<Trio<String, String, String>> restrictions = new ArrayList<>();
		restrictions.add(new Trio<>("field16", "<=", "24"));

		Generable generator = GeneratorsFactory.makeQueryRestrictionGenerator(field, restrictions);
		ResultSimpleField rF = (ResultSimpleField) generator.generate(1).get(0);

		Float result = Float.parseFloat(rF.getValue());

		assertThat(result, lessThanOrEqualTo(24f));
		assertThat(result, greaterThanOrEqualTo(-20.58f));
	}

	@Test
	public void testFloatQueryRestrictionLessEqualOperatorWithNegativeValue()
			throws NotExistingGeneratorException, ExprLangParsingException {
		Field field = new Field();
		field.setName("field14");
		field.setType("Float");
		field.setMin(-50.0);
		field.setMax(50.0);
		field.setQuotes(false);

		List<Trio<String, String, String>> restrictions = new ArrayList<>();
		restrictions.add(new Trio<>("field14", "<=", "-24"));

		Generable generator = GeneratorsFactory.makeQueryRestrictionGenerator(field, restrictions);
		ResultSimpleField rF = (ResultSimpleField) generator.generate(1).get(0);

		Float result = Float.parseFloat(rF.getValue());

		assertThat(result, lessThanOrEqualTo(-24f));
		assertThat(result, greaterThanOrEqualTo(-50f));
	}

	@RepeatedTest(10)
	public void testFloatQueryRestrictionWithPrecision()
			throws NotExistingGeneratorException, ExprLangParsingException {
		Field field = new Field();
		field.setName("field18");
		field.setType("Float");
		field.setPrecision(3);
		field.setMin(-100.134234);
		field.setMax(134.10034);
		field.setQuotes(false);

		List<Trio<String, String, String>> restrictions = new ArrayList<>();
		restrictions.add(new Trio<>("field18", "<=", "-24"));

		Generable generator = GeneratorsFactory.makeQueryRestrictionGenerator(field, restrictions);
		ResultSimpleField rF = (ResultSimpleField) generator.generate(1).get(0);

		Float result = Float.parseFloat(rF.getValue());

		assertThat(result, lessThanOrEqualTo(-24f));
		assertThat(result, greaterThanOrEqualTo(-100.134234f));
		assertThat(rF.getValue(), matchesPattern("-?\\d+\\.\\d{3}"));
	}
}
