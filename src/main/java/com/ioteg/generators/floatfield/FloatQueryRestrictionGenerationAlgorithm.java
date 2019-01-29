package com.ioteg.generators.floatfield;

import java.util.List;

import com.ioteg.Trio;
import com.ioteg.generators.QueryRestrictionGenerationAlgorithm;
import com.ioteg.model.Field;

/**
 * @author antonio
 *
 */
public class FloatQueryRestrictionGenerationAlgorithm extends QueryRestrictionGenerationAlgorithm<Float> {

	public FloatQueryRestrictionGenerationAlgorithm(List<Trio<String, String, String>> restrictions) {
		super(restrictions);
	}

	@Override
	public Float generate(Field floatField) {
		Trio<String, String, String> fieldRestrictionInformation = restrictions.get(0);

		String operator = fieldRestrictionInformation.getSecond();
		Float value = Float.valueOf(fieldRestrictionInformation.getThird());

		Float result = null;

		Float max = Float.MAX_VALUE;
		Float min = -Float.MAX_VALUE;

		if (floatField.getMax() != null)
			max = floatField.getMax().floatValue();

		if (floatField.getMin() != null)
			min = floatField.getMin().floatValue();

		if (operator.equals("!="))
			result = generateNotEqualValue(value, max, min);
		else if (operator.equals("="))
			result = value;
		else {
			if (operator.equals(">"))
				min = value + 1f;
			else if (operator.equals(">="))
				min = value;
			else if (operator.equals("<"))
				max = value - 1f;
			else if (operator.equals("<="))
				max = value;

			result = (float) r.doubles(min, max).findFirst().getAsDouble();
		}

		return result;
	}

	/**
	 * @param value The value that the generated one has to be different.
	 * @param max   The upper bound of the generated value.
	 * @param min   The lower bound of the generated value.
	 * @return A number between min and max and different than value.
	 */
	private Float generateNotEqualValue(Float value, float max, float min) {
		Float result;
		do {
			result = (float) r.doubles(min, max).findFirst().getAsDouble();
		} while (result.equals(value));

		return result;
	}
}
