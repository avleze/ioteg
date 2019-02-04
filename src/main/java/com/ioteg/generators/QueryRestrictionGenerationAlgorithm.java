package com.ioteg.generators;

import java.util.List;
import com.ioteg.Trio;
import com.ioteg.model.Field;

public abstract class QueryRestrictionGenerationAlgorithm<T> extends GenerationAlgorithm<T> {

	protected List<Trio<String, String, String>> restrictions;

	public QueryRestrictionGenerationAlgorithm(Field field, List<Trio<String, String, String>> restrictions) {
		super(field);
		this.restrictions = restrictions;
	}
}
