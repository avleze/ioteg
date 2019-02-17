package com.ioteg.generators.booleanfield;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.ioteg.generators.GenerationAlgorithm;
import com.ioteg.generators.exceptions.NotExistingGeneratorException;
import com.ioteg.generators.FieldGenerator;
import com.ioteg.model.Field;
import com.ioteg.resultmodel.ResultField;
import com.ioteg.resultmodel.ResultSimpleField;

public class BooleanGenerator extends FieldGenerator<Boolean> {

	public BooleanGenerator(GenerationAlgorithm<Boolean> generationAlgorithm, Field field) {
		super(generationAlgorithm, field);
	}

	@Override
	public List<ResultField> generate(Integer numberOfRequiredItems) throws NotExistingGeneratorException, IOException {
		List<ResultField> results = new ArrayList<>();

		if (field.getIsNumeric())
			for (int i = 0; i < numberOfRequiredItems; ++i)
				results.add(new ResultSimpleField(field.getName(), field.getType(), field.getQuotes(),
						booleanToNumericalString(generationAlgorithm.generate())));
		else
			results = super.generate(numberOfRequiredItems);

		return results;
	}

	private String booleanToNumericalString(Boolean b) {
		return b.booleanValue() ? "1" : "0";
	}
}
