package com.ioteg.generation;

import static org.hamcrest.MatcherAssert.assertThat;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
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

public class BooleanGeneratorQueryRestrictionTestCase {

	@Test
	public void testBooleanQueryRestrictionEqualOperator()
			throws Exception {

		Field field = new Field("field10", false, "Boolean");
		field.setIsNumeric(false);

		List<Trio<String, String, String>> restrictions = new ArrayList<>();
		restrictions.add(new Trio<>("field10", "=", "true"));

		Generable generator = GeneratorsFactory.makeQueryRestrictionGenerator(field, restrictions, new GenerationContext());

		ResultSimpleField rF = (ResultSimpleField) generator.generate(1).get(0);

		assertThat(rF.getValue(), equalTo("true"));
	}

	@Test
	public void testBooleanQueryRestrictionNotEqualOperator()
			throws NotExistingGeneratorException, ExprLangParsingException, ParseException {

		Field field = new Field("field10", false, "Boolean");
		field.setIsNumeric(false);

		List<Trio<String, String, String>> restrictions = new ArrayList<>();
		restrictions.add(new Trio<>("field10", "!=", "true"));
		Generable generator = GeneratorsFactory.makeQueryRestrictionGenerator(field, restrictions, new GenerationContext());
		ResultSimpleField rF = (ResultSimpleField) generator.generate(1).get(0);
		assertThat(rF.getValue(), not(equalTo("true")));

		field.setName("field11");
		restrictions.clear();
		restrictions.add(new Trio<>("field11", "!=", "false"));
		generator = GeneratorsFactory.makeQueryRestrictionGenerator(field, restrictions, new GenerationContext());
		rF = (ResultSimpleField) generator.generate(1).get(0);
		assertThat(rF.getValue(), not(equalTo("false")));

		field.setName("field12");
		field.setIsNumeric(true);
		restrictions.clear();
		restrictions.add(new Trio<>("field12", "!=", "1"));
		generator = GeneratorsFactory.makeQueryRestrictionGenerator(field, restrictions, new GenerationContext());
		rF = (ResultSimpleField) generator.generate(1).get(0);
		assertThat(rF.getValue(), not(equalTo("1")));

		field.setName("field13");
		field.setIsNumeric(true);
		restrictions.clear();
		restrictions.add(new Trio<>("field13", "!=", "0"));
		generator = GeneratorsFactory.makeQueryRestrictionGenerator(field, restrictions, new GenerationContext());
		rF = (ResultSimpleField) generator.generate(1).get(0);
		assertThat(rF.getValue(), not(equalTo("0")));
	}

}
