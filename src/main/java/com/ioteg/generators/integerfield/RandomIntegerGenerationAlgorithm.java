package com.ioteg.generators.integerfield;


import com.ioteg.generators.GenerationAlgorithm;
import com.ioteg.model.Field;

public class RandomIntegerGenerationAlgorithm extends GenerationAlgorithm<Integer> {
		
	@Override
	public Integer generate(Field integer) {
		Integer min = integer.getMin().intValue();
		Integer max = integer.getMax().intValue();
		return min + r.nextInt(max - min);
	}

}
