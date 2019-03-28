package com.ioteg.generators.longfield;

import com.ioteg.generators.GenerationAlgorithm;
import com.ioteg.generators.context.GenerationContext;
import com.ioteg.model.Field;

public class SequentialLongGenerationAlgorithm extends GenerationAlgorithm<Long> {

	private Long begin;
	private Long step;
	private Long end;

	private Long value;

	public SequentialLongGenerationAlgorithm(Field field, GenerationContext generationContext) {
		super(field, generationContext);
		this.begin = Long.valueOf(field.getBegin());
		this.step = Long.valueOf(field.getStep());
		this.end = Long.valueOf(field.getEnd());

		this.value = this.begin;

	}

	@Override
	public Long generate() {
		Long actualValue = this.value;

		if (step > 0 && (value + step) <= end)
			value += step;
		else if (step < 0 && (value + step) >= end)
			value += step;
		else
			value = begin;

		return actualValue;
	}

}
