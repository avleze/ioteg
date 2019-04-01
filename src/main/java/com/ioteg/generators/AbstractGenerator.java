package com.ioteg.generators;

import com.ioteg.generators.context.GenerationContext;

/**
 * <p>Abstract AbstractGenerator class.</p>
 *
 * @author antonio
 * @version $Id: $Id
 */
public abstract class AbstractGenerator<T> {

	protected AbstractGenerationAlgorithm<T> generationAlgorithm;
	protected GenerationContext generationContext;

	/**
	 * <p>Constructor for AbstractGenerator.</p>
	 *
	 * @param generationAlgorithm a {@link com.ioteg.generators.AbstractGenerationAlgorithm} object.
	 * @param generationContext a {@link com.ioteg.generators.context.GenerationContext} object.
	 */
	public AbstractGenerator(AbstractGenerationAlgorithm<T> generationAlgorithm, GenerationContext generationContext) {
		this.generationAlgorithm = generationAlgorithm;
		this.generationContext = generationContext;
	}

}
