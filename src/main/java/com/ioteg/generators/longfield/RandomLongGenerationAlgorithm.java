package com.ioteg.generators.longfield;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

import org.apache.log4j.Logger;

import com.ioteg.generators.GenerationAlgorithm;
import com.ioteg.model.Field;

public class RandomLongGenerationAlgorithm implements GenerationAlgorithm<Long> {
	
	private Logger logger = Logger.getRootLogger();
	
	@Override
	public Long generate(Field longField) {
		Long min = longField.getMin().longValue();
		Long max = longField.getMax().longValue();
		Long result = null;
		
		try {
			Random r = SecureRandom.getInstanceStrong();
			result = r.longs(min, max).findFirst().getAsLong();
		} catch (NoSuchAlgorithmException e) {
			logger.error(e);
		}  

		return result;
	}

}
