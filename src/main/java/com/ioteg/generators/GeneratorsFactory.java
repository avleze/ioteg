package com.ioteg.generators;


import com.ioteg.generators.block.BlockGenerator;
import com.ioteg.generators.context.GenerationContext;
import com.ioteg.generators.exceptions.NotExistingGeneratorException;

import java.text.ParseException;
import java.util.List;

import com.ioteg.eplutils.Trio;
import com.ioteg.exprlang.ExprParser.ExprLangParsingException;
import com.ioteg.generators.block.BlockGenerationAlgorithm;
import com.ioteg.model.Block;
import com.ioteg.model.Field;

public class GeneratorsFactory {

	private GeneratorsFactory() {
		throw new IllegalStateException("This is an utility class and can't be instantiated.");
	}

	public static Generable makeGenerator(Field field, Integer totalNumEvent, GenerationContext generationContext) throws NotExistingGeneratorException, ExprLangParsingException, ParseException {
		return NormalGeneratorsFactory.makeGenerator(field, totalNumEvent, generationContext);
	}
	
	public static Generable makeQueryRestrictionGenerator(Field field, List<Trio<String, String, String>> restrictions, GenerationContext generationContext) throws NotExistingGeneratorException {
		return QueryRestrictionGeneratorsFactory.makeGenerator(field, restrictions, generationContext);
	}
	
	public static BlockGenerator makeBlockGenerator(Block block, GenerationContext generationContext) throws NotExistingGeneratorException, ExprLangParsingException, ParseException {
		return new BlockGenerator(new BlockGenerationAlgorithm(block, generationContext), generationContext);

	}

	


}
