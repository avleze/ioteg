package com.ioteg.generators.floatfield;

import com.ioteg.generators.GenerationAlgorithm;
import com.ioteg.model.Field;

public class RandomFloatGenerationAlgorithm extends GenerationAlgorithm<Float> {
		
	public RandomFloatGenerationAlgorithm(Field field) {
		super(field);
	}

	@Override
	public Float generate() {
		Double min = field.getMin();
		Double max = field.getMax();
		
		return (float) r.doubles(min, max).findFirst().getAsDouble();
	}

}
