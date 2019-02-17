package com.ioteg.generators;

import java.io.IOException;
import java.util.Date;
import com.ioteg.generators.booleanfield.BooleanGenerator;
import com.ioteg.generators.booleanfield.FixedBooleanGenerationAlgorithm;
import com.ioteg.generators.booleanfield.RandomBooleanGenerationAlgorithm;
import com.ioteg.generators.complexfield.ComplexFieldGenerator;
import com.ioteg.generators.complexfield.ComplexFieldGeneratorAlgorithm;
import com.ioteg.generators.datefield.DateGenerator;
import com.ioteg.generators.datefield.FixedDateGenerationAlgorithm;
import com.ioteg.generators.datefield.RandomDateGenerationAlgorithm;
import com.ioteg.generators.exceptions.NotExistingGeneratorException;
import com.ioteg.generators.floatfield.CustomiseBehaviourGenerationAlgorithm;
import com.ioteg.generators.floatfield.FixedFloatGenerationAlgorithm;
import com.ioteg.generators.floatfield.FloatGenerator;
import com.ioteg.generators.floatfield.RandomFloatGenerationAlgorithm;
import com.ioteg.generators.longfield.FixedLongGenerationAlgorithm;
import com.ioteg.generators.longfield.RandomLongGenerationAlgorithm;
import com.ioteg.generators.stringfield.FixedStringGenerationAlgorithm;
import com.ioteg.generators.stringfield.RandomStringGenerationAlgorithm;
import com.ioteg.generators.stringfield.StringGenerator;
import com.ioteg.model.Field;

public class NormalGeneratorsFactory {

	private static final String ALPHABETICAL_VALUES = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private static final String ALPHANUMERIC_VALUES = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";

	private NormalGeneratorsFactory() {
		throw new IllegalStateException("This is an utility class and can't be instantiated.");
	}

	private static CustomiseBehaviourGenerationAlgorithm customiseBehaviourGenerationAlgorithm;

	public static Generable makeGenerator(Field field, Integer totalNumberOfEvents)
			throws NotExistingGeneratorException, IOException {
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
		else if (!field.getType().equals(null))
			generable = makeComplexGenerator(field);
		else
			throw new NotExistingGeneratorException("There is no existing generator for the type: null");

		return generable;
	}

	public static Generable makeComplexGenerator(Field field) throws NotExistingGeneratorException, IOException {
		return new ComplexFieldGenerator(new ComplexFieldGeneratorAlgorithm(field), field);
	}

	public static FieldGenerator<Long> makeLongGenerator(Field longField) {
		FieldGenerator<Long> longGenerator = null;

		if (longField.getValue() != null)
			longGenerator = new FieldGenerator<>(new FixedLongGenerationAlgorithm(longField), longField);
		else if (longField.getMin() != null && longField.getMax() != null)
			longGenerator = new FieldGenerator<>(new RandomLongGenerationAlgorithm(longField), longField);

		return longGenerator;
	}

	public static FieldGenerator<Float> makeFloatGenerator(Field floatField, Integer totalNumOfEvents) throws IOException {
		FieldGenerator<Float> floatGenerator = null;

		if (floatField.getValue() != null)
			floatGenerator = new FloatGenerator(new FixedFloatGenerationAlgorithm(floatField), floatField);
		else if (floatField.getCustomBehaviour() != null) {

			if (isNeededANewCustomiseBehaviourAlgorithm())
				customiseBehaviourGenerationAlgorithm = new CustomiseBehaviourGenerationAlgorithm(floatField,
						totalNumOfEvents);

			floatGenerator = new FloatGenerator(customiseBehaviourGenerationAlgorithm, floatField);
		} else if (floatField.getMin() != null && floatField.getMax() != null)
			floatGenerator = new FloatGenerator(new RandomFloatGenerationAlgorithm(floatField), floatField);

		return floatGenerator;
	}

	private static boolean isNeededANewCustomiseBehaviourAlgorithm() {
		return customiseBehaviourGenerationAlgorithm == null
				|| customiseBehaviourGenerationAlgorithm.getRules().isEmpty();
	}

	public static FieldGenerator<Boolean> makeBooleanGenerator(Field booleanField) {
		FieldGenerator<Boolean> booleanGenerator = null;

		if (booleanField.getValue() != null)
			booleanGenerator = new BooleanGenerator(new FixedBooleanGenerationAlgorithm(booleanField), booleanField);
		else
			booleanGenerator = new BooleanGenerator(new RandomBooleanGenerationAlgorithm(booleanField), booleanField);

		return booleanGenerator;
	}

	public static FieldGenerator<Date> makeDateGenerator(Field dateField) {
		FieldGenerator<Date> dateGenerator = null;

		if (dateField.getValue() != null)
			dateGenerator = new DateGenerator(new FixedDateGenerationAlgorithm(dateField), dateField);
		else
			dateGenerator = new DateGenerator(new RandomDateGenerationAlgorithm(dateField), dateField);

		return dateGenerator;
	}

	public static FieldGenerator<Date> makeTimeGenerator(Field timeField) {
		return makeDateGenerator(timeField);
	}

	public static FieldGenerator<String> makeStringGenerator(Field stringField) {
		FieldGenerator<String> stringGenerator = null;

		if (stringField.getValue() != null)
			stringGenerator = new StringGenerator(new FixedStringGenerationAlgorithm(stringField), stringField);
		else
			stringGenerator = new StringGenerator(new RandomStringGenerationAlgorithm(stringField, ALPHABETICAL_VALUES),
					stringField);

		return stringGenerator;
	}

	public static FieldGenerator<String> makeAlphanumericGenerator(Field alphanumericField) {
		FieldGenerator<String> alphanumericGenerator = null;

		if (alphanumericField.getValue() != null)
			alphanumericGenerator = new StringGenerator(new FixedStringGenerationAlgorithm(alphanumericField),
					alphanumericField);
		else
			alphanumericGenerator = new StringGenerator(
					new RandomStringGenerationAlgorithm(alphanumericField, ALPHANUMERIC_VALUES), alphanumericField);

		return alphanumericGenerator;
	}
}
