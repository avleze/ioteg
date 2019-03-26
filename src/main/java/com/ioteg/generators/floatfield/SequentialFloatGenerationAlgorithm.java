package com.ioteg.generators.floatfield;

import com.ioteg.generators.GenerationAlgorithm;
import com.ioteg.model.Field;

public class SequentialFloatGenerationAlgorithm extends GenerationAlgorithm<Float> {

	private Double step;
	private Double begin;
	private Double end;
	private Double value;

	public SequentialFloatGenerationAlgorithm(Field field) {
		super(field);
		this.step = Double.valueOf(field.getStep());

		this.begin = Double.valueOf(field.getBegin());
		this.end = Double.valueOf(field.getEnd());

		this.value = begin;
	}

	@Override
	public Float generate() {
		Double actualValue = this.value;

		if (step > 0 && (value + step) <= end)
			value += step;
		else if (step < 0 && (value + step) >= end)
			value += step;
		else
			value = begin;

		return actualValue.floatValue();
	}

}
