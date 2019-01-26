package com.ioteg.generators.longfield;

import com.ioteg.generators.GenerationAlgorithm;
import com.ioteg.model.Field;

public class RandomLongGenerationAlgorithm extends GenerationAlgorithm<Long> {
	
	
	@Override
	public Long generate(Field longField) {
		Long min = longField.getMin().longValue();
		Long max = longField.getMax().longValue();

		return r.longs(min, max).findFirst().getAsLong();
	}

}
