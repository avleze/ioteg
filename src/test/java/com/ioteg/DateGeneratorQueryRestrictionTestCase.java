package com.ioteg;

import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.ioteg.exprlang.ExprParser.ExprLangParsingException;
import com.ioteg.generators.Generable;
import com.ioteg.generators.GeneratorsFactory;
import com.ioteg.generators.exceptions.NotExistingGeneratorException;
import com.ioteg.model.Field;
import com.ioteg.resultmodel.ResultSimpleField;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;

public class DateGeneratorQueryRestrictionTestCase {

	private static Field field;

	@BeforeAll
	public static void initialize() {
		field = new Field();
		field.setType("Date");
		field.setName("field35");
		field.setFormat("yy-MM-dd");
	}

	@Test
	public void testDateQueryRestrictionEqualOperator() throws NotExistingGeneratorException, ExprLangParsingException {
		List<Trio<String, String, String>> restrictions = new ArrayList<>();
		restrictions.add(new Trio<>("field35", "=", "96-05-10"));

		Generable generator = GeneratorsFactory.makeQueryRestrictionGenerator(field, restrictions);

		ResultSimpleField rF = (ResultSimpleField) generator.generate(1).get(0);
		assertThat(rF.getValue(), equalTo("96-05-10"));
	}

	@Test
	public void testDateQueryRestrictionNotEqualOperator()
			throws NotExistingGeneratorException, ExprLangParsingException {
		List<Trio<String, String, String>> restrictions = new ArrayList<>();
		restrictions.add(new Trio<>("field35", "!=", "96-05-10"));

		Generable generator = GeneratorsFactory.makeQueryRestrictionGenerator(field, restrictions);

		ResultSimpleField rF = (ResultSimpleField) generator.generate(1).get(0);
		assertThat(rF.getValue(), not(equalTo("96-05-10")));
	}

}
