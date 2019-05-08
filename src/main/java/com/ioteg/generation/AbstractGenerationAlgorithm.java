package com.ioteg.generation;

import java.text.ParseException;
import java.util.Random;

import org.apache.log4j.Logger;

import com.ioteg.exprlang.ExprParser.ExprLangParsingException;

/**
 * <p>
 * Abstract AbstractGenerationAlgorithm class.
 * </p>
 *
 * @author antonio
 * @version $Id: $Id
 */
public abstract class AbstractGenerationAlgorithm<T> {
	/** Constant <code>logger</code> */
	protected static Logger logger;
	/** Constant <code>r</code> */
	protected static Random r;

	static {
		logger = Logger.getRootLogger();
		r = new Random();
	}

	/**
	 * <p>
	 * generate.
	 * </p>
	 *
	 * @return a T object.
	 * @throws com.ioteg.generation.NotExistingGeneratorException     if any.
	 * @throws com.ioteg.exprlang.ExprParser.ExprLangParsingException if any.
	 * @throws java.text.ParseException                               if any.
	 */
	public abstract T generate() throws NotExistingGeneratorException, ExprLangParsingException, ParseException;
}
