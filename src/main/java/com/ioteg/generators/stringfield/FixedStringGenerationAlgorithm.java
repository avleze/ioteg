package com.ioteg.generators.stringfield;

import com.ioteg.generators.GenerationAlgorithm;
import com.ioteg.generators.context.GenerationContext;
import com.ioteg.model.Field;

public class FixedStringGenerationAlgorithm extends GenerationAlgorithm<String> {

	public FixedStringGenerationAlgorithm(Field field, GenerationContext generationContext) {
		super(field, generationContext);
	}

	@Override
	public String generate() {
		return field.getValue();
	}

}
