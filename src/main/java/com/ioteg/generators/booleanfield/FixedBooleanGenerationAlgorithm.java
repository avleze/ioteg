package com.ioteg.generators.booleanfield;

import com.ioteg.generators.GenerationAlgorithm;
import com.ioteg.generators.context.GenerationContext;
import com.ioteg.model.Field;

public class FixedBooleanGenerationAlgorithm extends GenerationAlgorithm<Boolean> {

	public FixedBooleanGenerationAlgorithm(Field field, GenerationContext generationContext) {
		super(field, generationContext);
	}

	@Override
	public Boolean generate() {
		return Boolean.valueOf(field.getValue());
	}

}
