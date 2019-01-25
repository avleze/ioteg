package com.ioteg.generators.datefield;
import java.util.Date;


import com.ioteg.RandomUtil;
import com.ioteg.generators.GenerationAlgorithm;
import com.ioteg.model.Field;

public class RandomDateGenerationAlgorithm implements GenerationAlgorithm<Date> {
	
	
	@Override
	public Date generate(Field dateField) {
		return RandomUtil.getRandomDate(new Date(RandomUtil.getMinimumDate()),
				new Date(RandomUtil.getMaximumDate()), false);
	}

}
