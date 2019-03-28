package com.ioteg.generators.block;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import com.ioteg.exprlang.ExprParser.ExprLangParsingException;
import com.ioteg.generators.AbstractGenerationAlgorithm;
import com.ioteg.generators.AbstractGenerator;
import com.ioteg.generators.context.GenerationContext;
import com.ioteg.generators.exceptions.NotExistingGeneratorException;
import com.ioteg.resultmodel.ResultBlock;

public class BlockGenerator extends AbstractGenerator<ResultBlock> {

	public BlockGenerator(AbstractGenerationAlgorithm<ResultBlock> generationAlgorithm,
			GenerationContext generationContext) {
		super(generationAlgorithm, generationContext);
	}

	public List<ResultBlock> generate(Integer numberOfRequiredItems)
			throws NotExistingGeneratorException, ExprLangParsingException, ParseException {
		List<ResultBlock> results = new ArrayList<>();

		for (int i = 0; i < numberOfRequiredItems; ++i)
			results.add(generationAlgorithm.generate());

		return results;
	}
}
