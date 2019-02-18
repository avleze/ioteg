package com.ioteg.generators.block;

import java.util.ArrayList;
import java.util.List;

import com.ioteg.exprlang.ExprParser.ExprLangParsingException;
import com.ioteg.generators.AbstractGenerator;
import com.ioteg.generators.exceptions.NotExistingGeneratorException;
import com.ioteg.resultmodel.ResultBlock;


public class BlockGenerator extends AbstractGenerator<ResultBlock> {


	public BlockGenerator(BlockGenerationAlgorithm blockGenerationAlgorithm) {
		super(blockGenerationAlgorithm);
	}
		
	public List<ResultBlock> generate(Integer numberOfRequiredItems) throws NotExistingGeneratorException, ExprLangParsingException {
		List<ResultBlock> results = new ArrayList<>();

		for (int i = 0; i < numberOfRequiredItems; ++i)
			results.add(generationAlgorithm.generate());

		return results;
	}
}
