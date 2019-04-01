package com.ioteg.generators.stringfield;

import com.ioteg.generators.GenerationAlgorithm;
import com.ioteg.generators.context.GenerationContext;
import com.ioteg.model.Field;

/**
 * <p>FixedStringGenerationAlgorithm class.</p>
 *
 * @author antonio
 * @version $Id: $Id
 */
public class FixedStringGenerationAlgorithm extends GenerationAlgorithm<String> {

	/**
	 * <p>Constructor for FixedStringGenerationAlgorithm.</p>
	 *
	 * @param field a {@link com.ioteg.model.Field} object.
	 * @param generationContext a {@link com.ioteg.generators.context.GenerationContext} object.
	 */
	public FixedStringGenerationAlgorithm(Field field, GenerationContext generationContext) {
		super(field, generationContext);
	}

	/** {@inheritDoc} */
	@Override
	public String generate() {
		return field.getValue();
	}

}
