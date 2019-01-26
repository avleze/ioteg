package com.ioteg.generators.stringfield;

import com.ioteg.generators.GenerationAlgorithm;
import com.ioteg.model.Field;

public class RandomStringGenerationAlgorithm extends GenerationAlgorithm<String> {

	@Override
	public String generate(Field stringField) {
		return getRandStringRange(stringField.getLength(), stringField.getEndcharacter());
	}

	/**
	 * Generate a random String
	 * 
	 * @param length determines the length of the String and
	 * @param endcharacter   determines the last character for the range
	 * @return a random String with a long length
	 */
	private static String getRandStringRange(int length, String endcharacter) {
		final String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		StringBuilder result = new StringBuilder();
		int lastPossibleIndex =  endcharacter == null ? SALTCHARS.length() : SALTCHARS.indexOf(endcharacter);
		while (result.length() < length) {
			int index = r.nextInt(lastPossibleIndex);
			result.append(SALTCHARS.charAt(index));
		}
		
		return result.toString();
	}

}
