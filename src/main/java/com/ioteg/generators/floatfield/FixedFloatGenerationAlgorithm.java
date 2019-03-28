package com.ioteg.generators.floatfield;

import com.ioteg.generators.GenerationAlgorithm;
import com.ioteg.generators.context.GenerationContext;
import com.ioteg.model.Field;

public class FixedFloatGenerationAlgorithm extends GenerationAlgorithm<Float> {

	public FixedFloatGenerationAlgorithm(Field field, GenerationContext generationContext) {
		super(field, generationContext);
	}

	@Override
	public Float generate() {
		return Float.valueOf(field.getValue());
	}

}
