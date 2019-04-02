package com.ioteg.generation;

import java.util.Date;
import java.util.List;

import com.ioteg.eplutils.Trio;
import com.ioteg.model.Field;

/**
 * <p>QueryRestrictionGeneratorsFactory class.</p>
 *
 * @author antonio
 * @version $Id: $Id
 */
public class QueryRestrictionGeneratorsFactory {

	private static final String ALPHABETICAL_VALUES = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private static final String ALPHANUMERIC_VALUES = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";


	private QueryRestrictionGeneratorsFactory() {
		throw new IllegalStateException("This is an utility class and can't be instantiated.");
	}
	
	/**
	 * <p>makeGenerator.</p>
	 *
	 * @param field a {@link com.ioteg.model.Field} object.
	 * @param restrictions a {@link java.util.List} object.
	 * @param generationContext a {@link com.ioteg.generation.GenerationContext} object.
	 * @return a {@link com.ioteg.generation.Generable} object.
	 * @throws com.ioteg.generation.NotExistingGeneratorException if any.
	 */
	public static Generable makeGenerator(Field field,
			List<Trio<String, String, String>> restrictions, GenerationContext generationContext) throws NotExistingGeneratorException {
		Generable generable = null;

		if (field.getType().equals("String"))
			generable = makeQueryRestrictionStringGenerator(field, restrictions, generationContext);
		else if (field.getType().equals("Alphanumeric"))
			generable = makeQueryRestrictionAlphanumericGenerator(field, restrictions, generationContext);
		else if (field.getType().equals("Integer") || field.getType().equals("Long"))
			generable = makeQueryRestrictionLongGenerator(field, restrictions, generationContext);
		else if (field.getType().equals("Float"))
			generable = makeQueryRestrictionFloatGenerator(field, restrictions, generationContext);
		else if (field.getType().equals("Boolean"))
			generable = makeQueryRestrictionBooleanGenerator(field, restrictions, generationContext);
		else if (field.getType().equals("Date"))
			generable = makeQueryRestrictionDateGenerator(field, restrictions, generationContext);
		else if (field.getType().equals("Time"))
			generable = makeQueryRestrictionTimeGenerator(field, restrictions, generationContext);
		else
			throw new NotExistingGeneratorException("There is no query restriction generator for type: " + field.getType());
	

		return generable;
	}

	/**
	 * <p>makeQueryRestrictionStringGenerator.</p>
	 *
	 * @param field a {@link com.ioteg.model.Field} object.
	 * @param restrictions a {@link java.util.List} object.
	 * @param generationContext a {@link com.ioteg.generation.GenerationContext} object.
	 * @return a {@link com.ioteg.generation.FieldGenerator} object.
	 */
	public static FieldGenerator<String> makeQueryRestrictionStringGenerator(Field field,
			List<Trio<String, String, String>> restrictions, GenerationContext generationContext) {
		return new StringGenerator(
				new StringQueryRestrictionGenerationAlgorithm(field, generationContext, restrictions, ALPHABETICAL_VALUES), field, generationContext);
	}

	/**
	 * <p>makeQueryRestrictionAlphanumericGenerator.</p>
	 *
	 * @param field a {@link com.ioteg.model.Field} object.
	 * @param restrictions a {@link java.util.List} object.
	 * @param generationContext a {@link com.ioteg.generation.GenerationContext} object.
	 * @return a {@link com.ioteg.generation.FieldGenerator} object.
	 */
	public static FieldGenerator<String> makeQueryRestrictionAlphanumericGenerator(Field field,
			List<Trio<String, String, String>> restrictions, GenerationContext generationContext) {
		return new StringGenerator(
				new StringQueryRestrictionGenerationAlgorithm(field, generationContext, restrictions, ALPHANUMERIC_VALUES), field, generationContext);
	}

	/**
	 * <p>makeQueryRestrictionLongGenerator.</p>
	 *
	 * @param field a {@link com.ioteg.model.Field} object.
	 * @param restrictions a {@link java.util.List} object.
	 * @param generationContext a {@link com.ioteg.generation.GenerationContext} object.
	 * @return a {@link com.ioteg.generation.FieldGenerator} object.
	 */
	public static FieldGenerator<Long> makeQueryRestrictionLongGenerator(Field field,
			List<Trio<String, String, String>> restrictions, GenerationContext generationContext) {
		return new FieldGenerator<>(new LongQueryRestrictionGenerationAlgorithm(field, generationContext, restrictions), field, generationContext);
	}

	/**
	 * <p>makeQueryRestrictionFloatGenerator.</p>
	 *
	 * @param field a {@link com.ioteg.model.Field} object.
	 * @param restrictions a {@link java.util.List} object.
	 * @param generationContext a {@link com.ioteg.generation.GenerationContext} object.
	 * @return a {@link com.ioteg.generation.FieldGenerator} object.
	 */
	public static FieldGenerator<Float> makeQueryRestrictionFloatGenerator(Field field,
			List<Trio<String, String, String>> restrictions, GenerationContext generationContext) {
		return new FloatGenerator(new FloatQueryRestrictionGenerationAlgorithm(field, generationContext, restrictions), field, generationContext);
	}

	/**
	 * <p>makeQueryRestrictionBooleanGenerator.</p>
	 *
	 * @param field a {@link com.ioteg.model.Field} object.
	 * @param restrictions a {@link java.util.List} object.
	 * @param generationContext a {@link com.ioteg.generation.GenerationContext} object.
	 * @return a {@link com.ioteg.generation.FieldGenerator} object.
	 */
	public static FieldGenerator<Boolean> makeQueryRestrictionBooleanGenerator(Field field,
			List<Trio<String, String, String>> restrictions, GenerationContext generationContext) {
		return new BooleanGenerator(new BooleanQueryRestrictionGenerationAlgorithm(field, generationContext, restrictions), field, generationContext);
	}

	/**
	 * <p>makeQueryRestrictionDateGenerator.</p>
	 *
	 * @param field a {@link com.ioteg.model.Field} object.
	 * @param restrictions a {@link java.util.List} object.
	 * @param generationContext a {@link com.ioteg.generation.GenerationContext} object.
	 * @return a {@link com.ioteg.generation.FieldGenerator} object.
	 */
	public static FieldGenerator<Date> makeQueryRestrictionDateGenerator(Field field,
			List<Trio<String, String, String>> restrictions, GenerationContext generationContext) {
		return new DateGenerator(new DateQueryRestrictionGenerationAlgorithm(field, generationContext, restrictions), field, generationContext);
	}

	/**
	 * <p>makeQueryRestrictionTimeGenerator.</p>
	 *
	 * @param field a {@link com.ioteg.model.Field} object.
	 * @param restrictions a {@link java.util.List} object.
	 * @param generationContext a {@link com.ioteg.generation.GenerationContext} object.
	 * @return a {@link com.ioteg.generation.FieldGenerator} object.
	 */
	public static FieldGenerator<Date> makeQueryRestrictionTimeGenerator(Field field,
			List<Trio<String, String, String>> restrictions, GenerationContext generationContext) {
		return makeQueryRestrictionDateGenerator(field, restrictions, generationContext);
	}
}
