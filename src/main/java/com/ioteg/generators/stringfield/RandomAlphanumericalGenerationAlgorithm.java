package com.ioteg.generators.stringfield;

import com.ioteg.RandomUtil;
import com.ioteg.generators.GenerationAlgorithm;
import com.ioteg.model.Field;

public class RandomAlphanumericalGenerationAlgorithm extends GenerationAlgorithm<String> {

	@Override
	public String generate(Field stringField) {
		return RandomUtil.getAlphaNumRandStringRange(stringField.getLength(), stringField.getEndcharacter());
	}

}
