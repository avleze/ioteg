package com.ioteg.generators.floatfield;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.ioteg.generators.GenerationAlgorithm;
import com.ioteg.generators.FieldGenerator;
import com.ioteg.model.Field;
import com.ioteg.resultmodel.ResultField;
import com.ioteg.resultmodel.ResultSimpleField;

public class FloatGenerator extends FieldGenerator<Float> {

	public FloatGenerator(GenerationAlgorithm<Float> generationAlgorithm, Field field) {
		super(generationAlgorithm, field);
	}

	@Override
	public List<ResultField> generate(Integer numberOfRequiredItems) {
		List<ResultField> results = new ArrayList<>();

		if (field.getPrecision() != null)
			for (int i = 0; i < numberOfRequiredItems; ++i)
			{
				Float result = generationAlgorithm.generate();
				results.add(new ResultSimpleField(field.getName(), numberToSpecifiedPrecision(result, field.getPrecision())));
			}
		else
			results = super.generate(numberOfRequiredItems);

		return results;
	}

	private String numberToSpecifiedPrecision(Float floatNumber, Integer precision) {
		String format = "%." + precision + "f";
		return String.format(Locale.US, format, floatNumber);
	}

}
