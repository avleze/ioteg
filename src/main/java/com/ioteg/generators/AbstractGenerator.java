package com.ioteg.generators;



public abstract class AbstractGenerator<T> {

	protected AbstractGenerationAlgorithm<T> generationAlgorithm;

	public AbstractGenerator(AbstractGenerationAlgorithm<T> generationAlgorithm) {
		super();
		this.generationAlgorithm = generationAlgorithm;
	}
	
}
