package com.ioteg.generation;

import static org.hamcrest.MatcherAssert.assertThat;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.ioteg.eplutils.Trio;
import com.ioteg.exprlang.ExprParser.ExprLangParsingException;
import com.ioteg.generation.Generable;
import com.ioteg.generation.GenerationContext;
import com.ioteg.generation.GeneratorsFactory;
import com.ioteg.generation.NotExistingGeneratorException;
import com.ioteg.model.Field;
import com.ioteg.resultmodel.ResultSimpleField;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;

public class DateGeneratorQueryRestrictionTestCase {

	private static Field field;

	@BeforeAll
	public static void initialize() {
		field = new Field("field35", false, "Date");
		field.setFormat("yy-MM-dd");
	}

	@Test
	public void testDateQueryRestrictionEqualOperator() throws NotExistingGeneratorException, ExprLangParsingException, ParseException {
		List<Trio<String, String, String>> restrictions = new ArrayList<>();
		restrictions.add(new Trio<>("field35", "=", "96-05-10"));

		Generable generator = GeneratorsFactory.makeQueryRestrictionGenerator(field, restrictions, new GenerationContext());

		ResultSimpleField rF = (ResultSimpleField) generator.generate(1).get(0);
		assertThat(rF.getValue(), equalTo("96-05-10"));
	}

	@Test
	public void testDateQueryRestrictionNotEqualOperator()
			throws NotExistingGeneratorException, ExprLangParsingException, ParseException {
		List<Trio<String, String, String>> restrictions = new ArrayList<>();
		restrictions.add(new Trio<>("field35", "!=", "96-05-10"));

		Generable generator = GeneratorsFactory.makeQueryRestrictionGenerator(field, restrictions, new GenerationContext());

		ResultSimpleField rF = (ResultSimpleField) generator.generate(1).get(0);
		assertThat(rF.getValue(), not(equalTo("96-05-10")));
	}

}
