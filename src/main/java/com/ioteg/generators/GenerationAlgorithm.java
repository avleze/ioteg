package com.ioteg.generators;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

import org.apache.log4j.Logger;

import com.ioteg.model.Field;

public abstract class GenerationAlgorithm<T> {
	protected static Logger logger;
	protected static Random r;
	protected Field field;
	
	public GenerationAlgorithm(Field field) {
		super();
		this.field = field;
	}

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
