package com.ioteg.generators.integer;

import java.util.ArrayList;
import java.util.List;

import com.ioteg.model.Field;

public class IntegerGenerator {

	private IntegerGenerable integerGenerator;

	public IntegerGenerator(IntegerGenerable integerGenerator) {
		this.integerGenerator = integerGenerator;
	}

	public List<String> generate(Field integer, Integer numberOfIntegers) {
		List<String> integers = new ArrayList<String>();
		
		for (int i = 0; i < numberOfIntegers; ++i) 
			integers.add(integerGenerator.generate(integer).toString());
		
		return integers;

	}

}
