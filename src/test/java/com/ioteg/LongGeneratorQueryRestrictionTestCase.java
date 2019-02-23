package com.ioteg;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.Matchers.lessThanOrEqualTo;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.ioteg.exprlang.ExprParser.ExprLangParsingException;
import com.ioteg.generators.Generable;
import com.ioteg.generators.GeneratorsFactory;
import com.ioteg.generators.exceptions.NotExistingGeneratorException;
import com.ioteg.model.Field;
import com.ioteg.resultmodel.ResultSimpleField;


public class LongGeneratorQueryRestrictionTestCase {

	@Test
	public void testLongQueryRestrictionLessOperator() throws NotExistingGeneratorException, ExprLangParsingException  {
		Field field = new Field();
		field.setType("Long");
		field.setQuotes(false);
		field.setName("field19");
		field.setMin(-50.0);
		field.setMax(50.0);

		List<Trio<String, String, String>> restrictions = new ArrayList<>();
		restrictions.add(new Trio<>("field19", "<", "24"));

		Generable generator = GeneratorsFactory.makeQueryRestrictionGenerator(field, restrictions);
		ResultSimpleField rF = (ResultSimpleField) generator.generate(1).get(0);

		Long result = Long.parseLong(rF.getValue());
		assertThat(result, lessThan(Long.valueOf(24L)));
		assertThat(result, greaterThanOrEqualTo(Long.valueOf(-50L)));
	}
	
	@Test
	public void testLongQueryRestrictionLessOperatorWithMinValue() throws NotExistingGeneratorException, ExprLangParsingException  {
		Field field = new Field();
		field.setType("Long");
		field.setQuotes(false);
		field.setName("field21");
		field.setMin(-20.0);

		List<Trio<String, String, String>> restrictions = new ArrayList<>();
		restrictions.add(new Trio<>("field21", "<", "24"));

		Generable generator = GeneratorsFactory.makeQueryRestrictionGenerator(field, restrictions);
		ResultSimpleField rF = (ResultSimpleField) generator.generate(1).get(0);

		Long result = Long.parseLong(rF.getValue());
		assertThat(result, lessThan(Long.valueOf(24)));
		assertThat(result, greaterThanOrEqualTo(Long.valueOf(-20L)));
	}
	
	
	@Test
	public void testLongQueryRestrictionLessOperatorWithNegativeValue() throws NotExistingGeneratorException, ExprLangParsingException {
		Field field = new Field();
		field.setType("Long");
		field.setQuotes(false);
		field.setName("field19");
		field.setMin(-50.0);
		field.setMax(50.0);

		List<Trio<String, String, String>> restrictions = new ArrayList<>();
		restrictions.add(new Trio<>("field19", "<", "-24"));

		Generable generator = GeneratorsFactory.makeQueryRestrictionGenerator(field, restrictions);
		ResultSimpleField rF = (ResultSimpleField) generator.generate(1).get(0);

		Long result = Long.parseLong(rF.getValue());
		assertThat(result, lessThan(Long.valueOf(-24)));
		assertThat(result, greaterThanOrEqualTo(Long.valueOf(-50)));
	}
	
	@Test
	public void testLongQueryRestrictionGreaterOperator() throws NotExistingGeneratorException, ExprLangParsingException {
		Field field = new Field();
		field.setType("Long");
		field.setQuotes(false);
		field.setName("field19");
		field.setMin(-50.0);
		field.setMax(50.0);

		List<Trio<String, String, String>> restrictions = new ArrayList<>();
		restrictions.add(new Trio<>("field19", ">", "24"));

		Generable generator = GeneratorsFactory.makeQueryRestrictionGenerator(field, restrictions);
		ResultSimpleField rF = (ResultSimpleField) generator.generate(1).get(0);

		Long result = Long.parseLong(rF.getValue());
		assertThat(result, greaterThan(24L));
		assertThat(result, lessThanOrEqualTo(50L));
	}
	
	
	@Test
	public void testLongQueryRestrictionGreaterOperatorWithMaxValue() throws NotExistingGeneratorException, ExprLangParsingException  {
		Field field = new Field();
		field.setType("Long");
		field.setQuotes(false);
		field.setName("field20");
		field.setMax(200.0);

		List<Trio<String, String, String>> restrictions = new ArrayList<>();
		restrictions.add(new Trio<>("field20", ">", "24"));

		Generable generator = GeneratorsFactory.makeQueryRestrictionGenerator(field, restrictions);
		ResultSimpleField rF = (ResultSimpleField) generator.generate(1).get(0);

		Long result = Long.parseLong(rF.getValue());
		assertThat(result, greaterThan(24L));
		assertThat(result, lessThanOrEqualTo(200L));
	}
	
	@Test
	public void testLongQueryRestrictionEqualOperator() throws NotExistingGeneratorException, ExprLangParsingException {
		Field field = new Field();
		field.setType("Long");
		field.setQuotes(false);
		field.setName("field19");
		field.setMin(-50.0);
		field.setMax(50.0);

		List<Trio<String, String, String>> restrictions = new ArrayList<>();
		restrictions.add(new Trio<>("field19", "=", "24"));

		Generable generator = GeneratorsFactory.makeQueryRestrictionGenerator(field, restrictions);
		ResultSimpleField rF = (ResultSimpleField) generator.generate(1).get(0);

		Long result = Long.parseLong(rF.getValue());
		assertEquals(result, Long.valueOf(24));
	}
	
