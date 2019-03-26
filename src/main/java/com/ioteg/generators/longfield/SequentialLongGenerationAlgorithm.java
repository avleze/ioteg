package com.ioteg.generators.longfield;

import com.ioteg.generators.GenerationAlgorithm;
import com.ioteg.model.Field;

public class SequentialLongGenerationAlgorithm extends GenerationAlgorithm<Long> {

	private Long value;
	private Long step;
	private Long limit;

	public SequentialLongGenerationAlgorithm(Field field) {
		super(field);
		this.step = Long.valueOf(field.getStep());
		if (step < 0) {
			this.value = Long.valueOf(field.getEnd());
			this.limit = Long.valueOf(field.getBegin());
		} else {
			this.value = Long.valueOf(field.getBegin());
			this.limit = Long.valueOf(field.getEnd());
		}

	}

	@Override
	public Long generate() {
		Long actualValue = this.value;

		if (step < 0) {
			if (value + step >= limit)
				value += step;
			else
				value = Long.valueOf(field.getEnd());
		} else {
			if (value + step <= limit)
				value += step;
			else
				value = Long.valueOf(field.getBegin());
		}

		return actualValue;
	}

}
