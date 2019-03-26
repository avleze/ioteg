package com.ioteg.generators.queryrestriction;

import static org.hamcrest.MatcherAssert.assertThat;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

import com.ioteg.eplutils.Trio;
import com.ioteg.generators.Generable;
import com.ioteg.generators.GeneratorsFactory;
import com.ioteg.model.Field;
import com.ioteg.resultmodel.ResultSimpleField;

import static org.hamcrest.Matchers.matchesPattern;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;

public class StringGeneratorQueryRestrictionTestCase {

	private static final int DEFAULT_LENGTH = 10;

	@Test
	public void testStringQueryRestrictionEqualOperator()
			throws Exception {
		Field field = new Field("field23", false);
		field.setType("String");
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
			throws Exception {
		Field field = new Field("field23", false);
		field.setType("String");
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
			throws Exception {
		Field field = new Field("field24", false);
		field.setType("String");
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
			throws Exception {
		Field field = new Field("field25", false);
		field.setType("String");
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
			throws Exception {
		Field field = new Field("field26", false);
		field.setType("String");
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
			throws Exception {
		Field field = new Field("field27", false);
		field.setType("String");
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
			throws Exception {
		Field field = new Field("field25", false);
		field.setType("String");
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
