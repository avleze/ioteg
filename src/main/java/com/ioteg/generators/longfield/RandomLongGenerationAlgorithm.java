package com.ioteg.generators.longfield;

import com.ioteg.generators.GenerationAlgorithm;
import com.ioteg.generators.context.GenerationContext;
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
	 * @param generationContext a {@link com.ioteg.generators.context.GenerationContext} object.
	 */
	public RandomLongGenerationAlgorithm(Field field, GenerationContext generationContext) {
		super(field, generationContext);
	}

	/** {@inheritDoc} */
	@Override
	public Long generate() {
		Long min = field.getMin().longValue();
		Long max = field.getMax().longValue();

		return r.longs(min, max).findFirst().getAsLong();
	}

}
