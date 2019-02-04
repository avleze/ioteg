package com.ioteg.generators.integerfield;

import java.util.List;

import com.ioteg.Trio;
import com.ioteg.generators.QueryRestrictionGenerationAlgorithm;
import com.ioteg.model.Field;

/**
 * @author antonio
 *
 */
public class IntegerQueryRestrictionGenerationAlgorithm extends QueryRestrictionGenerationAlgorithm<Integer> {

	public IntegerQueryRestrictionGenerationAlgorithm(Field field, List<Trio<String, String, String>> restrictions) {
		super(field, restrictions);
	}

	@Override
	public Integer generate() {
		Trio<String, String, String> fieldRestrictionInformation = restrictions.get(0);

		String operator = fieldRestrictionInformation.getSecond();
		Integer value = Integer.valueOf(fieldRestrictionInformation.getThird());

		Integer result = null;

		Integer max = Integer.MAX_VALUE;
		Integer min = Integer.MIN_VALUE;

		if (field.getMax() != null)
			max = field.getMax().intValue();

		if (field.getMin() != null)
			min = field.getMin().intValue();

		if (operator.equals("!="))
			result = generateNotEqualValue(value, max, min);
		else if (operator.equals("="))
			result = value;
		else {
			if (operator.equals(">"))
				min = value + 1;
			else if (operator.equals(">="))
				min = value;
			else if (operator.equals("<"))
				max = value - 1;
			else if (operator.equals("<="))
				max = value;

			result = r.ints(min, max).findFirst().getAsInt();
		}

		return result;
	}

	/**
	 * @param value The value that the generated one has to be different.
	 * @param max   The upper bound of the generated value.
	 * @param min   The lower bound of the generated value.
	 * @return A number between min and max and different than value.
	 */
	private Integer generateNotEqualValue(Integer value, Integer max, Integer min) {
		Integer result;
		do {
			result = r.ints(min, max).findFirst().getAsInt();
		} while (result.equals(value));

		return result;
	}
}
