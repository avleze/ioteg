package com.ioteg.generation;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import com.ioteg.exprlang.ExprParser.ExprLangParsingException;
import com.ioteg.resultmodel.ResultEvent;


/**
 * <p>EventTypeGenerator class.</p>
 *
 * @author antonio
 * @version $Id: $Id
 */
public class EventTypeGenerator extends AbstractGenerator<ResultEvent> {


	/**
	 * <p>Constructor for EventTypeGenerator.</p>
	 *
	 * @param generationAlgorithm a {@link com.ioteg.generation.AbstractGenerationAlgorithm} object.
	 * @param generationContext a {@link com.ioteg.generation.GenerationContext} object.
	 */
	public EventTypeGenerator(AbstractGenerationAlgorithm<ResultEvent> generationAlgorithm, GenerationContext generationContext) {
		super(generationAlgorithm, generationContext);
	}

	/**
	 * <p>generate.</p>
	 *
	 * @param numberOfRequiredItems a {@link java.lang.Integer} object.
	 * @return a {@link java.util.List} object.
	 * @throws com.ioteg.generation.NotExistingGeneratorException if any.
	 * @throws com.ioteg.exprlang.ExprParser.ExprLangParsingException if any.
	 * @throws java.text.ParseException if any.
	 */
	public List<ResultEvent> generate(Integer numberOfRequiredItems) throws NotExistingGeneratorException, ExprLangParsingException, ParseException {
		List<ResultEvent> results = new ArrayList<>();

		for (int i = 0; i < numberOfRequiredItems; ++i)
			results.add(generationAlgorithm.generate());

		return results;
	}

}
