package com.ioteg.generators;

import com.ioteg.generators.context.GenerationContext;

public abstract class AbstractGenerator<T> {

	protected AbstractGenerationAlgorithm<T> generationAlgorithm;
	protected GenerationContext generationContext;

	/**
	 * @param generationAlgorithm
	 * @param generationContext
	 */
	public AbstractGenerator(AbstractGenerationAlgorithm<T> generationAlgorithm, GenerationContext generationContext) {
		this.generationAlgorithm = generationAlgorithm;
		this.generationContext = generationContext;
	}

}
