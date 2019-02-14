package com.ioteg.generators;
import com.ioteg.model.Field;

public abstract class GenerationAlgorithm<T> extends AbstractGenerationAlgorithm<T>{

	protected Field field;
	
	public GenerationAlgorithm(Field field) {
		super();
		this.field = field;
	}

	
}
