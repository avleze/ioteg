package com.ioteg.generators.stringfield;

import com.ioteg.RandomUtil;
import com.ioteg.generators.GenerationAlgorithm;
import com.ioteg.generators.context.GenerationContext;
import com.ioteg.model.Field;

/**
 * <p>RandomStringGenerationAlgorithm class.</p>
 *
 * @author antonio
 * @version $Id: $Id
 */
public class RandomStringGenerationAlgorithm extends GenerationAlgorithm<String> {

	protected String possibleChars;
	
	/**
	 * <p>Constructor for RandomStringGenerationAlgorithm.</p>
	 *
	 * @param field a {@link com.ioteg.model.Field} object.
	 * @param generationContext a {@link com.ioteg.generators.context.GenerationContext} object.
	 * @param possibleChars a {@link java.lang.String} object.
	 */
	public RandomStringGenerationAlgorithm(Field field, GenerationContext generationContext, String possibleChars) {
		super(field, generationContext);
		this.possibleChars = possibleChars;
	}

	/** {@inheritDoc} */
	@Override
	public String generate() {
		return RandomUtil.getRandStringRange(field.getLength(), field.getEndcharacter(), possibleChars);
	}

}
