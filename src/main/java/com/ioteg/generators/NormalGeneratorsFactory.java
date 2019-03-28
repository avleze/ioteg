package com.ioteg.generators;

import java.text.ParseException;
import java.util.Date;

import com.ioteg.exprlang.ExprParser.ExprLangParsingException;
import com.ioteg.generators.booleanfield.BooleanGenerator;
import com.ioteg.generators.booleanfield.FixedBooleanGenerationAlgorithm;
import com.ioteg.generators.booleanfield.RandomBooleanGenerationAlgorithm;
import com.ioteg.generators.complexfield.ComplexFieldGenerator;
import com.ioteg.generators.complexfield.ComplexFieldGeneratorAlgorithm;
import com.ioteg.generators.context.GenerationContext;
import com.ioteg.generators.datefield.DateGenerator;
import com.ioteg.generators.datefield.FixedDateGenerationAlgorithm;
import com.ioteg.generators.datefield.RandomDateGenerationAlgorithm;
import com.ioteg.generators.datefield.SequentialDateGenerationAlgorithm;
import com.ioteg.generators.exceptions.NotExistingGeneratorException;
import com.ioteg.generators.floatfield.CustomiseBehaviourGenerationAlgorithm;
import com.ioteg.generators.floatfield.FixedFloatGenerationAlgorithm;
import com.ioteg.generators.floatfield.FloatGenerator;
import com.ioteg.generators.floatfield.RandomFloatGenerationAlgorithm;
import com.ioteg.generators.floatfield.SequentialFloatGenerationAlgorithm;
import com.ioteg.generators.longfield.FixedLongGenerationAlgorithm;
import com.ioteg.generators.longfield.RandomLongGenerationAlgorithm;
import com.ioteg.generators.longfield.SequentialLongGenerationAlgorithm;
import com.ioteg.generators.stringfield.FixedStringGenerationAlgorithm;
import com.ioteg.generators.stringfield.RandomStringGenerationAlgorithm;
import com.ioteg.generators.stringfield.SequentialStringGenerationAlgorithm;
import com.ioteg.generators.stringfield.StringGenerator;
import com.ioteg.model.Field;

public class NormalGeneratorsFactory {

	private static final String ALPHABETICAL_VALUES = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private static final String ALPHANUMERIC_VALUES = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";

	private NormalGeneratorsFactory() {
		throw new IllegalStateException("This is an utility class and can't be instantiated.");
	}

	public static Generable makeGenerator(Field field, Integer totalNumberOfEvents, GenerationContext generationContext)
			throws NotExistingGeneratorException, ExprLangParsingException, ParseException {
		Generable generable = null;

		if (field.getType().equals("Integer") || field.getType().equals("Long"))
			generable = makeLongGenerator(field, generationContext);
		else if (field.getType().equals("String"))
			generable = makeStringGenerator(field, generationContext);
		else if (field.getType().equals("Alphanumeric"))
			generable = makeAlphanumericGenerator(field, generationContext);
		else if (field.getType().equals("Float"))
			generable = makeFloatGenerator(field, totalNumberOfEvents, generationContext);
		else if (field.getType().equals("Boolean"))
			generable = makeBooleanGenerator(field, generationContext);
		else if (field.getType().equals("Date"))
			generable = makeDateGenerator(field, generationContext);
		else if (field.getType().equals("Time"))
			generable = makeDateGenerator(field, generationContext);
		else if (field.getType() != null)
			generable = makeComplexGenerator(field, generationContext);
		else
			throw new NotExistingGeneratorException("There is no existing generator for the type: null");

		return generable;
	}

	public static Generable makeComplexGenerator(Field field, GenerationContext generationContext)
			throws NotExistingGeneratorException, ExprLangParsingException, ParseException {
		return new ComplexFieldGenerator(new ComplexFieldGeneratorAlgorithm(field, generationContext), field, generationContext);
	}

	public static FieldGenerator<Long> makeLongGenerator(Field longField, GenerationContext generationContext) {
		FieldGenerator<Long> longGenerator = null;

		if (longField.getValue() != null)
			longGenerator = new FieldGenerator<>(new FixedLongGenerationAlgorithm(longField, generationContext), longField, generationContext);
		else if (longField.getBegin() != null && longField.getEnd() != null && longField.getStep() != null)
			longGenerator = new FieldGenerator<>(new SequentialLongGenerationAlgorithm(longField, generationContext), longField, generationContext);
		else if (longField.getMin() != null && longField.getMax() != null)
			longGenerator = new FieldGenerator<>(new RandomLongGenerationAlgorithm(longField, generationContext), longField, generationContext);

		return longGenerator;
	}

