package com.ioteg.generators.context;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.ioteg.resultmodel.ResultField;

public class GenerationContext {
	Map<String, Integer> selectedDependableIndexes;
	ConcurrentMap<String, ResultField> injectableResultFields;

	public GenerationContext(ConcurrentMap<String, ResultField> injectableResultFields) {
		if(injectableResultFields == null)
			injectableResultFields = new ConcurrentHashMap<>();
		
		this.selectedDependableIndexes = new HashMap<>();
		this.injectableResultFields = injectableResultFields;
	}
	
	public GenerationContext() {
		this(null);
	}

	public Integer getDependenceIndex(String fieldName) {
		return selectedDependableIndexes.get(fieldName);
	}

	public Integer putDependenceIndex(String fieldName, Integer index) {
		return selectedDependableIndexes.put(fieldName, index);
	}

	public ResultField getInjectableResultField(String fieldName) {
		return injectableResultFields.get(fieldName);
	}

	public ResultField putInjectableResultField(String fieldName, ResultField resultField) {
		return injectableResultFields.put(fieldName, resultField);
	}
}