package com.ioteg.generators;

import java.util.ArrayList;
import java.util.List;
import com.ioteg.model.Field;

public class Generator<T> {
	
	protected GenerationAlgorithm<T> generationAlgorithm;

	
	public Generator(GenerationAlgorithm<T> generationAlgorithm) {
		super();
		this.generationAlgorithm = generationAlgorithm;
	}


	public List<String> generate(Field field, Integer numberOfRequiredItems) {
		List<String> results = new ArrayList<>();

		for (int i = 0; i < numberOfRequiredItems; ++i)
			results.add(generationAlgorithm.generate(field).toString());
		
		return results;
	}
}
