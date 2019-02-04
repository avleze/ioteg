package com.ioteg.generators.stringfield;

import com.ioteg.RandomUtil;
import com.ioteg.generators.GenerationAlgorithm;
import com.ioteg.model.Field;

public class RandomStringGenerationAlgorithm extends GenerationAlgorithm<String> {

	public RandomStringGenerationAlgorithm(Field field) {
		super(field);
	}

	@Override
	public String generate() {
		return RandomUtil.getRandStringRange(field.getLength(), field.getEndcharacter());
	}

}
