package com.ioteg.generators.booleanfield;

import com.ioteg.generators.GenerationAlgorithm;
import com.ioteg.generators.context.GenerationContext;
import com.ioteg.model.Field;

/**
 * <p>FixedBooleanGenerationAlgorithm class.</p>
 *
 * @author antonio
 * @version $Id: $Id
 */
public class FixedBooleanGenerationAlgorithm extends GenerationAlgorithm<Boolean> {

	/**
	 * <p>Constructor for FixedBooleanGenerationAlgorithm.</p>
	 *
	 * @param field a {@link com.ioteg.model.Field} object.
	 * @param generationContext a {@link com.ioteg.generators.context.GenerationContext} object.
	 */
	public FixedBooleanGenerationAlgorithm(Field field, GenerationContext generationContext) {
		super(field, generationContext);
	}

	/** {@inheritDoc} */
	@Override
	public Boolean generate() {
		return Boolean.valueOf(field.getValue());
	}

}
