package com.ioteg.generators.longfield;

import com.ioteg.generators.GenerationAlgorithm;
import com.ioteg.model.Field;

public class FixedLongGenerationAlgorithm implements GenerationAlgorithm<Long> {

	@Override
	public Long generate(Field longField) {
		return Long.valueOf(longField.getValue());
	}

}
