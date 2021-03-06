package com.ioteg.generation;

import com.ioteg.model.Field;

/**
 * <p>RandomBooleanGenerationAlgorithm class.</p>
 *
 * @author antonio
 * @version $Id: $Id
 */
public class RandomBooleanGenerationAlgorithm extends GenerationAlgorithm<Boolean> {

	/**
	 * <p>Constructor for RandomBooleanGenerationAlgorithm.</p>
	 *
	 * @param field a {@link com.ioteg.model.Field} object.
	 * @param generationContext a {@link com.ioteg.generation.GenerationContext} object.
	 */
	public RandomBooleanGenerationAlgorithm(Field field, GenerationContext generationContext) {
		super(field, generationContext);
	}

	/** {@inheritDoc} */
	@Override
	public Boolean generate() {
		return r.nextBoolean();
	}

}
