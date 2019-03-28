package com.ioteg.generators.stringfield;

import java.util.List;

import com.ioteg.RandomUtil;
import com.ioteg.eplutils.Trio;
import com.ioteg.generators.QueryRestrictionGenerationAlgorithm;
import com.ioteg.generators.context.GenerationContext;
import com.ioteg.model.Field;

public class StringQueryRestrictionGenerationAlgorithm extends QueryRestrictionGenerationAlgorithm<String> {

	protected String possibleChars;
	
	public StringQueryRestrictionGenerationAlgorithm(Field field, GenerationContext generationContext, List<Trio<String, String, String>> restrictions, String possibleChars) {
		super(field, generationContext, restrictions);
		this.possibleChars = possibleChars;
	}

	@Override
	public String generate() {
		Trio<String, String, String> fieldRestrictionInformation = restrictions.get(0);

		String operator = fieldRestrictionInformation.getSecond();
		String value = fieldRestrictionInformation.getThird();

		String result = null;

		if (operator.equals("="))
			result = value;
		else if (operator.equals("!=")) 
			result = generateNotEqualValue(field, value);
		

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
			result = RandomUtil.getRandStringRange(field.getLength(), field.getEndcharacter(), possibleChars);
		} while (result.equals(value));
		
		return result;
	}
}
