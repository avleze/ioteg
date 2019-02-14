package com.ioteg.generators;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

import org.apache.log4j.Logger;


public abstract class AbstractGenerationAlgorithm<T> {
	protected static Logger logger;
	protected static Random r;

	static {
		logger = Logger.getRootLogger();
		try {
			r = SecureRandom.getInstanceStrong();
		} catch (NoSuchAlgorithmException e) {
			logger.error(e);
		}
	}
	
	public abstract T generate();
}
