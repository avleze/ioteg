package com.ioteg.generators.floatfield;

import com.ioteg.generators.GenerationAlgorithm;
import com.ioteg.generators.context.GenerationContext;
import com.ioteg.model.Field;

public class RandomFloatGenerationAlgorithm extends GenerationAlgorithm<Float> {

	public RandomFloatGenerationAlgorithm(Field field, GenerationContext generationContext) {
		super(field, generationContext);
	}

	@Override
	public Float generate() {
		Double min = field.getMin();
		Double max = field.getMax();

		return (float) r.doubles(min, max).findFirst().getAsDouble();
	}

}
