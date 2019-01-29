package com.ioteg.generators.stringfield;

import java.util.List;

import com.ioteg.RandomUtil;
import com.ioteg.Trio;
import com.ioteg.generators.QueryRestrictionGenerationAlgorithm;
import com.ioteg.model.Field;

public class AlphanumericQueryRestrictionGenerationAlgorithm extends QueryRestrictionGenerationAlgorithm<String> {

	public AlphanumericQueryRestrictionGenerationAlgorithm(List<Trio<String, String, String>> restrictions) {
		super(restrictions);
	}

	@Override
	public String generate(Field alphanumericField) {
		Trio<String, String, String> fieldRestrictionInformation = restrictions.get(0);

		String operator = fieldRestrictionInformation.getSecond();
		String value = fieldRestrictionInformation.getThird();

		String result = null;

		if (operator.equals("="))
			result = value;
		else if (operator.equals("!=")) 
			result = generateNotEqualValue(alphanumericField, value);
		

		return result;
	}

	/**
	 * @param field The field that must have length and may have an end character.
	 * @param value The value that the generated one has to be different.
	 * @return A string different than value.
	 */
	private String generateNotEqualValue(Field field, String value) {
		String result;
		do {
			result = RandomUtil.getAlphaNumRandStringRange(field.getLength(), field.getEndcharacter());
		} while (result.equals(value));
		
		return result;
	}
}
