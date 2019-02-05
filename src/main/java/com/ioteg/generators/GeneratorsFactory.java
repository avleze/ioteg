package com.ioteg.generators;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.ioteg.Trio;
import com.ioteg.generators.booleanfield.BooleanGenerator;
import com.ioteg.generators.booleanfield.BooleanQueryRestrictionGenerationAlgorithm;
import com.ioteg.generators.booleanfield.FixedBooleanGenerationAlgorithm;
import com.ioteg.generators.booleanfield.RandomBooleanGenerationAlgorithm;
import com.ioteg.generators.datefield.DateGenerator;
import com.ioteg.generators.datefield.DateQueryRestrictionGenerationAlgorithm;
import com.ioteg.generators.datefield.FixedDateGenerationAlgorithm;
import com.ioteg.generators.datefield.RandomDateGenerationAlgorithm;
import com.ioteg.generators.floatfield.CustomiseBehaviourGenerationAlgorithm;
import com.ioteg.generators.floatfield.FixedFloatGenerationAlgorithm;
import com.ioteg.generators.floatfield.FloatGenerator;
import com.ioteg.generators.floatfield.FloatQueryRestrictionGenerationAlgorithm;
import com.ioteg.generators.floatfield.RandomFloatGenerationAlgorithm;
import com.ioteg.generators.longfield.FixedLongGenerationAlgorithm;
import com.ioteg.generators.longfield.LongQueryRestrictionGenerationAlgorithm;
import com.ioteg.generators.longfield.RandomLongGenerationAlgorithm;
import com.ioteg.generators.stringfield.FixedStringGenerationAlgorithm;
import com.ioteg.generators.stringfield.RandomStringGenerationAlgorithm;
import com.ioteg.generators.stringfield.StringGenerator;
import com.ioteg.generators.stringfield.StringQueryRestrictionGenerationAlgorithm;
import com.ioteg.model.Field;

public class GeneratorsFactory {

	private static final String ALPHABETICAL_VALUES = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private static final String ALPHANUMERIC_VALUES = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	protected static Logger logger;

	static {
		logger = Logger.getRootLogger();
	}
	
	private GeneratorsFactory() {
		throw new IllegalStateException("This is an utility class and can't be instantiated.");
	}

	private static CustomiseBehaviourGenerationAlgorithm customiseBehaviourGenerationAlgorithm;

	public static Generable makeGenerator(Field field, Integer totalNumberOfEvents) {
		Generable generable = null;

		if (field.getType().equals("Integer") || field.getType().equals("Long"))
			generable = makeLongGenerator(field);
		else if (field.getType().equals("String"))
			generable = makeStringGenerator(field);
		else if (field.getType().equals("Alphanumeric"))
			generable = makeAlphanumericGenerator(field);
		else if (field.getType().equals("Float"))
			generable = makeFloatGenerator(field, totalNumberOfEvents);
		else if (field.getType().equals("Boolean"))
			generable = makeBooleanGenerator(field);
		else if (field.getType().equals("Date"))
			generable = makeDateGenerator(field);
		else if (field.getType().equals("Time"))
			generable = makeDateGenerator(field);

		return generable;
	}

	public static Generator<Long> makeLongGenerator(Field longField) {
		Generator<Long> longGenerator = null;

		if (longField.getValue() != null)
			longGenerator = new Generator<>(new FixedLongGenerationAlgorithm(longField));
		else if (longField.getMin() != null && longField.getMax() != null)
			longGenerator = new Generator<>(new RandomLongGenerationAlgorithm(longField));

		return longGenerator;
	}

	public static Generator<Float> makeFloatGenerator(Field floatField, Integer totalNumOfEvents) {
		Generator<Float> floatGenerator = null;

		if (floatField.getValue() != null)
			floatGenerator = new FloatGenerator(new FixedFloatGenerationAlgorithm(floatField));
		else if (floatField.getCustomBehaviour() != null) {
			
			try {
				if(isNeededANewCustomiseBehaviourAlgorithm())
					customiseBehaviourGenerationAlgorithm = new CustomiseBehaviourGenerationAlgorithm(floatField,
						totalNumOfEvents);
			} catch (IOException e) {
				logger.error(e);
			}
			
			floatGenerator = new FloatGenerator(customiseBehaviourGenerationAlgorithm);
		}
		else if (floatField.getMin() != null && floatField.getMax() != null)
			floatGenerator = new FloatGenerator(new RandomFloatGenerationAlgorithm(floatField));
		

		return floatGenerator;
	}

