package com.ioteg.generation;

import java.util.List;

import com.ioteg.eplutils.Trio;
import com.ioteg.model.Field;

/**
 * <p>Abstract QueryRestrictionGenerationAlgorithm class.</p>
 *
 * @author antonio
 * @version $Id: $Id
 */
public abstract class QueryRestrictionGenerationAlgorithm<T> extends GenerationAlgorithm<T> {

	protected List<Trio<String, String, String>> restrictions;

	/**
	 * <p>Constructor for QueryRestrictionGenerationAlgorithm.</p>
	 *
	 * @param field a {@link com.ioteg.model.Field} object.
	 * @param generationContext a {@link com.ioteg.generation.GenerationContext} object.
	 * @param restrictions a {@link java.util.List} object.
	 */
	public QueryRestrictionGenerationAlgorithm(Field field, GenerationContext generationContext,
			List<Trio<String, String, String>> restrictions) {
		super(field, generationContext);
		this.restrictions = restrictions;
	}
}
