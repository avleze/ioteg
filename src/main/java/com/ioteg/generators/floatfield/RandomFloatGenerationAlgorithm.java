package com.ioteg.generators.floatfield;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

import org.apache.log4j.Logger;

import com.ioteg.generators.GenerationAlgorithm;
import com.ioteg.model.Field;

public class RandomFloatGenerationAlgorithm implements GenerationAlgorithm<Float> {
	
	private Logger logger = Logger.getRootLogger();
	
	@Override
	public Float generate(Field floatField) {
		Double min = floatField.getMin();
		Double max = floatField.getMax();
		Float result = null;
		
		try {
			Random r = SecureRandom.getInstanceStrong();
			result = (float) r.doubles(min, max).findFirst().getAsDouble();
		} catch (NoSuchAlgorithmException e) {
			logger.error(e);
		}  

		return result;
	}

}
