package com.ioteg.generators.longfield;

import com.ioteg.generators.GenerationAlgorithm;
import com.ioteg.model.Field;

public class FixedLongGenerationAlgorithm extends GenerationAlgorithm<Long> {

	public FixedLongGenerationAlgorithm(Field field) {
		super(field);
	}

	@Override
	public Long generate() {
		return Long.valueOf(field.getValue());
	}

}
