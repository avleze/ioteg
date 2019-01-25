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
		
		String result = null;
		if (stringField.getEndcharacter() != null) {
			result = getRandStringRange(stringField.getLength(),
					stringField.getEndcharacter());
		} else {
			result = getRandString(stringField.getLength());
		}

		return result;
	}
	
	/**
	 * Generate a random String
	 * 
	 * @param longt determines the length of the String
	 * @return a random String with a long length
	 */
	private static String getRandString(int longt) {
		String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		StringBuilder salt = new StringBuilder();
		
		while (salt.length() < longt) {
			int index = (int) (r.nextFloat() * SALTCHARS.length());
			salt.append(SALTCHARS.charAt(index));
		}
		String saltStr = salt.toString();
		return saltStr;

	}

	/**
	 * Generate a random String
	 * 
	 * @param longt determines the length of the String and
	 * @param end   determines the last character for the range
	 * @return a random String with a long length
	 */
	private static String getRandStringRange(int longt, String end) {
		String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
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
