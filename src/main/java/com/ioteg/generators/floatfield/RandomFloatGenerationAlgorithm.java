package com.ioteg.generators.floatfield;

import com.ioteg.generators.GenerationAlgorithm;
import com.ioteg.model.Field;

public class RandomFloatGenerationAlgorithm extends GenerationAlgorithm<Float> {
		
	@Override
	public Float generate(Field floatField) {
		Double min = floatField.getMin();
		Double max = floatField.getMax();
		
		return (float) r.doubles(min, max).findFirst().getAsDouble();
	}

}
