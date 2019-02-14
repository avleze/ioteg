package com.ioteg.generators.block;

import java.util.ArrayList;
import java.util.List;


import com.ioteg.generators.AbstractGenerator;
import com.ioteg.resultmodel.ResultBlock;


public class BlockGenerator extends AbstractGenerator<ResultBlock> {


	public BlockGenerator(BlockGenerationAlgorithm blockGenerationAlgorithm) {
		super(blockGenerationAlgorithm);
	}
		
	public List<ResultBlock> generate(Integer numberOfRequiredItems) {
		List<ResultBlock> results = new ArrayList<>();

		for (int i = 0; i < numberOfRequiredItems; ++i)
			results.add(generationAlgorithm.generate());

		return results;
	}
}
