package com.ioteg.generators.stringfield;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

import org.apache.log4j.Logger;

import com.ioteg.generators.GenerationAlgorithm;
import com.ioteg.model.Field;

public class RandomAlphanumericalGenerationAlgorithm implements GenerationAlgorithm<String> {
	
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
		
		String result = null;
		if (stringField.getEndcharacter() != null) {
			result = getAlphaNumRandStringRange(stringField.getLength(),
					stringField.getEndcharacter());
		} else {
			result = getAlphaNumRandString(stringField.getLength());
		}

		return result;
	}
	
	/**
	 * Generate a random Alphanumeric String
	 * 
	 * @param longt determines the length of the String
	 * @return a random String with a long length
	 */
	private static String getAlphaNumRandString(int longt) {
		String SALTCHARS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		StringBuilder salt = new StringBuilder();
		while (salt.length() < longt) {
			int index = (int) (r.nextFloat() * SALTCHARS.length());
			salt.append(SALTCHARS.charAt(index));
		}
		String saltStr = salt.toString();
		return saltStr;

	}

	/**
	 * Generate a random Alphanumeric String
	 * 
	 * @param longt determines the length of the String and
	 * @param end   determines the last character for the range
	 * @return a random String with a longt length
	 */
	private static String getAlphaNumRandStringRange(int longt, String end) {
		String SALTCHARS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		StringBuilder salt = new StringBuilder();
		int newlength = SALTCHARS.indexOf(end);
		while (salt.length() < longt) {
			int index = (int) (r.nextFloat() * newlength + 1);
			salt.append(SALTCHARS.charAt(index));
		}
		String saltStr = salt.toString();
		return saltStr;

	}

}