	public static FieldGenerator<Float> makeFloatGenerator(Field floatField, Integer totalNumOfEvents, GenerationContext generationContext)
			throws ExprLangParsingException {
		FieldGenerator<Float> floatGenerator = null;

		if (floatField.getValue() != null)
			floatGenerator = new FloatGenerator(new FixedFloatGenerationAlgorithm(floatField, generationContext), floatField, generationContext);
		else if (floatField.getCustomBehaviour() != null) {
			floatGenerator = new FloatGenerator(new CustomiseBehaviourGenerationAlgorithm(floatField, totalNumOfEvents, generationContext),
					floatField, generationContext);
		} else if (floatField.getBegin() != null && floatField.getEnd() != null && floatField.getStep() != null) {
			floatGenerator = new FloatGenerator(new SequentialFloatGenerationAlgorithm(floatField, generationContext), floatField, generationContext);
		} else if (floatField.getMin() != null && floatField.getMax() != null) {
			floatGenerator = new FloatGenerator(new RandomFloatGenerationAlgorithm(floatField, generationContext), floatField, generationContext);
		}

		return floatGenerator;
	}

	public static FieldGenerator<Boolean> makeBooleanGenerator(Field booleanField, GenerationContext generationContext) {
		FieldGenerator<Boolean> booleanGenerator = null;

		if (booleanField.getValue() != null)
			booleanGenerator = new BooleanGenerator(new FixedBooleanGenerationAlgorithm(booleanField, generationContext), booleanField, generationContext);
		else
			booleanGenerator = new BooleanGenerator(new RandomBooleanGenerationAlgorithm(booleanField, generationContext), booleanField, generationContext);

		return booleanGenerator;
	}

	public static FieldGenerator<Date> makeDateGenerator(Field dateField, GenerationContext generationContext) throws ParseException {
		FieldGenerator<Date> dateGenerator = null;

		if (dateField.getValue() != null)
			dateGenerator = new DateGenerator(new FixedDateGenerationAlgorithm(dateField, generationContext), dateField, generationContext);
		else if (dateField.getBegin() != null && dateField.getEnd() != null && dateField.getStep() != null)
			dateGenerator = new DateGenerator(new SequentialDateGenerationAlgorithm(dateField, generationContext), dateField, generationContext);
		else
			dateGenerator = new DateGenerator(new RandomDateGenerationAlgorithm(dateField, generationContext), dateField, generationContext);

		return dateGenerator;
	}

	public static FieldGenerator<Date> makeTimeGenerator(Field timeField, GenerationContext generationContext) throws ParseException {
		return makeDateGenerator(timeField, generationContext);
	}

	public static FieldGenerator<String> makeStringGenerator(Field stringField, GenerationContext generationContext) {
		FieldGenerator<String> stringGenerator = null;

		if (stringField.getValue() != null)
			stringGenerator = new StringGenerator(new FixedStringGenerationAlgorithm(stringField, generationContext), stringField, null);
		else if (stringField.getBegin() != null && stringField.getStep() != null)
			stringGenerator = new StringGenerator(
					new SequentialStringGenerationAlgorithm(stringField, ALPHABETICAL_VALUES, generationContext), stringField, generationContext);
		else
			stringGenerator = new StringGenerator(new RandomStringGenerationAlgorithm(stringField, generationContext, ALPHABETICAL_VALUES),
					stringField, generationContext);

		return stringGenerator;
	}

	public static FieldGenerator<String> makeAlphanumericGenerator(Field alphanumericField, GenerationContext generationContext) {
		FieldGenerator<String> alphanumericGenerator = null;

		if (alphanumericField.getValue() != null)
			alphanumericGenerator = new StringGenerator(new FixedStringGenerationAlgorithm(alphanumericField, generationContext),
					alphanumericField, generationContext);
		else if (alphanumericField.getBegin() != null && alphanumericField.getStep() != null)
			alphanumericGenerator = new StringGenerator(
					new SequentialStringGenerationAlgorithm(alphanumericField, ALPHANUMERIC_VALUES, generationContext), alphanumericField, generationContext);
		else
			alphanumericGenerator = new StringGenerator(
					new RandomStringGenerationAlgorithm(alphanumericField, generationContext, ALPHANUMERIC_VALUES), alphanumericField, generationContext);

		return alphanumericGenerator;
	}
}
