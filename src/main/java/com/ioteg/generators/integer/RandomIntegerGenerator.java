package com.ioteg.generators.integer;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

import com.ioteg.model.Field;

public class RandomIntegerGenerator implements IntegerGenerable {
	

	@Override
	public Integer generate(Field integer) {
		Integer min = integer.getMin().intValue();
		Integer max = integer.getMax().intValue();
		Integer result = null;
		Random r;
		try {
			r = SecureRandom.getInstanceStrong();
			result = min + r.nextInt(max - min);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}  

		return result;
	}

}
