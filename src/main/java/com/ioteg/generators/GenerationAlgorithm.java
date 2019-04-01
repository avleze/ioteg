package com.ioteg.generators;
import com.ioteg.generators.context.GenerationContext;
import com.ioteg.model.Field;

/**
 * <p>Abstract GenerationAlgorithm class.</p>
 *
 * @author antonio
 * @version $Id: $Id
 */
public abstract class GenerationAlgorithm<T> extends AbstractGenerationAlgorithm<T>{

	protected Field field;
	protected GenerationContext generationContext;
	
	/**
	 * <p>Constructor for GenerationAlgorithm.</p>
	 *
	 * @param field a {@link com.ioteg.model.Field} object.
	 * @param generationContext a {@link com.ioteg.generators.context.GenerationContext} object.
	 */
	public GenerationAlgorithm(Field field, GenerationContext generationContext) {
		super();
		this.field = field;
		this.generationContext = generationContext;
	}

	
}
