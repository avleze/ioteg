package com.ioteg.generation;
import java.util.Date;


import com.ioteg.RandomUtil;
import com.ioteg.model.Field;

/**
 * <p>RandomDateGenerationAlgorithm class.</p>
 *
 * @author antonio
 * @version $Id: $Id
 */
public class RandomDateGenerationAlgorithm extends GenerationAlgorithm<Date> {
	
	/**
	 * <p>Constructor for RandomDateGenerationAlgorithm.</p>
	 *
	 * @param field a {@link com.ioteg.model.Field} object.
	 * @param generationContext a {@link com.ioteg.generation.GenerationContext} object.
	 */
	public RandomDateGenerationAlgorithm(Field field, GenerationContext generationContext) {
		super(field, generationContext);
	}

	/** {@inheritDoc} */
	@Override
	public Date generate() {
		return RandomUtil.getRandomDate(new Date(RandomUtil.getMinimumDate()),
				new Date(RandomUtil.getMaximumDate()), false);
	}

}
