package com.ioteg.generators.stringfield;

import com.ioteg.RandomUtil;
import com.ioteg.generators.GenerationAlgorithm;
import com.ioteg.model.Field;

public class RandomAlphanumericalGenerationAlgorithm extends GenerationAlgorithm<String> {

	public RandomAlphanumericalGenerationAlgorithm(Field field) {
		super(field);
	}

	@Override
	public String generate() {
		return RandomUtil.getAlphaNumRandStringRange(field.getLength(), field.getEndcharacter());
	}

}
