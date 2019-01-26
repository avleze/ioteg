package com.ioteg.generators.stringfield;

import com.ioteg.generators.GenerationAlgorithm;
import com.ioteg.model.Field;

public class RandomAlphanumericalGenerationAlgorithm extends GenerationAlgorithm<String> {

	@Override
	public String generate(Field stringField) {
		return getAlphaNumRandStringRange(stringField.getLength(), stringField.getEndcharacter());
	}
	
	/**
	 * Generate a random Alphanumeric String
	 * 
	 * @param length determines the length of the String and
	 * @param endcharacter   determines the last character for the range
	 * @return a random Alphanumeric String with a long length
	 */
	private static String getAlphaNumRandStringRange(int length, String endcharacter) {
		final String SALTCHARS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		StringBuilder result = new StringBuilder();
		int lastPossibleIndex =  endcharacter == null ? SALTCHARS.length() : SALTCHARS.indexOf(endcharacter);
		while (result.length() < length) {
			int index = r.nextInt(lastPossibleIndex);
			result.append(SALTCHARS.charAt(index));
		}
		
		return result.toString();
	}

}
