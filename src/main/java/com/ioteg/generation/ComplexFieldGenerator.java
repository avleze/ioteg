package com.ioteg.generation;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import com.ioteg.exprlang.ExprParser.ExprLangParsingException;
import com.ioteg.model.Field;
import com.ioteg.resultmodel.ResultField;

/**
 * <p>ComplexFieldGenerator class.</p>
 *
 * @author antonio
 * @version $Id: $Id
 */
public class ComplexFieldGenerator extends FieldGenerator<ResultField> {

	/**
	 * <p>Constructor for ComplexFieldGenerator.</p>
	 *
	 * @param generationAlgorithm a {@link com.ioteg.generation.GenerationAlgorithm} object.
	 * @param field a {@link com.ioteg.model.Field} object.
	 * @param generationContext a {@link com.ioteg.generation.GenerationContext} object.
	 */
	public ComplexFieldGenerator(GenerationAlgorithm<ResultField> generationAlgorithm, Field field,
			GenerationContext generationContext) {
		super(generationAlgorithm, field, generationContext);
	}

	/** {@inheritDoc} */
	@Override
	public List<ResultField> generate(Integer numberOfRequiredItems)
			throws NotExistingGeneratorException, ExprLangParsingException, ParseException {
		List<ResultField> results = new ArrayList<>();

		for (int i = 0; i < numberOfRequiredItems; ++i)
			results.add(generationAlgorithm.generate());

		if(field != null && field.getInjectable())
			generationContext.putInjectableResultField(field.getName(), results.get(results.size() - 1));
		
		return results;
	}
}
