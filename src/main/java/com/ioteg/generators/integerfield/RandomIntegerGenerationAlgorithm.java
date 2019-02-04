package com.ioteg.generators.integerfield;


import com.ioteg.generators.GenerationAlgorithm;
import com.ioteg.model.Field;

public class RandomIntegerGenerationAlgorithm extends GenerationAlgorithm<Integer> {
		
	public RandomIntegerGenerationAlgorithm(Field field) {
		super(field);
	}

	@Override
	public Integer generate() {
		Integer min = field.getMin().intValue();
		Integer max = field.getMax().intValue();
		return min + r.nextInt(max - min);
	}

}
