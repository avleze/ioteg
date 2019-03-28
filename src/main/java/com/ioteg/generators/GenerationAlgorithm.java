package com.ioteg.generators;
import com.ioteg.generators.context.GenerationContext;
import com.ioteg.model.Field;

public abstract class GenerationAlgorithm<T> extends AbstractGenerationAlgorithm<T>{

	protected Field field;
	protected GenerationContext generationContext;
	
	public GenerationAlgorithm(Field field, GenerationContext generationContext) {
		super();
		this.field = field;
		this.generationContext = generationContext;
	}

	
}
