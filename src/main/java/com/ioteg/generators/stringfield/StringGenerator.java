package com.ioteg.generators.stringfield;

import java.util.ArrayList;
import java.util.List;

import com.ioteg.generators.GenerationAlgorithm;
import com.ioteg.generators.Generator;
import com.ioteg.model.Field;

public class StringGenerator extends Generator<String>{

	public StringGenerator(GenerationAlgorithm<String> generationAlgorithm) {
		super(generationAlgorithm);
	}

	@Override
	public List<String> generate(Field stringField, Integer numberOfRequiredItems) {
		List<String> results = new ArrayList<>();
		String caseStr = stringField.getCase();
		
		if (caseStr != null && caseStr.equalsIgnoreCase("low")) 
			for (int i = 0; i < numberOfRequiredItems; ++i)
				results.add(generationAlgorithm.generate(stringField).toLowerCase());
		else
			results = super.generate(stringField, numberOfRequiredItems);
		
		return results;
	}

	
}
