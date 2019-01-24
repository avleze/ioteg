package com.ioteg.generators.floatfield;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.ioteg.generators.GenerationAlgorithm;
import com.ioteg.generators.Generator;
import com.ioteg.model.Field;

public class FloatGenerator extends Generator<Float> {

	public FloatGenerator(GenerationAlgorithm<Float> generationAlgorithm) {
		super(generationAlgorithm);
	}

	@Override
	public List<String> generate(Field floatField, Integer numberOfRequiredItems) {
		List<String> results = new ArrayList<>();

		if (floatField.getPrecision() != null)
			for (int i = 0; i < numberOfRequiredItems; ++i)
			{
				Float result = generationAlgorithm.generate(floatField);
				results.add(numberToSpecifiedPrecision(result, floatField.getPrecision()));
			}
		else
			results = super.generate(floatField, numberOfRequiredItems);

		return results;
	}

	private String numberToSpecifiedPrecision(Float floatNumber, Integer precision) {
		return String.format(Locale.US, "%." + precision + "f", Float.valueOf(floatNumber));
	}

}
