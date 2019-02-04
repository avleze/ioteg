package com.ioteg.generators.booleanfield;

import com.ioteg.generators.GenerationAlgorithm;
import com.ioteg.model.Field;

public class RandomBooleanGenerationAlgorithm extends GenerationAlgorithm<Boolean> {
		
	public RandomBooleanGenerationAlgorithm(Field field) {
		super(field);
	}

	@Override
	public Boolean generate() {
		return r.nextBoolean();
	}

}
