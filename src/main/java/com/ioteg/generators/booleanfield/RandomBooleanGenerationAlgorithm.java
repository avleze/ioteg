package com.ioteg.generators.booleanfield;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

import org.apache.log4j.Logger;

import com.ioteg.generators.GenerationAlgorithm;
import com.ioteg.model.Field;

public class RandomBooleanGenerationAlgorithm implements GenerationAlgorithm<Boolean> {
	
	private Logger logger = Logger.getRootLogger();
	
	@Override
	public Boolean generate(Field booleanField) {
		Boolean result = null;
		
		try {
			Random r = SecureRandom.getInstanceStrong();
			result = r.nextBoolean();
		} catch (NoSuchAlgorithmException e) {
			logger.error(e);
		}  

		return result;
	}

}
