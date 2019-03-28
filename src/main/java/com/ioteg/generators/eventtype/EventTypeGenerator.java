package com.ioteg.generators.eventtype;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import com.ioteg.exprlang.ExprParser.ExprLangParsingException;
import com.ioteg.generators.AbstractGenerationAlgorithm;
import com.ioteg.generators.AbstractGenerator;
import com.ioteg.generators.context.GenerationContext;
import com.ioteg.generators.exceptions.NotExistingGeneratorException;
import com.ioteg.resultmodel.ResultEvent;


public class EventTypeGenerator extends AbstractGenerator<ResultEvent> {


	public EventTypeGenerator(AbstractGenerationAlgorithm<ResultEvent> generationAlgorithm, GenerationContext generationContext) {
		super(generationAlgorithm, generationContext);
	}

	public List<ResultEvent> generate(Integer numberOfRequiredItems) throws NotExistingGeneratorException, ExprLangParsingException, ParseException {
		List<ResultEvent> results = new ArrayList<>();

		for (int i = 0; i < numberOfRequiredItems; ++i)
			results.add(generationAlgorithm.generate());

		return results;
	}

}