	@Test
	public void testLongQueryRestrictionNotEqualOperator() throws NotExistingGeneratorException, ExprLangParsingException {
		Field field = new Field();
		field.setType("Long");
		field.setQuotes(false);
		field.setName("field19");
		field.setMin(-50.0);
		field.setMax(50.0);

		List<Trio<String, String, String>> restrictions = new ArrayList<>();
		restrictions.add(new Trio<>("field19", "!=", "24"));

		Generable generator = GeneratorsFactory.makeQueryRestrictionGenerator(field, restrictions);
		ResultSimpleField rF = (ResultSimpleField) generator.generate(1).get(0);

		Long result = Long.parseLong(rF.getValue());
		assertThat(result, not(24L));
		assertThat(result, greaterThanOrEqualTo(-50L));
		assertThat(result, lessThanOrEqualTo(50L));

	}
	
	@Test
	public void testLongQueryRestrictionNotEqualOperatorWithMinAndMax() throws NotExistingGeneratorException, ExprLangParsingException  {
		Field field = new Field();
		field.setType("Long");
		field.setQuotes(false);
		field.setName("field22");
		field.setMin(-100.0);
		field.setMax(100.0);

		List<Trio<String, String, String>> restrictions = new ArrayList<>();
		restrictions.add(new Trio<>("field22", "!=", "20"));

		Generable generator = GeneratorsFactory.makeQueryRestrictionGenerator(field, restrictions);
		ResultSimpleField rF = (ResultSimpleField) generator.generate(1).get(0);

		Long result = Long.parseLong(rF.getValue());
		assertThat(result, not(20L));
		assertThat(result, greaterThanOrEqualTo(-100L));
		assertThat(result, lessThanOrEqualTo(100L));
	}

	
	@Test
	public void testLongQueryRestrictionGreaterEqualOperator() throws NotExistingGeneratorException, ExprLangParsingException  {
		Field field = new Field();
		field.setType("Long");
		field.setQuotes(false);
		field.setName("field19");
		field.setMin(-50.0);
		field.setMax(50.0);

		List<Trio<String, String, String>> restrictions = new ArrayList<>();
		restrictions.add(new Trio<>("field19", ">=", "24"));

		Generable generator = GeneratorsFactory.makeQueryRestrictionGenerator(field, restrictions);
		ResultSimpleField rF = (ResultSimpleField) generator.generate(1).get(0);

		Long result = Long.parseLong(rF.getValue());
		assertThat(result, greaterThanOrEqualTo(Long.valueOf(24)));
		assertThat(result, lessThanOrEqualTo(Long.valueOf(50)));
	}
	
	@Test
	public void testLongQueryRestrictionGreaterEqualOperatorWithMaxAttribute() throws NotExistingGeneratorException, ExprLangParsingException  {
		Field field = new Field();
		field.setType("Long");
		field.setQuotes(false);
		field.setName("field20");
		field.setMax(200.0);

		List<Trio<String, String, String>> restrictions = new ArrayList<>();
		restrictions.add(new Trio<>("field20", ">=", "24"));

		Generable generator = GeneratorsFactory.makeQueryRestrictionGenerator(field, restrictions);
		ResultSimpleField rF = (ResultSimpleField) generator.generate(1).get(0);

		Long result = Long.parseLong(rF.getValue());
		assertThat(result, greaterThanOrEqualTo(24L));
		assertThat(result, lessThanOrEqualTo(200L));
	}
	
	@Test
	public void testLongQueryRestrictionLessEqualOperator() throws NotExistingGeneratorException, ExprLangParsingException  {
		Field field = new Field();
		field.setType("Long");
		field.setQuotes(false);
		field.setName("field19");
		field.setMin(-50.0);
		field.setMax(50.0);

		List<Trio<String, String, String>> restrictions = new ArrayList<>();
		restrictions.add(new Trio<>("field19", "<=", "24"));

		Generable generator = GeneratorsFactory.makeQueryRestrictionGenerator(field, restrictions);
		ResultSimpleField rF = (ResultSimpleField) generator.generate(1).get(0);

		Long result = Long.parseLong(rF.getValue());
		assertThat(result, lessThanOrEqualTo(24L));
		assertThat(result, greaterThanOrEqualTo(-50L));
	}
	
	@Test
	public void testLongQueryRestrictionLessEqualOperatorWithMinValue() throws NotExistingGeneratorException, ExprLangParsingException {
		Field field = new Field();
		field.setType("Long");
		field.setQuotes(false);
		field.setName("field21");
		field.setMin(-20.0);

		List<Trio<String, String, String>> restrictions = new ArrayList<>();
		restrictions.add(new Trio<>("field21", "<=", "24"));

		Generable generator = GeneratorsFactory.makeQueryRestrictionGenerator(field, restrictions);
		ResultSimpleField rF = (ResultSimpleField) generator.generate(1).get(0);

		Long result = Long.parseLong(rF.getValue());

		assertThat(result, lessThanOrEqualTo(24L));
		assertThat(result, greaterThanOrEqualTo(-20L));

	}
	
	@Test
	public void testLongQueryRestrictionLessEqualOperatorWithNegativeValue() throws NotExistingGeneratorException, ExprLangParsingException  {
		Field field = new Field();
		field.setType("Long");
		field.setQuotes(false);
		field.setName("field19");
		field.setMin(-50.0);
		field.setMax(50.0);

		List<Trio<String, String, String>> restrictions = new ArrayList<>();
		restrictions.add(new Trio<>("field19", "<=", "-24"));

		Generable generator = GeneratorsFactory.makeQueryRestrictionGenerator(field, restrictions);
		ResultSimpleField rF = (ResultSimpleField) generator.generate(1).get(0);

		Long result = Long.parseLong(rF.getValue());

		assertThat(result, lessThanOrEqualTo(Long.valueOf(-24)));
	}
}
