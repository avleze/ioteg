package com.ioteg.generators.integerfield;

import com.ioteg.generators.GenerationAlgorithm;
import com.ioteg.model.Field;

public class FixedIntegerGenerationAlgorithm extends GenerationAlgorithm<Integer> {

	public FixedIntegerGenerationAlgorithm(Field field) {
		super(field);
	}

	@Override
	public Integer generate() {
		return Integer.valueOf(field.getValue());
	}

}
