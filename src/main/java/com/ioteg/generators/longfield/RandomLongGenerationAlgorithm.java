package com.ioteg.generators.longfield;

import com.ioteg.generators.GenerationAlgorithm;
import com.ioteg.model.Field;

public class RandomLongGenerationAlgorithm extends GenerationAlgorithm<Long> {
	
	public RandomLongGenerationAlgorithm(Field field) {
		super(field);
	}

	@Override
	public Long generate() {
		Long min = field.getMin().longValue();
		Long max = field.getMax().longValue();

		return r.longs(min, max).findFirst().getAsLong();
	}

}
