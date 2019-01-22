package com.ioteg.generators.integerfield;

import com.ioteg.generators.GenerationAlgorithm;
import com.ioteg.model.Field;

public class FixedIntegerGenerationAlgorithm implements GenerationAlgorithm<Integer> {

	@Override
	public Integer generate(Field integer) {
		return Integer.valueOf(integer.getValue());
	}

}
