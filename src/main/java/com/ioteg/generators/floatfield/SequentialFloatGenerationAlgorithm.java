package com.ioteg.generators.floatfield;

import com.ioteg.generators.GenerationAlgorithm;
import com.ioteg.generators.context.GenerationContext;
import com.ioteg.model.Field;

/**
 * <p>SequentialFloatGenerationAlgorithm class.</p>
 *
 * @author antonio
 * @version $Id: $Id
 */
public class SequentialFloatGenerationAlgorithm extends GenerationAlgorithm<Float> {

	private Double step;
	private Double begin;
	private Double end;
	private Double value;

	/**
	 * <p>Constructor for SequentialFloatGenerationAlgorithm.</p>
	 *
	 * @param field a {@link com.ioteg.model.Field} object.
	 * @param generationContext a {@link com.ioteg.generators.context.GenerationContext} object.
	 */
	public SequentialFloatGenerationAlgorithm(Field field, GenerationContext generationContext) {
		super(field, generationContext);
		this.step = Double.valueOf(field.getStep());

		this.begin = Double.valueOf(field.getBegin());
		this.end = Double.valueOf(field.getEnd());

		this.value = begin;
	}

	/** {@inheritDoc} */
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
