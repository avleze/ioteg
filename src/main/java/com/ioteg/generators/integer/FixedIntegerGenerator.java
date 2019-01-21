package com.ioteg.generators.integer;

import com.ioteg.model.Field;

public class FixedIntegerGenerator implements IntegerGenerable {

	@Override
	public Integer generate(Field integer) {
		return Integer.valueOf(integer.getValue());
	}

}
