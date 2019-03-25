package com.ioteg.generators;

import java.util.Date;
import java.util.List;

import com.ioteg.eplutils.Trio;
import com.ioteg.generators.booleanfield.BooleanGenerator;
import com.ioteg.generators.booleanfield.BooleanQueryRestrictionGenerationAlgorithm;
import com.ioteg.generators.datefield.DateGenerator;
import com.ioteg.generators.datefield.DateQueryRestrictionGenerationAlgorithm;
import com.ioteg.generators.exceptions.NotExistingGeneratorException;
import com.ioteg.generators.floatfield.FloatGenerator;
import com.ioteg.generators.floatfield.FloatQueryRestrictionGenerationAlgorithm;
import com.ioteg.generators.longfield.LongQueryRestrictionGenerationAlgorithm;
import com.ioteg.generators.stringfield.StringGenerator;
import com.ioteg.generators.stringfield.StringQueryRestrictionGenerationAlgorithm;
import com.ioteg.model.Field;

public class QueryRestrictionGeneratorsFactory {

	private static final String ALPHABETICAL_VALUES = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private static final String ALPHANUMERIC_VALUES = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";


	private QueryRestrictionGeneratorsFactory() {
		throw new IllegalStateException("This is an utility class and can't be instantiated.");
	}
	
	public static Generable makeGenerator(Field field,
			List<Trio<String, String, String>> restrictions) throws NotExistingGeneratorException {
		Generable generable = null;

		if (field.getType().equals("String"))
			generable = makeQueryRestrictionStringGenerator(field, restrictions);
		else if (field.getType().equals("Alphanumeric"))
			generable = makeQueryRestrictionAlphanumericGenerator(field, restrictions);
		else if (field.getType().equals("Integer") || field.getType().equals("Long"))
			generable = makeQueryRestrictionLongGenerator(field, restrictions);
		else if (field.getType().equals("Float"))
			generable = makeQueryRestrictionFloatGenerator(field, restrictions);
		else if (field.getType().equals("Boolean"))
			generable = makeQueryRestrictionBooleanGenerator(field, restrictions);
		else if (field.getType().equals("Date"))
			generable = makeQueryRestrictionDateGenerator(field, restrictions);
		else if (field.getType().equals("Time"))
			generable = makeQueryRestrictionTimeGenerator(field, restrictions);
		else
			throw new NotExistingGeneratorException("There is no query restriction generator for type: " + field.getType());
	

		return generable;
	}

	public static FieldGenerator<String> makeQueryRestrictionStringGenerator(Field field,
			List<Trio<String, String, String>> restrictions) {
		return new StringGenerator(
				new StringQueryRestrictionGenerationAlgorithm(field, restrictions, ALPHABETICAL_VALUES), field);
	}

	public static FieldGenerator<String> makeQueryRestrictionAlphanumericGenerator(Field field,
			List<Trio<String, String, String>> restrictions) {
		return new StringGenerator(
				new StringQueryRestrictionGenerationAlgorithm(field, restrictions, ALPHANUMERIC_VALUES), field);
	}

	public static FieldGenerator<Long> makeQueryRestrictionLongGenerator(Field field,
			List<Trio<String, String, String>> restrictions) {
		return new FieldGenerator<>(new LongQueryRestrictionGenerationAlgorithm(field, restrictions), field);
	}

	public static FieldGenerator<Float> makeQueryRestrictionFloatGenerator(Field field,
			List<Trio<String, String, String>> restrictions) {
		return new FloatGenerator(new FloatQueryRestrictionGenerationAlgorithm(field, restrictions), field);
	}

	public static FieldGenerator<Boolean> makeQueryRestrictionBooleanGenerator(Field field,
			List<Trio<String, String, String>> restrictions) {
		return new BooleanGenerator(new BooleanQueryRestrictionGenerationAlgorithm(field, restrictions), field);
	}

	public static FieldGenerator<Date> makeQueryRestrictionDateGenerator(Field field,
			List<Trio<String, String, String>> restrictions) {
		return new DateGenerator(new DateQueryRestrictionGenerationAlgorithm(field, restrictions), field);
	}

	public static FieldGenerator<Date> makeQueryRestrictionTimeGenerator(Field field,
			List<Trio<String, String, String>> restrictions) {
		return makeQueryRestrictionDateGenerator(field, restrictions);
	}
}
