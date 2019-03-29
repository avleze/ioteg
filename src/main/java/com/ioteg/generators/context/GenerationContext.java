package com.ioteg.generators.context;

import java.util.HashMap;
import java.util.Map;

public class GenerationContext {
	Map<String, Integer> selectedDependableIndexes;


	public GenerationContext() {
		this.selectedDependableIndexes = new HashMap<>();
	}
	
	public Integer getDependenceIndex(String fieldName) {
		return selectedDependableIndexes.get(fieldName);
	}
	
	public Integer putDependenceIndex(String fieldName, Integer index) {
		return selectedDependableIndexes.put(fieldName, index);
	}
}
