package com.ioteg.generators.queryrestriction;

import static org.hamcrest.MatcherAssert.assertThat;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

import com.ioteg.eplutils.Trio;
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
import static org.hamcrest.Matchers.equalTo;

import static org.hamcrest.Matchers.not;

public class IntegerGeneratorQueryRestrictionTestCase {

	@Test
	public void testIntegerQueryRestrictionLessOperator()
			throws NotExistingGeneratorException, ExprLangParsingException {
		Field field = new Field();
		field.setType("Integer");
		field.setQuotes(false);
		field.setName("field1");
		field.setMin(0.0);
		field.setMax(9.0);

		List<Trio<String, String, String>> restrictions = new ArrayList<>();
		restrictions.add(new Trio<>("field1", "<", "24"));

		Generable generator = GeneratorsFactory.makeQueryRestrictionGenerator(field, restrictions);
		ResultSimpleField rF = (ResultSimpleField) generator.generate(1).get(0);

		Integer result = Integer.parseInt(rF.getValue());
		assertThat(result, lessThan(Integer.valueOf(24)));
	}

	@Test
	public void testIntegerQueryRestrictionLessOperatorWithMinValue()
			throws NotExistingGeneratorException, ExprLangParsingException {
		Field field = new Field();
		field.setType("Integer");
		field.setQuotes(false);
		field.setName("field8");
		field.setMin(-20.0);
		field.setMax(9.0);

		List<Trio<String, String, String>> restrictions = new ArrayList<>();
		restrictions.add(new Trio<>("field8", "<", "24"));

		Generable generator = GeneratorsFactory.makeQueryRestrictionGenerator(field, restrictions);
		ResultSimpleField rF = (ResultSimpleField) generator.generate(1).get(0);

		Integer result = Integer.parseInt(rF.getValue());
		assertThat(result, lessThan(Integer.valueOf(24)));
		assertThat(result, greaterThanOrEqualTo(Integer.valueOf(-20)));
	}

	@Test
	public void testIntegerQueryRestrictionLessOperatorWithNegativeValue()
			throws NotExistingGeneratorException, ExprLangParsingException {
		Field field = new Field();
		field.setType("Integer");
		field.setQuotes(false);
		field.setName("field1");
		field.setMin(-30.0);
		field.setMax(9.0);

		List<Trio<String, String, String>> restrictions = new ArrayList<>();
		restrictions.add(new Trio<>("field1", "<", "-24"));

		Generable generator = GeneratorsFactory.makeQueryRestrictionGenerator(field, restrictions);
		ResultSimpleField rF = (ResultSimpleField) generator.generate(1).get(0);

		Integer result = Integer.parseInt(rF.getValue());

		assertThat(result, lessThan(Integer.valueOf(-24)));
		assertThat(result, greaterThanOrEqualTo(Integer.valueOf(-30)));
	}

	@Test
	public void testIntegerQueryRestrictionGreaterOperator()
			throws NotExistingGeneratorException, ExprLangParsingException {
		Field field = new Field();
		field.setType("Integer");
		field.setQuotes(false);
		field.setName("field1");
		field.setMin(0.0);
		field.setMax(30.0);

		List<Trio<String, String, String>> restrictions = new ArrayList<>();
		restrictions.add(new Trio<>("field1", ">", "24"));

		Generable generator = GeneratorsFactory.makeQueryRestrictionGenerator(field, restrictions);
		ResultSimpleField rF = (ResultSimpleField) generator.generate(1).get(0);

		Integer result = Integer.parseInt(rF.getValue());
		assertThat(result, greaterThan(24));
		assertThat(result, lessThanOrEqualTo(30));
	}

	@Test
	public void testIntegerQueryRestrictionGreaterOperatorWithMaxValue()
			throws NotExistingGeneratorException, ExprLangParsingException {
		Field field = new Field();
		field.setType("Integer");
		field.setQuotes(false);
		field.setName("field7");
		field.setMax(200.0);
		field.setMin(0.0);

		List<Trio<String, String, String>> restrictions = new ArrayList<>();
		restrictions.add(new Trio<>("field7", ">", "24"));

		Generable generator = GeneratorsFactory.makeQueryRestrictionGenerator(field, restrictions);
		ResultSimpleField rF = (ResultSimpleField) generator.generate(1).get(0);

		Integer result = Integer.parseInt(rF.getValue());
		assertThat(result, greaterThan(24));
		assertThat(result, lessThanOrEqualTo(200));
	}

	@Test
	public void testIntegerQueryRestrictionEqualOperator()
			throws NotExistingGeneratorException, ExprLangParsingException {
		Field field = new Field();
		field.setType("Integer");
		field.setQuotes(false);
		field.setName("field1");
		field.setMin(0.0);
		field.setMax(9.0);

		List<Trio<String, String, String>> restrictions = new ArrayList<>();
		restrictions.add(new Trio<>("field1", "=", "24"));

		Generable generator = GeneratorsFactory.makeQueryRestrictionGenerator(field, restrictions);
		ResultSimpleField rF = (ResultSimpleField) generator.generate(1).get(0);

		Integer result = Integer.parseInt(rF.getValue());
		assertThat(result, equalTo(24));
	}

