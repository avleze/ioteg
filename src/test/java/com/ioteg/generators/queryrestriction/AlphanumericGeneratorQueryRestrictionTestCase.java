package com.ioteg.generators.queryrestriction;



import java.text.ParseException;
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

import static org.hamcrest.Matchers.matchesPattern;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.MatcherAssert.assertThat;

public class AlphanumericGeneratorQueryRestrictionTestCase {

	private static final int DEFAULT_LENGTH = 10;

	@Test
	public void testAlphanumericQueryRestrictionEqualOperator()
			throws NotExistingGeneratorException, ExprLangParsingException, ParseException {
		Field field = new Field("field29", false, "Alphanumeric");
		field.setLength(DEFAULT_LENGTH);

		List<Trio<String, String, String>> restrictions = new ArrayList<>();
		restrictions.add(new Trio<>("field29", "=", "HOLA ESTO ES UNA PRUEBA"));

		Generable generator = GeneratorsFactory.makeQueryRestrictionGenerator(field, restrictions);

		ResultSimpleField rF = (ResultSimpleField) generator.generate(1).get(0);
		assertThat(rF.getValue(), equalTo("HOLA ESTO ES UNA PRUEBA"));
		assertThat(rF.getValue().length(), equalTo(23));
	}

	@Test
	public void testAlphanumericQueryRestrictionNotEqualOperator()
			throws NotExistingGeneratorException, ExprLangParsingException, ParseException {
		Field field = new Field("field29", false, "Alphanumeric");
		field.setLength(DEFAULT_LENGTH);

		List<Trio<String, String, String>> restrictions = new ArrayList<>();
		restrictions.add(new Trio<>("field29", "!=", "HOLA ESTO ES UNA PRUEBA"));

		Generable generator = GeneratorsFactory.makeQueryRestrictionGenerator(field, restrictions);

		ResultSimpleField rF = (ResultSimpleField) generator.generate(1).get(0);

		assertThat(rF.getValue(), not(equalTo("HOLA ESTO ES UNA PRUEBA")));
		assertThat(rF.getValue(), matchesPattern("[0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ]*"));
		assertThat(rF.getValue().length(), equalTo(10));
	}

	@Test
	public void testAlphanumericQueryRestrictionNotEqualOperatorWithEndCharacter()
			throws NotExistingGeneratorException, ExprLangParsingException, ParseException {
		Field field = new Field("field30", false, "Alphanumeric");
		field.setEndcharacter("F");
		field.setLength(DEFAULT_LENGTH);

		List<Trio<String, String, String>> restrictions = new ArrayList<>();
		restrictions.add(new Trio<>("field30", "!=", "HOLA ESTO ES UNA PRUEBA"));
		Generable generator = GeneratorsFactory.makeQueryRestrictionGenerator(field, restrictions);

		ResultSimpleField rF = (ResultSimpleField) generator.generate(1).get(0);
		assertThat(rF.getValue(), not("HOLA ESTO ES UNA PRUEBA"));
		assertThat(rF.getValue(), matchesPattern("[0123456789ABCDEF]*"));
		assertThat(rF.getValue().length(), equalTo(10));
	}

	@Test
	public void testAlphanumericQueryRestrictionNotEqualOperatorWithLength()
			throws NotExistingGeneratorException, ExprLangParsingException, ParseException {
		Field field = new Field("field31", false, "Alphanumeric");
		field.setLength(12);
		List<Trio<String, String, String>> restrictions = new ArrayList<>();
		restrictions.add(new Trio<>("field31", "!=", "HOLA ESTO ES UNA PRUEBA"));
		Generable generator = GeneratorsFactory.makeQueryRestrictionGenerator(field, restrictions);
		ResultSimpleField rF = (ResultSimpleField) generator.generate(1).get(0);

		assertThat(rF.getValue(), not("HOLA ESTO ES UNA PRUEBA"));
		assertThat(rF.getValue(), matchesPattern("[0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ]*"));
	}

	@Test
	public void testAlphanumericQueryRestrictionNotEqualOperatorWithLengthAndCase()
			throws NotExistingGeneratorException, ExprLangParsingException, ParseException {
		Field field = new Field("field33", false, "Alphanumeric");
		field.setLength(12);
		field.setCase("low");
		List<Trio<String, String, String>> restrictions = new ArrayList<>();
		restrictions.add(new Trio<>("field33", "!=", "HOLA ESTO ES UNA PRUEBA"));
		Generable generator = GeneratorsFactory.makeQueryRestrictionGenerator(field, restrictions);
		ResultSimpleField rF = (ResultSimpleField) generator.generate(1).get(0);
		assertThat(rF.getValue(), not("HOLA ESTO ES UNA PRUEBA"));
		assertThat(rF.getValue().length(), equalTo(12));
		assertThat(rF.getValue(), matchesPattern("[0123456789abcdefghijklmnopqrstuvwxyz]*"));
	}

	@Test
	public void testAlphanumericQueryRestrictionNotEqualOperatorWithEndCharacterAndCase()
			throws NotExistingGeneratorException, ExprLangParsingException, ParseException {
		Field field = new Field("field34", false, "Alphanumeric");
		field.setCase("low");
		field.setEndcharacter("F");
		field.setLength(DEFAULT_LENGTH);
		List<Trio<String, String, String>> restrictions = new ArrayList<>();
		restrictions.add(new Trio<>("field34", "!=", "HOLA ESTO ES UNA PRUEBA"));
		Generable generator = GeneratorsFactory.makeQueryRestrictionGenerator(field, restrictions);
		ResultSimpleField rF = (ResultSimpleField) generator.generate(1).get(0);
		assertThat(rF.getValue(), not("HOLA ESTO ES UNA PRUEBA"));
		assertThat(rF.getValue(), matchesPattern("[0123456789abcdefghijklmnopqrstuvwxyz]*"));
	}

	@Test
	public void testAlphanumericQueryRestrictionNotEqualOperatorWithEndCharacterAndLength()
			throws NotExistingGeneratorException, ExprLangParsingException, ParseException {
		Field field = new Field("field31", false, "Alphanumeric");
		field.setEndcharacter("F");
		field.setLength(12);

		List<Trio<String, String, String>> restrictions = new ArrayList<>();
		restrictions.add(new Trio<>("field31", "!=", "HOLA ESTO ES UNA PRUEBA"));
		Generable generator = GeneratorsFactory.makeQueryRestrictionGenerator(field, restrictions);
		ResultSimpleField rF = (ResultSimpleField) generator.generate(1).get(0);
		assertThat(rF.getValue(), not("HOLA ESTO ES UNA PRUEBA"));
		assertThat(rF.getValue(), matchesPattern("[0123456789ABCDEF]*"));
		assertThat(rF.getValue().length(), equalTo(12));
	}

}
