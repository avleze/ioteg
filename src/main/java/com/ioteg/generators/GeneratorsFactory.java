package com.ioteg.generators;

import com.ioteg.generators.floatfield.FixedFloatGenerationAlgorithm;
import com.ioteg.generators.floatfield.RandomFloatGenerationAlgorithm;
import com.ioteg.generators.integerfield.FixedIntegerGenerationAlgorithm;
import com.ioteg.generators.integerfield.RandomIntegerGenerationAlgorithm;
import com.ioteg.generators.longfield.FixedLongGenerationAlgorithm;
import com.ioteg.generators.longfield.RandomLongGenerationAlgorithm;
import com.ioteg.model.Field;

public class GeneratorsFactory {

	private GeneratorsFactory() {
		    throw new IllegalStateException("This is an utility class and can't be instantiated.");
		  }

	public static Generator<Integer> makeIntegerGenerator(Field integer) {
		Generator<Integer> integerGenerator = null;

		if (integer.getValue() != null)
			integerGenerator = new Generator<Integer>(new FixedIntegerGenerationAlgorithm());
		else if (integer.getMin() != null && integer.getMax() != null)
			integerGenerator = new Generator<Integer>(new RandomIntegerGenerationAlgorithm());

		return integerGenerator;
	}
	
	public static Generator<Long> makeLongGenerator(Field longField) {
		Generator<Long> longGenerator = null;

		if (longField.getValue() != null)
			longGenerator = new Generator<Long>(new FixedLongGenerationAlgorithm());
		else if (longField.getMin() != null && longField.getMax() != null)
			longGenerator = new Generator<Long>(new RandomLongGenerationAlgorithm());

		return longGenerator;
	}
	
	public static Generator<Float> makeFloatGenerator(Field floatField) {
		Generator<Float> floatGenerator = null;

		if (floatField.getValue() != null)
			floatGenerator = new Generator<Float>(new FixedFloatGenerationAlgorithm());
		else if (floatField.getMin() != null && floatField.getMax() != null)
			floatGenerator = new Generator<Float>(new RandomFloatGenerationAlgorithm());

		return floatGenerator;
	}
}
