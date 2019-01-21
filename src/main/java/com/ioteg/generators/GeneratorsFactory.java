package com.ioteg.generators;

import com.ioteg.generators.integer.FixedIntegerGenerator;
import com.ioteg.generators.integer.IntegerGenerator;
import com.ioteg.generators.integer.RandomIntegerGenerator;
import com.ioteg.model.Field;

public class GeneratorsFactory {

	private GeneratorsFactory() {
		    throw new IllegalStateException("Utility class");
		  }

	public static IntegerGenerator makeIntegerGenerator(Field integer) {
		IntegerGenerator integerGenerator = null;

		if (integer.getValue() != null)
			integerGenerator = new IntegerGenerator(new FixedIntegerGenerator());
		else if (integer.getMin() != null && integer.getMax() != null)
			integerGenerator = new IntegerGenerator(new RandomIntegerGenerator());

		return integerGenerator;
	}
}
