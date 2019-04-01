package com.ioteg.generators.stringfield;

import java.util.List;

import com.ioteg.RandomUtil;
import com.ioteg.eplutils.Trio;
import com.ioteg.generators.QueryRestrictionGenerationAlgorithm;
import com.ioteg.generators.context.GenerationContext;
import com.ioteg.model.Field;

/**
 * <p>StringQueryRestrictionGenerationAlgorithm class.</p>
 *
 * @author antonio
 * @version $Id: $Id
 */
public class StringQueryRestrictionGenerationAlgorithm extends QueryRestrictionGenerationAlgorithm<String> {

	protected String possibleChars;
	
	/**
	 * <p>Constructor for StringQueryRestrictionGenerationAlgorithm.</p>
	 *
	 * @param field a {@link com.ioteg.model.Field} object.
	 * @param generationContext a {@link com.ioteg.generators.context.GenerationContext} object.
	 * @param restrictions a {@link java.util.List} object.
	 * @param possibleChars a {@link java.lang.String} object.
	 */
	public StringQueryRestrictionGenerationAlgorithm(Field field, GenerationContext generationContext, List<Trio<String, String, String>> restrictions, String possibleChars) {
		super(field, generationContext, restrictions);
		this.possibleChars = possibleChars;
	}

	/** {@inheritDoc} */
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
