package com.ioteg.generators;

import java.util.ArrayList;
import java.util.List;
import com.ioteg.model.Field;
import com.ioteg.resultmodel.ResultField;
import com.ioteg.resultmodel.ResultSimpleField;

public class FieldGenerator<T> extends AbstractGenerator<T> implements Generable{

	protected Field field;
	
	public FieldGenerator(GenerationAlgorithm<T> generationAlgorithm, Field field) {
		super(generationAlgorithm);
		this.field = field;
	}

	public List<ResultField> generate(Integer numberOfRequiredItems) {
		List<ResultField> results = new ArrayList<>();

		for (int i = 0; i < numberOfRequiredItems; ++i)
			results.add(new ResultSimpleField(field.getName(), field.getType(), field.getQuotes(), generationAlgorithm.generate().toString()));

		return results;
	}
}
