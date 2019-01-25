package com.ioteg.generators.stringfield;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

import org.apache.log4j.Logger;

import com.ioteg.generators.GenerationAlgorithm;
import com.ioteg.model.Field;

public class RandomStringGenerationAlgorithm implements GenerationAlgorithm<String> {
	
	private static Logger logger;
	private static Random r;

	static {
		logger = Logger.getRootLogger();
		try {
			r = SecureRandom.getInstanceStrong();
		} catch (NoSuchAlgorithmException e) {
			logger.error(e);
		}
	}

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
