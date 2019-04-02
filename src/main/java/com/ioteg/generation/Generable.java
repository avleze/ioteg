package com.ioteg.generation;

import java.text.ParseException;
import java.util.List;

import com.ioteg.exprlang.ExprParser.ExprLangParsingException;
import com.ioteg.resultmodel.ResultField;

/**
 * <p>Generable interface.</p>
 *
 * @author antonio
 * @version $Id: $Id
 */
public interface Generable {
	/**
	 * <p>generate.</p>
	 *
	 * @param numberOfRequiredItems a {@link java.lang.Integer} object.
	 * @return a {@link java.util.List} object.
	 * @throws com.ioteg.generation.NotExistingGeneratorException if any.
	 * @throws com.ioteg.exprlang.ExprParser.ExprLangParsingException if any.
	 * @throws java.text.ParseException if any.
	 */
	public List<ResultField> generate(Integer numberOfRequiredItems) throws NotExistingGeneratorException, ExprLangParsingException, ParseException;
}
