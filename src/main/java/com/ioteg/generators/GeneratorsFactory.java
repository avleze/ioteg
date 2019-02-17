package com.ioteg.generators;


import com.ioteg.generators.block.BlockGenerator;
import com.ioteg.generators.exceptions.NotExistingGeneratorException;

import java.io.IOException;
import java.util.List;

import com.ioteg.Trio;
import com.ioteg.generators.block.BlockGenerationAlgorithm;
import com.ioteg.model.Block;
import com.ioteg.model.Field;

public class GeneratorsFactory {

	private GeneratorsFactory() {
		throw new IllegalStateException("This is an utility class and can't be instantiated.");
	}

	public static Generable makeGenerator(Field field, Integer totalNumEvent) throws NotExistingGeneratorException, IOException {
		return NormalGeneratorsFactory.makeGenerator(field, totalNumEvent);
	}
	
	public static Generable makeQueryRestrictionGenerator(Field field, List<Trio<String, String, String>> restrictions) throws NotExistingGeneratorException, IOException {
		return QueryRestrictionGeneratorsFactory.makeGenerator(field, restrictions);
	}
	
	public static BlockGenerator makeBlockGenerator(Block block) throws NotExistingGeneratorException, IOException {
		return new BlockGenerator(new BlockGenerationAlgorithm(block));

	}

	


}
