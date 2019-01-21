package com.ioteg.generators.integer;
import java.util.Random;

import com.ioteg.model.Field;

public class RandomIntegerGenerator implements IntegerGenerable {

	@Override
	public Integer generate(Field integer) {
		Integer min = integer.getMin().intValue();
		Integer max = integer.getMax().intValue();
		Random r = new Random();
		Integer result = min + r.nextInt(max - min);

		return result;
	}

}
