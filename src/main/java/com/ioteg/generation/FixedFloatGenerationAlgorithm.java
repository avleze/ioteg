package com.ioteg.generation;

import com.ioteg.model.Field;

/**
 * <p>FixedFloatGenerationAlgorithm class.</p>
 *
 * @author antonio
 * @version $Id: $Id
 */
public class FixedFloatGenerationAlgorithm extends GenerationAlgorithm<Float> {

	/**
	 * <p>Constructor for FixedFloatGenerationAlgorithm.</p>
	 *
	 * @param field a {@link com.ioteg.model.Field} object.
	 * @param generationContext a {@link com.ioteg.generation.GenerationContext} object.
	 */
	public FixedFloatGenerationAlgorithm(Field field, GenerationContext generationContext) {
		super(field, generationContext);
	}

	/** {@inheritDoc} */
	@Override
	public Float generate() {
		return Float.valueOf(field.getValue());
	}

}
