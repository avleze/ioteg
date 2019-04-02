package com.ioteg.generation;

import com.ioteg.model.Field;

/**
 * <p>SequentialLongGenerationAlgorithm class.</p>
 *
 * @author antonio
 * @version $Id: $Id
 */
public class SequentialLongGenerationAlgorithm extends GenerationAlgorithm<Long> {

	private Long begin;
	private Long step;
	private Long end;

	private Long value;

	/**
	 * <p>Constructor for SequentialLongGenerationAlgorithm.</p>
	 *
	 * @param field a {@link com.ioteg.model.Field} object.
	 * @param generationContext a {@link com.ioteg.generation.GenerationContext} object.
	 */
	public SequentialLongGenerationAlgorithm(Field field, GenerationContext generationContext) {
		super(field, generationContext);
		this.begin = Long.valueOf(field.getBegin());
		this.step = Long.valueOf(field.getStep());
		this.end = Long.valueOf(field.getEnd());

		this.value = this.begin;

	}

	/** {@inheritDoc} */
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
