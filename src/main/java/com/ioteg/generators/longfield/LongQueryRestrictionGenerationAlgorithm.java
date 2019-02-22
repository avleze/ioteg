package com.ioteg.generators.longfield;

import java.util.List;

import com.ioteg.Trio;
import com.ioteg.generators.QueryRestrictionGenerationAlgorithm;
import com.ioteg.model.Field;

/**
 * @author antonio
 *
 */
public class LongQueryRestrictionGenerationAlgorithm extends QueryRestrictionGenerationAlgorithm<Long> {



	public LongQueryRestrictionGenerationAlgorithm(Field field, List<Trio<String, String, String>> restrictions) {
		super(field, restrictions);
	}

	@Override
	public Long generate() {
		Trio<String, String, String> fieldRestrictionInformation = restrictions.get(0);

		String operator = fieldRestrictionInformation.getSecond();
		Long value = Long.valueOf(fieldRestrictionInformation.getThird());

		Long result = null;

		Long max = Long.MAX_VALUE;
		Long min = Long.MIN_VALUE;
		
		if(field.getType().equalsIgnoreCase("Integer"))
		{
			max = (long) Integer.MAX_VALUE;
			min = (long) Integer.MIN_VALUE;
		} 

		if (field.getMax() != null)
			max = field.getMax().longValue();

		if (field.getMin() != null)
			min = field.getMin().longValue();

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

			if(min != max)
				result = r.longs(min, max).findFirst().getAsLong();
			else
				result = min;
		}

		return result;
	}

	/**
	 * @param value The value that the generated one has to be different.
	 * @param max   The upper bound of the generated value.
	 * @param min   The lower bound of the generated value.
	 * @return A number between min and max and different than value.
	 */
	private Long generateNotEqualValue(Long value, Long max, Long min) {
		Long result;
		do {
			result = r.longs(min, max).findFirst().getAsLong();
		} while (result.equals(value));

		return result;
	}
}
