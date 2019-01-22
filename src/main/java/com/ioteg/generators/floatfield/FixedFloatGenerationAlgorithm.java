package com.ioteg.generators.floatfield;

import com.ioteg.generators.GenerationAlgorithm;
import com.ioteg.model.Field;

public class FixedFloatGenerationAlgorithm implements GenerationAlgorithm<Float> {

	@Override
	public Float generate(Field longField) {
		return Float.valueOf(longField.getValue());
	}

}
