package com.ioteg.generators.integer;

import java.util.ArrayList;
import java.util.List;

import com.ioteg.model.Field;

public class IntegerGenerator {

	private IntegerGenerable generationAlgorithm;

	public IntegerGenerator(IntegerGenerable integerGenerator) {
		this.generationAlgorithm = integerGenerator;
	}

	public List<String> generate(Field integer, Integer numberOfIntegers) {
		List<String> integers = new ArrayList<>();
		
		for (int i = 0; i < numberOfIntegers; ++i) 
			integers.add(generationAlgorithm.generate(integer).toString());
		
		return integers;

	}

}
