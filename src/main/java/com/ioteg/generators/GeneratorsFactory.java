package com.ioteg.generators;

import java.util.Date;
import java.util.List;

import com.ioteg.Trio;
import com.ioteg.generators.booleanfield.BooleanGenerator;
import com.ioteg.generators.booleanfield.BooleanQueryRestrictionGenerationAlgorithm;
import com.ioteg.generators.booleanfield.FixedBooleanGenerationAlgorithm;
import com.ioteg.generators.booleanfield.RandomBooleanGenerationAlgorithm;
import com.ioteg.generators.datefield.DateGenerator;
import com.ioteg.generators.datefield.DateQueryRestrictionGenerationAlgorithm;
import com.ioteg.generators.datefield.FixedDateGenerationAlgorithm;
import com.ioteg.generators.datefield.RandomDateGenerationAlgorithm;
import com.ioteg.generators.floatfield.FixedFloatGenerationAlgorithm;
import com.ioteg.generators.floatfield.FloatGenerator;
import com.ioteg.generators.floatfield.FloatQueryRestrictionGenerationAlgorithm;
import com.ioteg.generators.floatfield.RandomFloatGenerationAlgorithm;
import com.ioteg.generators.integerfield.FixedIntegerGenerationAlgorithm;
import com.ioteg.generators.integerfield.IntegerQueryRestrictionGenerationAlgorithm;
import com.ioteg.generators.integerfield.RandomIntegerGenerationAlgorithm;
import com.ioteg.generators.longfield.FixedLongGenerationAlgorithm;
import com.ioteg.generators.longfield.LongQueryRestrictionGenerationAlgorithm;
import com.ioteg.generators.longfield.RandomLongGenerationAlgorithm;
import com.ioteg.generators.stringfield.AlphanumericQueryRestrictionGenerationAlgorithm;
import com.ioteg.generators.stringfield.FixedStringGenerationAlgorithm;
import com.ioteg.generators.stringfield.RandomAlphanumericalGenerationAlgorithm;
import com.ioteg.generators.stringfield.RandomStringGenerationAlgorithm;
import com.ioteg.generators.stringfield.StringGenerator;
import com.ioteg.generators.stringfield.StringQueryRestrictionGenerationAlgorithm;
import com.ioteg.model.Field;

public class GeneratorsFactory {

	private GeneratorsFactory() {
		    throw new IllegalStateException("This is an utility class and can't be instantiated.");
		  }
	
	public static Generable makeGenerator(Field field)
	{
		Generable generable = null;
		
		if(field.getType().equals("Integer"))
			generable = makeIntegerGenerator(field);
		else if(field.getType().equals("String"))
			generable = makeStringGenerator(field);
		else if(field.getType().equals("Alphanumeric"))
			generable = makeAlphanumericGenerator(field);
		else if(field.getType().equals("Long"))
			generable = makeLongGenerator(field);
		else if(field.getType().equals("Float"))
			generable = makeFloatGenerator(field);
		else if(field.getType().equals("Boolean"))
			generable = makeBooleanGenerator(field);
		else if(field.getType().equals("Date"))
			generable = makeDateGenerator(field);
		else if(field.getType().equals("Time"))
			generable = makeDateGenerator(field);
		
		return generable;
	}
	
	public static Generator<Integer> makeIntegerGenerator(Field integer) {
		Generator<Integer> integerGenerator = null;

		if (integer.getValue() != null)
			integerGenerator = new Generator<>(new FixedIntegerGenerationAlgorithm());
		else if (integer.getMin() != null && integer.getMax() != null)
			integerGenerator = new Generator<>(new RandomIntegerGenerationAlgorithm());

		return integerGenerator;
	}
	

	
	public static Generator<Long> makeLongGenerator(Field longField) {
		Generator<Long> longGenerator = null;

		if (longField.getValue() != null)
			longGenerator = new Generator<>(new FixedLongGenerationAlgorithm());
		else if (longField.getMin() != null && longField.getMax() != null)
			longGenerator = new Generator<>(new RandomLongGenerationAlgorithm());

		return longGenerator;
	}
	
	public static Generator<Float> makeFloatGenerator(Field floatField) {
		Generator<Float> floatGenerator = null;

		if (floatField.getValue() != null)
			floatGenerator = new FloatGenerator(new FixedFloatGenerationAlgorithm());
		else if (floatField.getMin() != null && floatField.getMax() != null)
			floatGenerator = new FloatGenerator(new RandomFloatGenerationAlgorithm());

		return floatGenerator;
	}
	
