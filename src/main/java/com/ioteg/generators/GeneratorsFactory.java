package com.ioteg.generators;


import com.ioteg.generators.block.BlockGenerator;
import com.ioteg.generators.exceptions.NotExistingGeneratorException;

import java.util.List;

import com.ioteg.Trio;
import com.ioteg.exprlang.ExprParser.ExprLangParsingException;
import com.ioteg.generators.block.BlockGenerationAlgorithm;
import com.ioteg.model.Block;
import com.ioteg.model.Field;

public class GeneratorsFactory {

	private GeneratorsFactory() {
		throw new IllegalStateException("This is an utility class and can't be instantiated.");
	}

	public static Generable makeGenerator(Field field, Integer totalNumEvent) throws NotExistingGeneratorException, ExprLangParsingException {
		return NormalGeneratorsFactory.makeGenerator(field, totalNumEvent);
	}
	
	public static Generable makeQueryRestrictionGenerator(Field field, List<Trio<String, String, String>> restrictions) throws NotExistingGeneratorException {
		return QueryRestrictionGeneratorsFactory.makeGenerator(field, restrictions);
	}
	
	public static BlockGenerator makeBlockGenerator(Block block) throws NotExistingGeneratorException, ExprLangParsingException {
		return new BlockGenerator(new BlockGenerationAlgorithm(block));

	}

	


}
