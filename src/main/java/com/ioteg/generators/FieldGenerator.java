package com.ioteg.generators;

import java.util.ArrayList;
import java.util.List;
import com.ioteg.model.Field;
import com.ioteg.resultmodel.ResultField;
import com.ioteg.resultmodel.ResultSimpleField;

public class FieldGenerator<T> implements Generable{

	protected GenerationAlgorithm<T> generationAlgorithm;
	protected Field field;
	
	public FieldGenerator(GenerationAlgorithm<T> generationAlgorithm, Field field) {
		this.generationAlgorithm = generationAlgorithm;
		this.field = field;
	}

	public List<ResultField> generate(Integer numberOfRequiredItems) {
		List<ResultField> results = new ArrayList<>();

		for (int i = 0; i < numberOfRequiredItems; ++i)
			results.add(new ResultSimpleField(field.getName(), generationAlgorithm.generate().toString()));

		return results;
	}
}