	public static Generator<Boolean> makeBooleanGenerator(Field booleanField) {
		Generator<Boolean> booleanGenerator = null;

		if (booleanField.getValue() != null)
			booleanGenerator = new BooleanGenerator(new FixedBooleanGenerationAlgorithm());
		else
			booleanGenerator = new BooleanGenerator(new RandomBooleanGenerationAlgorithm());

		return booleanGenerator;
	}
	
	public static Generator<Date> makeDateGenerator(Field dateField) {
		Generator<Date> dateGenerator = null;

		if (dateField.getValue() != null)
			dateGenerator = new DateGenerator(new FixedDateGenerationAlgorithm());
		else
			dateGenerator = new DateGenerator(new RandomDateGenerationAlgorithm());

		return dateGenerator;
	}
	
	public static Generator<Date> makeTimeGenerator(Field timeField) {
		return makeDateGenerator(timeField);
	}
	
	public static Generator<String> makeStringGenerator(Field stringField) {
		Generator<String> stringGenerator = null;

		if (stringField.getValue() != null)
			stringGenerator = new StringGenerator(new FixedStringGenerationAlgorithm());
		else
			stringGenerator = new StringGenerator(new RandomStringGenerationAlgorithm());

		return stringGenerator;
	}
	
	public static Generator<String> makeAlphanumericGenerator(Field alphanumericField) {
		Generator<String> alphanumericGenerator = null;

		if (alphanumericField.getValue() != null)
			alphanumericGenerator = new StringGenerator(new FixedStringGenerationAlgorithm());
		else
			alphanumericGenerator = new StringGenerator(new RandomAlphanumericalGenerationAlgorithm());

		return alphanumericGenerator;
	}
	
	
	public static Generable makeQueryRestrictionGenerator(Field field, List<Trio<String, String, String>> restrictions)
	{
		Generable generable = null;
		
		if(field.getType().equals("Integer"))
			generable = makeQueryRestrictionIntegerGenerator(field, restrictions);
		else if(field.getType().equals("String"))
			generable = makeQueryRestrictionStringGenerator(field, restrictions);
		else if(field.getType().equals("Alphanumeric"))
			generable = makeQueryRestrictionAlphanumericGenerator(field, restrictions);
		else if(field.getType().equals("Long"))
			generable = makeQueryRestrictionLongGenerator(field, restrictions);
		else if(field.getType().equals("Float"))
			generable = makeQueryRestrictionFloatGenerator(field, restrictions);
		else if(field.getType().equals("Boolean"))
			generable = makeQueryRestrictionBooleanGenerator(field, restrictions);
		else if(field.getType().equals("Date"))
			generable = makeQueryRestrictionDateGenerator(field, restrictions);
		else if(field.getType().equals("Time"))
			generable = makeQueryRestrictionTimeGenerator(field, restrictions);
		
		return generable;
	}
	
	public static Generator<Integer> makeQueryRestrictionIntegerGenerator(Field integerField, List<Trio<String, String, String>> restrictions) {
		return new Generator<>(new IntegerQueryRestrictionGenerationAlgorithm(restrictions));
	}
	
	public static Generator<String> makeQueryRestrictionStringGenerator(Field stringField, List<Trio<String, String, String>> restrictions) {
		return new StringGenerator(new StringQueryRestrictionGenerationAlgorithm(restrictions));
	}
	
	public static Generator<String> makeQueryRestrictionAlphanumericGenerator(Field alphanumericField, List<Trio<String, String, String>> restrictions) {
		return new StringGenerator(new AlphanumericQueryRestrictionGenerationAlgorithm(restrictions));
	}
	
	public static Generator<Long> makeQueryRestrictionLongGenerator(Field longField, List<Trio<String, String, String>> restrictions) {
		return new Generator<>(new LongQueryRestrictionGenerationAlgorithm(restrictions));
	}
	
	public static Generator<Float> makeQueryRestrictionFloatGenerator(Field floatField, List<Trio<String, String, String>> restrictions) {
		return new FloatGenerator(new FloatQueryRestrictionGenerationAlgorithm(restrictions));
	}
	
	public static Generator<Boolean> makeQueryRestrictionBooleanGenerator(Field booleanField, List<Trio<String, String, String>> restrictions) {
		return new BooleanGenerator(new BooleanQueryRestrictionGenerationAlgorithm(restrictions));
	}
	
	public static Generator<Date> makeQueryRestrictionDateGenerator(Field dateField, List<Trio<String, String, String>> restrictions) {
		return new DateGenerator(new DateQueryRestrictionGenerationAlgorithm(restrictions));
	}
	
	public static Generator<Date> makeQueryRestrictionTimeGenerator(Field timeField, List<Trio<String, String, String>> restrictions) {
		return makeQueryRestrictionDateGenerator(timeField, restrictions);
	}
}
