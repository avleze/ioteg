package com.ioteg.generators;

import java.util.List;
import com.ioteg.Trio;

public abstract class QueryRestrictionGenerationAlgorithm<T> extends GenerationAlgorithm<T> {

	protected List<Trio<String, String, String>> restrictions;
	
	public QueryRestrictionGenerationAlgorithm(List<Trio<String, String, String>> restrictions) {
		super();
		this.restrictions = restrictions;
	}
}
