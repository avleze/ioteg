package com.ioteg.generation;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.ioteg.resultmodel.ResultField;

/**
 * <p>GenerationContext class.</p>
 *
 * @author antonio
 * @version $Id: $Id
 */
public class GenerationContext {
	private Map<String, Integer> selectedDependableIndexes;
	private ConcurrentMap<String, ResultField> injectableResultFields;

	/**
	 * <p>Constructor for GenerationContext.</p>
	 *
	 * @param injectableResultFields a {@link java.util.concurrent.ConcurrentMap} object.
	 */
	public GenerationContext(ConcurrentMap<String, ResultField> injectableResultFields) {
		if(injectableResultFields == null)
			injectableResultFields = new ConcurrentHashMap<>();
		
		this.selectedDependableIndexes = new HashMap<>();
		this.injectableResultFields = injectableResultFields;
	}
	
	/**
	 * <p>Constructor for GenerationContext.</p>
	 */
	public GenerationContext() {
		this(null);
	}

	/**
	 * <p>getDependenceIndex.</p>
	 *
	 * @param fieldName a {@link java.lang.String} object.
	 * @return a {@link java.lang.Integer} object.
	 */
	public Integer getDependenceIndex(String fieldName) {
		return selectedDependableIndexes.get(fieldName);
	}

	/**
	 * <p>putDependenceIndex.</p>
	 *
	 * @param fieldName a {@link java.lang.String} object.
	 * @param index a {@link java.lang.Integer} object.
	 * @return a {@link java.lang.Integer} object.
	 */
	public Integer putDependenceIndex(String fieldName, Integer index) {
		return selectedDependableIndexes.put(fieldName, index);
	}

	/**
	 * <p>getInjectableResultField.</p>
	 *
	 * @param fieldName a {@link java.lang.String} object.
	 * @return a {@link com.ioteg.resultmodel.ResultField} object.
	 */
	public ResultField getInjectableResultField(String fieldName) {
		return injectableResultFields.get(fieldName);
	}

	/**
	 * <p>putInjectableResultField.</p>
	 *
	 * @param fieldName a {@link java.lang.String} object.
	 * @param resultField a {@link com.ioteg.resultmodel.ResultField} object.
	 * @return a {@link com.ioteg.resultmodel.ResultField} object.
	 */
	public ResultField putInjectableResultField(String fieldName, ResultField resultField) {
		return injectableResultFields.put(fieldName, resultField);
	}
}
