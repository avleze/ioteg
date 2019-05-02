package com.ioteg.generation;

import com.ioteg.model.Field;

/**
 * <p>RandomLongGenerationAlgorithm class.</p>
 *
 * @author antonio
 * @version $Id: $Id
 */
public class RandomLongGenerationAlgorithm extends GenerationAlgorithm<Long> {
	
	/**
	 * <p>Constructor for RandomLongGenerationAlgorithm.</p>
	 *
	 * @param field a {@link com.ioteg.model.Field} object.
	 * @param generationContext a {@link com.ioteg.generation.GenerationContext} object.
	 */
	public RandomLongGenerationAlgorithm(Field field, GenerationContext generationContext) {
		super(field, generationContext);
	}

	/** {@inheritDoc} */
	@Override
	public Long generate() {
		Long min = field.getMin().longValue();
		Long max = field.getMax().longValue();

		return r.longs(min, max + 1).findFirst().getAsLong();
	}

}