	@Test
	public void testIntegerQueryRestrictionNotEqualOperator()
			throws NotExistingGeneratorException, ExprLangParsingException {
		Field field = new Field();
		field.setType("Integer");
		field.setQuotes(false);
		field.setName("field1");
		field.setMin(0.0);
		field.setMax(9.0);

		List<Trio<String, String, String>> restrictions = new ArrayList<>();
		restrictions.add(new Trio<>("field1", "!=", "24"));

		Generable generator = GeneratorsFactory.makeQueryRestrictionGenerator(field, restrictions);
		ResultSimpleField rF = (ResultSimpleField) generator.generate(1).get(0);

		Integer result = Integer.parseInt(rF.getValue());
		assertThat(result, not(equalTo(24)));
	}

	@Test
	public void testIntegerQueryRestrictionNotEqualOperatorWithMinAndMax()
			throws NotExistingGeneratorException, ExprLangParsingException {
		Field field = new Field();
		field.setType("Integer");
		field.setQuotes(false);
		field.setName("field9");
		field.setMax(100.0);
		field.setMin(-100.0);

		List<Trio<String, String, String>> restrictions = new ArrayList<>();
		restrictions.add(new Trio<>("field9", "!=", "20"));

		Generable generator = GeneratorsFactory.makeQueryRestrictionGenerator(field, restrictions);
		ResultSimpleField rF = (ResultSimpleField) generator.generate(1).get(0);

		Integer result = Integer.parseInt(rF.getValue());

		assertThat(result, not(Integer.valueOf(20)));
		assertThat(result, greaterThanOrEqualTo(Integer.valueOf(-100)));
		assertThat(result, lessThanOrEqualTo(Integer.valueOf(100)));

	}

	@Test
	public void testIntegerQueryRestrictionGreaterEqualOperator()
			throws NotExistingGeneratorException, ExprLangParsingException {
		Field field = new Field();
		field.setType("Integer");
		field.setQuotes(false);
		field.setName("field1");
		field.setMin(0.0);
		field.setMax(9.0);

		List<Trio<String, String, String>> restrictions = new ArrayList<>();
		restrictions.add(new Trio<>("field1", ">=", "3"));

		Generable generator = GeneratorsFactory.makeQueryRestrictionGenerator(field, restrictions);
		ResultSimpleField rF = (ResultSimpleField) generator.generate(1).get(0);

		Integer result = Integer.parseInt(rF.getValue());
		assertThat(result, greaterThanOrEqualTo(3));
		assertThat(result, lessThanOrEqualTo(9));

	}

	@Test
	public void testIntegerQueryRestrictionGreaterEqualOperatorWithMaxAttribute()
			throws NotExistingGeneratorException, ExprLangParsingException {
		Field field = new Field();
		field.setType("Integer");
		field.setQuotes(false);
		field.setName("field7");
		field.setMax(200.0);

		List<Trio<String, String, String>> restrictions = new ArrayList<>();
		restrictions.add(new Trio<>("field7", ">=", "24"));

		Generable generator = GeneratorsFactory.makeQueryRestrictionGenerator(field, restrictions);
		ResultSimpleField rF = (ResultSimpleField) generator.generate(1).get(0);

		Integer result = Integer.parseInt(rF.getValue());
		assertThat(result, greaterThanOrEqualTo(Integer.valueOf(24)));
	}

	@Test
	public void testIntegerQueryRestrictionLessEqualOperator()
			throws NotExistingGeneratorException, ExprLangParsingException {
		Field field = new Field();
		field.setType("Integer");
		field.setQuotes(false);
		field.setName("field1");
		field.setMin(0.0);
		field.setMax(9.0);

		List<Trio<String, String, String>> restrictions = new ArrayList<>();
		restrictions.add(new Trio<>("field1", "<=", "24"));

		Generable generator = GeneratorsFactory.makeQueryRestrictionGenerator(field, restrictions);
		ResultSimpleField rF = (ResultSimpleField) generator.generate(1).get(0);

		Integer result = Integer.parseInt(rF.getValue());
		assertThat(result, lessThanOrEqualTo(Integer.valueOf(24)));
	}

	@Test
	public void testIntegerQueryRestrictionLessEqualOperatorWithMinValue()
			throws NotExistingGeneratorException, ExprLangParsingException {
		Field field = new Field();
		field.setType("Integer");
		field.setQuotes(false);
		field.setName("field1");
		field.setMin(-20.0);
		field.setMax(9.0);
		List<Trio<String, String, String>> restrictions = new ArrayList<>();
		restrictions.add(new Trio<>("field1", "<=", "24"));

		Generable generator = GeneratorsFactory.makeQueryRestrictionGenerator(field, restrictions);
		ResultSimpleField rF = (ResultSimpleField) generator.generate(1).get(0);

		Integer result = Integer.parseInt(rF.getValue());
		assertThat(result, lessThanOrEqualTo(Integer.valueOf(24)));
		assertThat(result, greaterThanOrEqualTo(Integer.valueOf(-20)));

	}

	@Test
	public void testIntegerQueryRestrictionLessEqualOperatorWithNegativeValue()
			throws NotExistingGeneratorException, ExprLangParsingException {
		Field field = new Field();
		field.setType("Integer");
		field.setQuotes(false);
		field.setName("field1");

		List<Trio<String, String, String>> restrictions = new ArrayList<>();
		restrictions.add(new Trio<>("field1", "<=", "-24"));

		Generable generator = GeneratorsFactory.makeQueryRestrictionGenerator(field, restrictions);
		ResultSimpleField rF = (ResultSimpleField) generator.generate(1).get(0);

		Integer result = Integer.parseInt(rF.getValue());
		assertThat(result, lessThanOrEqualTo(Integer.valueOf(-24)));
	}
}
