package com.ioteg.generation;


import java.text.ParseException;
import java.util.List;

import com.ioteg.eplutils.Trio;
import com.ioteg.exprlang.ExprParser.ExprLangParsingException;
import com.ioteg.model.Block;
import com.ioteg.model.EventType;
import com.ioteg.model.Field;

/**
 * <p>GeneratorsFactory class.</p>
 *
 * @author antonio
 * @version $Id: $Id
 */
public class GeneratorsFactory {

	private GeneratorsFactory() {
		throw new IllegalStateException("This is an utility class and can't be instantiated.");
	}

	/**
	 * <p>makeGenerator.</p>
	 *
	 * @param field a {@link com.ioteg.model.Field} object.
	 * @param totalNumEvent a {@link java.lang.Integer} object.
	 * @param generationContext a {@link com.ioteg.generation.GenerationContext} object.
	 * @return a {@link com.ioteg.generation.Generable} object.
	 * @throws com.ioteg.generation.NotExistingGeneratorException if any.
	 * @throws com.ioteg.exprlang.ExprParser.ExprLangParsingException if any.
	 * @throws java.text.ParseException if any.
	 */
	public static Generable makeGenerator(Field field, Integer totalNumEvent, GenerationContext generationContext) throws NotExistingGeneratorException, ExprLangParsingException, ParseException {
		return NormalGeneratorsFactory.makeGenerator(field, totalNumEvent, generationContext);
	}
	
	/**
	 * <p>makeQueryRestrictionGenerator.</p>
	 *
	 * @param field a {@link com.ioteg.model.Field} object.
	 * @param restrictions a {@link java.util.List} object.
	 * @param generationContext a {@link com.ioteg.generation.GenerationContext} object.
	 * @return a {@link com.ioteg.generation.Generable} object.
	 * @throws com.ioteg.generation.NotExistingGeneratorException if any.
	 */
	public static Generable makeQueryRestrictionGenerator(Field field, List<Trio<String, String, String>> restrictions, GenerationContext generationContext) throws NotExistingGeneratorException {
		return QueryRestrictionGeneratorsFactory.makeGenerator(field, restrictions, generationContext);
	}
	
	/**
	 * <p>makeBlockGenerator.</p>
	 *
	 * @param block a {@link com.ioteg.model.Block} object.
	 * @param generationContext a {@link com.ioteg.generation.GenerationContext} object.
	 * @return a {@link com.ioteg.generation.BlockGenerator} object.
	 * @throws com.ioteg.generation.NotExistingGeneratorException if any.
	 * @throws com.ioteg.exprlang.ExprParser.ExprLangParsingException if any.
	 * @throws java.text.ParseException if any.
	 */
	public static BlockGenerator makeBlockGenerator(Block block, GenerationContext generationContext) throws NotExistingGeneratorException, ExprLangParsingException, ParseException {
		return new BlockGenerator(new BlockGenerationAlgorithm(block, generationContext), generationContext);

	}
	
	/**
	 * <p>makeEventTypeGenerator.</p>
	 *
	 * @param eventType a {@link com.ioteg.model.EventType} object.
	 * @param context a {@link com.ioteg.generation.GenerationContext} object.
	 * @return a {@link com.ioteg.generation.EventTypeGenerator} object.
	 * @throws com.ioteg.generation.NotExistingGeneratorException if any.
	 * @throws com.ioteg.exprlang.ExprParser.ExprLangParsingException if any.
	 * @throws java.text.ParseException if any.
	 */
	public static EventTypeGenerator makeEventTypeGenerator(EventType eventType, GenerationContext context) throws NotExistingGeneratorException, ExprLangParsingException, ParseException {
		return new EventTypeGenerator(new EventTypeGenerationAlgorithm(eventType, context), context);
	}

	


}
