package com.ioteg.generators.booleanfield;

import com.ioteg.generators.GenerationAlgorithm;
import com.ioteg.model.Field;

public class FixedBooleanGenerationAlgorithm implements GenerationAlgorithm<Boolean> {

	@Override
	public Boolean generate(Field booleanField) {
		return Boolean.valueOf(booleanField.getValue());
	}

}
