package com.ioteg.generators.booleanfield;

import java.util.ArrayList;
import java.util.List;

import com.ioteg.generators.GenerationAlgorithm;
import com.ioteg.generators.Generator;
import com.ioteg.model.Field;

public class BooleanGenerator extends Generator<Boolean>{

	public BooleanGenerator(GenerationAlgorithm<Boolean> generationAlgorithm) {
		super(generationAlgorithm);
	}

	@Override
	public List<String> generate(Field field, Integer numberOfRequiredItems) {
		List<String> results = new ArrayList<>();
		
		if(field.getIsNumeric())
			for (int i = 0; i < numberOfRequiredItems; ++i)
				results.add(booleanToNumericalString(generationAlgorithm.generate()));
		else
			results = super.generate(field, numberOfRequiredItems);
		
		return results;
	}

	private String booleanToNumericalString(Boolean b) {
		return b.booleanValue() ? "1" : "0";
	}
}
