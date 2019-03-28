package com.ioteg.generators.stringfield;

import com.ioteg.RandomUtil;
import com.ioteg.generators.GenerationAlgorithm;
import com.ioteg.generators.context.GenerationContext;
import com.ioteg.model.Field;

public class RandomStringGenerationAlgorithm extends GenerationAlgorithm<String> {

	protected String possibleChars;
	
	public RandomStringGenerationAlgorithm(Field field, GenerationContext generationContext, String possibleChars) {
		super(field, generationContext);
		this.possibleChars = possibleChars;
	}

	@Override
	public String generate() {
		return RandomUtil.getRandStringRange(field.getLength(), field.getEndcharacter(), possibleChars);
	}

}
