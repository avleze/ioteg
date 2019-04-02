package com.ioteg.generation;

import com.ioteg.model.Field;

/**
 * <p>RandomFloatGenerationAlgorithm class.</p>
 *
 * @author antonio
 * @version $Id: $Id
 */
public class RandomFloatGenerationAlgorithm extends GenerationAlgorithm<Float> {

	/**
	 * <p>Constructor for RandomFloatGenerationAlgorithm.</p>
	 *
	 * @param field a {@link com.ioteg.model.Field} object.
	 * @param generationContext a {@link com.ioteg.generation.GenerationContext} object.
	 */
	public RandomFloatGenerationAlgorithm(Field field, GenerationContext generationContext) {
		super(field, generationContext);
	}

	/** {@inheritDoc} */
	@Override
	public Float generate() {
		Double min = field.getMin();
		Double max = field.getMax();

		return (float) r.doubles(min, max).findFirst().getAsDouble();
	}

}
