package com.ioteg.generators.stringfield;

import com.ioteg.RandomUtil;
import com.ioteg.generators.GenerationAlgorithm;
import com.ioteg.model.Field;

public class RandomStringGenerationAlgorithm extends GenerationAlgorithm<String> {

	protected String possibleChars;
	
	public RandomStringGenerationAlgorithm(Field field, String possibleChars) {
		super(field);
		this.possibleChars = possibleChars;
	}

	@Override
	public String generate() {
		return RandomUtil.getRandStringRange(field.getLength(), field.getEndcharacter(), possibleChars);
	}

}
