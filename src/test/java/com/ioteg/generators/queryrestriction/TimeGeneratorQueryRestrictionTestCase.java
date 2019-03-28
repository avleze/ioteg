package com.ioteg.generators.queryrestriction;

import static org.hamcrest.MatcherAssert.assertThat;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.ioteg.eplutils.Trio;
import com.ioteg.generators.Generable;
import com.ioteg.generators.GeneratorsFactory;
import com.ioteg.generators.context.GenerationContext;
import com.ioteg.model.Field;
import com.ioteg.resultmodel.ResultSimpleField;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;

public class TimeGeneratorQueryRestrictionTestCase {

	private static Field field;

	@BeforeAll
	public static void initialize() {
		field = new Field("field36", false, "Time");
		field.setType("Time");
		field.setFormat("HH:mm");
	}

	@Test
	public void testTimeQueryRestrictionEqualOperator() throws Exception {
		List<Trio<String, String, String>> restrictions = new ArrayList<>();
		restrictions.add(new Trio<>("field36", "=", "12:43"));

		Generable generator = GeneratorsFactory.makeQueryRestrictionGenerator(field, restrictions, new GenerationContext());

		ResultSimpleField rF = (ResultSimpleField) generator.generate(1).get(0);

		assertThat(rF.getValue(), equalTo("12:43"));
	}

	@Test
	public void testTimeQueryRestrictionNotEqualOperator() throws Exception {
		List<Trio<String, String, String>> restrictions = new ArrayList<>();
		restrictions.add(new Trio<>("field36", "!=", "12:43"));

		Generable generator = GeneratorsFactory.makeQueryRestrictionGenerator(field, restrictions, new GenerationContext());

		ResultSimpleField rF = (ResultSimpleField) generator.generate(1).get(0);

		assertThat(rF.getValue(), not(equalTo("12:43")));
	}

}
