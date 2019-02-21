package com.ioteg;

import static org.junit.Assert.assertThat;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

import com.ioteg.exprlang.ExprParser.ExprLangParsingException;
import com.ioteg.generators.Generable;
import com.ioteg.generators.GeneratorsFactory;
import com.ioteg.generators.exceptions.NotExistingGeneratorException;
import com.ioteg.model.Field;
import com.ioteg.resultmodel.ResultSimpleField;

import static org.hamcrest.Matchers.matchesPattern;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;

public class StringGeneratorQueryRestrictionTestCase {

	private static final int DEFAULT_LENGTH = 10;

	@Test
	public void testStringQueryRestrictionEqualOperator()
			throws NotExistingGeneratorException, ExprLangParsingException {
		Field field = new Field();
		field.setType("String");
		field.setQuotes(false);
		field.setName("field23");
		field.setLength(DEFAULT_LENGTH);

		List<Trio<String, String, String>> restrictions = new ArrayList<>();
		restrictions.add(new Trio<>("field23", "=", "HOLA ESTO ES UNA PRUEBA"));

		Generable generator = GeneratorsFactory.makeQueryRestrictionGenerator(field, restrictions);

		ResultSimpleField rF = (ResultSimpleField) generator.generate(1).get(0);

		assertThat(rF.getValue(), equalTo("HOLA ESTO ES UNA PRUEBA"));
		assertThat(rF.getValue().length(), equalTo(23));
	}

	@Test
	public void testStringQueryRestrictionNotEqualOperator()
			throws NotExistingGeneratorException, ExprLangParsingException {
		Field field = new Field();
		field.setType("String");
		field.setQuotes(false);
		field.setName("field23");
		field.setLength(DEFAULT_LENGTH);

		List<Trio<String, String, String>> restrictions = new ArrayList<>();
		restrictions.add(new Trio<>("field23", "!=", "HOLA ESTO ES UNA PRUEBA"));

		Generable generator = GeneratorsFactory.makeQueryRestrictionGenerator(field, restrictions);

		ResultSimpleField rF = (ResultSimpleField) generator.generate(1).get(0);

		assertThat(rF.getValue(), not(equalTo("HOLA ESTO ES UNA PRUEBA")));
		assertThat(rF.getValue().length(), equalTo(10));
	}

	@Test
	public void testStringQueryRestrictionNotEqualOperatorWithEndCharacter()
			throws NotExistingGeneratorException, ExprLangParsingException {
		Field field = new Field();
		field.setType("String");
		field.setQuotes(false);
		field.setName("field24");
		field.setEndcharacter("F");
		field.setLength(DEFAULT_LENGTH);

		List<Trio<String, String, String>> restrictions = new ArrayList<>();
		restrictions.add(new Trio<>("field24", "!=", "HOLA ESTO ES UNA PRUEBA"));
		Generable generator = GeneratorsFactory.makeQueryRestrictionGenerator(field, restrictions);

		ResultSimpleField rF = (ResultSimpleField) generator.generate(1).get(0);
		assertThat(rF.getValue(), not("HOLA ESTO ES UNA PRUEBA"));
		assertThat(rF.getValue(), matchesPattern("[ABCDEF]*"));
		assertThat(rF.getValue().length(), equalTo(10));
	}

	@Test
	public void testStringQueryRestrictionNotEqualOperatorWithLength()
			throws NotExistingGeneratorException, ExprLangParsingException {
		Field field = new Field();
		field.setType("String");
		field.setQuotes(false);
		field.setName("field25");
		field.setLength(12);
		List<Trio<String, String, String>> restrictions = new ArrayList<>();
		restrictions.add(new Trio<>("field25", "!=", "HOLA ESTO ES UNA PRUEBA"));
		Generable generator = GeneratorsFactory.makeQueryRestrictionGenerator(field, restrictions);
		ResultSimpleField rF = (ResultSimpleField) generator.generate(1).get(0);

		assertThat(rF.getValue(), not("HOLA ESTO ES UNA PRUEBA"));
		assertThat(rF.getValue().length(), equalTo(12));
	}

	@Test
	public void testStringQueryRestrictionNotEqualOperatorWithLengthAndCase()
			throws NotExistingGeneratorException, ExprLangParsingException {
		Field field = new Field();
		field.setType("String");
		field.setQuotes(false);
		field.setName("field26");
		field.setLength(12);
		field.setCase("low");
		List<Trio<String, String, String>> restrictions = new ArrayList<>();
		restrictions.add(new Trio<>("field26", "!=", "HOLA ESTO ES UNA PRUEBA"));
		Generable generator = GeneratorsFactory.makeQueryRestrictionGenerator(field, restrictions);
		ResultSimpleField rF = (ResultSimpleField) generator.generate(1).get(0);
		assertThat(rF.getValue(), not("HOLA ESTO ES UNA PRUEBA"));
		assertThat(rF.getValue().length(), equalTo(12));
		assertThat(rF.getValue(), equalTo(rF.getValue().toLowerCase()));
	}

	@Test
	public void testStringQueryRestrictionNotEqualOperatorWithEndCharacterAndCase()
			throws NotExistingGeneratorException, ExprLangParsingException {
		Field field = new Field();
		field.setType("String");
		field.setQuotes(false);
		field.setName("field27");
		field.setCase("low");
		field.setEndcharacter("F");
		field.setLength(DEFAULT_LENGTH);
		List<Trio<String, String, String>> restrictions = new ArrayList<>();
		restrictions.add(new Trio<>("field27", "!=", "HOLA ESTO ES UNA PRUEBA"));
		Generable generator = GeneratorsFactory.makeQueryRestrictionGenerator(field, restrictions);
		ResultSimpleField rF = (ResultSimpleField) generator.generate(1).get(0);

		assertThat(rF.getValue(), not("HOLA ESTO ES UNA PRUEBA"));
		assertThat(rF.getValue(), equalTo(rF.getValue().toLowerCase()));
		assertThat(rF.getValue(), matchesPattern("[abcdef]*"));
	}

	@Test
	public void testStringQueryRestrictionNotEqualOperatorWithEndCharacterAndLength()
			throws NotExistingGeneratorException, ExprLangParsingException {
		Field field = new Field();
		field.setType("String");
		field.setQuotes(false);
		field.setName("field25");
		field.setEndcharacter("F");
		field.setLength(12);

		List<Trio<String, String, String>> restrictions = new ArrayList<>();
		restrictions.add(new Trio<>("field25", "!=", "HOLA ESTO ES UNA PRUEBA"));
		Generable generator = GeneratorsFactory.makeQueryRestrictionGenerator(field, restrictions);
		ResultSimpleField rF = (ResultSimpleField) generator.generate(1).get(0);

		assertThat(rF.getValue(), not("HOLA ESTO ES UNA PRUEBA"));
		assertThat(rF.getValue(), matchesPattern("[ABCDEF]*"));
		assertThat(rF.getValue().length(), equalTo(12));
	}

}
