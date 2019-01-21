package com.ioteg.generators.integer;
import com.ioteg.model.Field;

public class RandomIntegerGenerator implements IntegerGenerable {

	@Override
	public Integer generate(Field integer) {
		Integer min = integer.getMin().intValue();
		Integer max = integer.getMax().intValue();

		Integer result = min + (int) (Math.random() * ((max - min)));

		return result;
	}

}
