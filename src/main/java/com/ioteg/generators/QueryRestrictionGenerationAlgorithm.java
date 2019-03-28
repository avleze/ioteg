package com.ioteg.generators;

import java.util.List;

import com.ioteg.eplutils.Trio;
import com.ioteg.generators.context.GenerationContext;
import com.ioteg.model.Field;

public abstract class QueryRestrictionGenerationAlgorithm<T> extends GenerationAlgorithm<T> {

	protected List<Trio<String, String, String>> restrictions;

	/**
	 * @param field
	 * @param generationContext
	 * @param restrictions
	 */
	public QueryRestrictionGenerationAlgorithm(Field field, GenerationContext generationContext,
			List<Trio<String, String, String>> restrictions) {
		super(field, generationContext);
		this.restrictions = restrictions;
	}
}
