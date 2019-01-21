package com.ioteg.generators.integerfield;

import com.ioteg.generators.GenerationAlgorithm;
import com.ioteg.model.Field;

public class FixedIntegerGenerationAlgorithm extends GenerationAlgorithm<Integer> {

	@Override
	public Integer generate(Field integer) {
		return Integer.valueOf(integer.getValue());
	}

}
