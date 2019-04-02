package com.ioteg.generation;

import com.ioteg.model.Field;

/**
 * <p>FixedLongGenerationAlgorithm class.</p>
 *
 * @author antonio
 * @version $Id: $Id
 */
public class FixedLongGenerationAlgorithm extends GenerationAlgorithm<Long> {

	/**
	 * <p>Constructor for FixedLongGenerationAlgorithm.</p>
	 *
	 * @param field a {@link com.ioteg.model.Field} object.
	 * @param generationContext a {@link com.ioteg.generation.GenerationContext} object.
	 */
	public FixedLongGenerationAlgorithm(Field field, GenerationContext generationContext) {
		super(field, generationContext);
	}

	/** {@inheritDoc} */
	@Override
	public Long generate() {
		return Long.valueOf(field.getValue());
	}

}
