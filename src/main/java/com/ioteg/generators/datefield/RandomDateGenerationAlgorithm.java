package com.ioteg.generators.datefield;
import java.util.Date;


import com.ioteg.RandomUtil;
import com.ioteg.generators.GenerationAlgorithm;
import com.ioteg.generators.context.GenerationContext;
import com.ioteg.model.Field;

public class RandomDateGenerationAlgorithm extends GenerationAlgorithm<Date> {
	
	public RandomDateGenerationAlgorithm(Field field, GenerationContext generationContext) {
		super(field, generationContext);
	}

	@Override
	public Date generate() {
		return RandomUtil.getRandomDate(new Date(RandomUtil.getMinimumDate()),
				new Date(RandomUtil.getMaximumDate()), false);
	}

}
