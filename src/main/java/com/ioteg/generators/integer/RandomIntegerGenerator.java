package com.ioteg.generators.integer;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

import org.apache.log4j.Logger;

import com.ioteg.model.Field;

public class RandomIntegerGenerator implements IntegerGenerable {
	
	private Logger logger = Logger.getRootLogger();
	
	@Override
	public Integer generate(Field integer) {
		Integer min = integer.getMin().intValue();
		Integer max = integer.getMax().intValue();
		Integer result = null;
		
		try {
			Random r = SecureRandom.getInstanceStrong();
			result = min + r.nextInt(max - min);
		} catch (NoSuchAlgorithmException e) {
			logger.error(e);
		}  

		return result;
	}

}