	private static boolean isNeededANewCustomiseBehaviourAlgorithm()
	{
		return customiseBehaviourGenerationAlgorithm == null || customiseBehaviourGenerationAlgorithm.getRules().isEmpty();
	}

	public static Generator<Boolean> makeBooleanGenerator(Field booleanField) {
		Generator<Boolean> booleanGenerator = null;

		if (booleanField.getValue() != null)
			booleanGenerator = new BooleanGenerator(new FixedBooleanGenerationAlgorithm(booleanField));
		else
			booleanGenerator = new BooleanGenerator(new RandomBooleanGenerationAlgorithm(booleanField));

		return booleanGenerator;
	}

	public static Generator<Date> makeDateGenerator(Field dateField) {
		Generator<Date> dateGenerator = null;

		if (dateField.getValue() != null)
			dateGenerator = new DateGenerator(new FixedDateGenerationAlgorithm(dateField));
		else
			dateGenerator = new DateGenerator(new RandomDateGenerationAlgorithm(dateField));

		return dateGenerator;
	}

	public static Generator<Date> makeTimeGenerator(Field timeField) {
		return makeDateGenerator(timeField);
	}

	public static Generator<String> makeStringGenerator(Field stringField) {
		Generator<String> stringGenerator = null;

		if (stringField.getValue() != null)
			stringGenerator = new StringGenerator(new FixedStringGenerationAlgorithm(stringField));
		else
			stringGenerator = new StringGenerator(new RandomStringGenerationAlgorithm(stringField, ALPHABETICAL_VALUES));

		return stringGenerator;
	}

	public static Generator<String> makeAlphanumericGenerator(Field alphanumericField) {
		Generator<String> alphanumericGenerator = null;

		if (alphanumericField.getValue() != null)
			alphanumericGenerator = new StringGenerator(new FixedStringGenerationAlgorithm(alphanumericField));
		else
			alphanumericGenerator = new StringGenerator(new RandomStringGenerationAlgorithm(alphanumericField, ALPHANUMERIC_VALUES));

		return alphanumericGenerator;
	}

	public static Generable makeQueryRestrictionGenerator(Field field,
			List<Trio<String, String, String>> restrictions) {
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

		return generable;
	}


	public static Generator<String> makeQueryRestrictionStringGenerator(Field field,
			List<Trio<String, String, String>> restrictions) {
		return new StringGenerator(new StringQueryRestrictionGenerationAlgorithm(field, restrictions, ALPHABETICAL_VALUES));
	}

	public static Generator<String> makeQueryRestrictionAlphanumericGenerator(Field field,
			List<Trio<String, String, String>> restrictions) {
		return new StringGenerator(new StringQueryRestrictionGenerationAlgorithm(field, restrictions, ALPHANUMERIC_VALUES));
	}

	public static Generator<Long> makeQueryRestrictionLongGenerator(Field field,
			List<Trio<String, String, String>> restrictions) {
		return new Generator<>(new LongQueryRestrictionGenerationAlgorithm(field, restrictions));
	}

	public static Generator<Float> makeQueryRestrictionFloatGenerator(Field field,
			List<Trio<String, String, String>> restrictions) {
		return new FloatGenerator(new FloatQueryRestrictionGenerationAlgorithm(field, restrictions));
	}

	public static Generator<Boolean> makeQueryRestrictionBooleanGenerator(Field field,
			List<Trio<String, String, String>> restrictions) {
		return new BooleanGenerator(new BooleanQueryRestrictionGenerationAlgorithm(field, restrictions));
	}

	public static Generator<Date> makeQueryRestrictionDateGenerator(Field field,
			List<Trio<String, String, String>> restrictions) {
		return new DateGenerator(new DateQueryRestrictionGenerationAlgorithm(field, restrictions));
	}

	public static Generator<Date> makeQueryRestrictionTimeGenerator(Field field,
			List<Trio<String, String, String>> restrictions) {
		return makeQueryRestrictionDateGenerator(field, restrictions);
	}
}
