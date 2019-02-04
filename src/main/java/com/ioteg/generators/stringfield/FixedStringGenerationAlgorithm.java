package com.ioteg.generators.stringfield;

import com.ioteg.generators.GenerationAlgorithm;
import com.ioteg.model.Field;

public class FixedStringGenerationAlgorithm extends GenerationAlgorithm<String> {

	public FixedStringGenerationAlgorithm(Field field) {
		super(field);
	}

	@Override
	public String generate() {
		return field.getValue();
	}

}
