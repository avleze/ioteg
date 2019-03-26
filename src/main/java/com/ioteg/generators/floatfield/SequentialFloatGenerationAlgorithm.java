package com.ioteg.generators.floatfield;

import com.ioteg.generators.GenerationAlgorithm;
import com.ioteg.model.Field;

public class SequentialFloatGenerationAlgorithm extends GenerationAlgorithm<Float> {
		
	private Double step;
	private Double value;
	private Double limit;

	public SequentialFloatGenerationAlgorithm(Field field) {
		super(field);
		this.step = Double.valueOf(field.getStep());
		if (step < 0) {
			this.value = Double.valueOf(field.getEnd());
			this.limit = Double.valueOf(field.getBegin());
		} else {
			this.value = Double.valueOf(field.getBegin());
			this.limit = Double.valueOf(field.getEnd());
		}
	}

	@Override
	public Float generate() {
		Double actualValue = this.value;

		if (step < 0) {
			if (value + step >= limit)
				value += step;
			else
				value = Double.valueOf(field.getEnd());
		} else {
			if (value + step <= limit)
				value += step;
			else
				value = Double.valueOf(field.getBegin());
		}

		return actualValue.floatValue();
	}

}
