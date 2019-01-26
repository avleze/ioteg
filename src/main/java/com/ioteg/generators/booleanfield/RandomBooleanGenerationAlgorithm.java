package com.ioteg.generators.booleanfield;

import com.ioteg.generators.GenerationAlgorithm;
import com.ioteg.model.Field;

public class RandomBooleanGenerationAlgorithm extends GenerationAlgorithm<Boolean> {
		
	@Override
	public Boolean generate(Field booleanField) {
		return r.nextBoolean();
	}

}
