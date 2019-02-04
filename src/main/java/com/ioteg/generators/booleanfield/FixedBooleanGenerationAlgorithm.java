package com.ioteg.generators.booleanfield;

import com.ioteg.generators.GenerationAlgorithm;
import com.ioteg.model.Field;

public class FixedBooleanGenerationAlgorithm extends GenerationAlgorithm<Boolean> {

	public FixedBooleanGenerationAlgorithm(Field field) {
		super(field);
	}

	@Override
	public Boolean generate() {
		return Boolean.valueOf(field.getValue());
	}

}
