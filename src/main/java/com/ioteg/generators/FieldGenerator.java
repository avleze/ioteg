package com.ioteg.generators;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import com.ioteg.exprlang.ExprParser.ExprLangParsingException;
import com.ioteg.generators.context.GenerationContext;
import com.ioteg.generators.exceptions.NotExistingGeneratorException;
import com.ioteg.model.Field;
import com.ioteg.resultmodel.ResultField;
import com.ioteg.resultmodel.ResultSimpleField;

/**
 * <p>FieldGenerator class.</p>
 *
 * @author antonio
 * @version $Id: $Id
 */
public class FieldGenerator<T> extends AbstractGenerator<T> implements Generable {

	protected Field field;

	/**
	 * <p>Constructor for FieldGenerator.</p>
	 *
	 * @param generationAlgorithm a {@link com.ioteg.generators.GenerationAlgorithm} object.
	 * @param field a {@link com.ioteg.model.Field} object.
	 * @param generationContext a {@link com.ioteg.generators.context.GenerationContext} object.
	 */
	public FieldGenerator(GenerationAlgorithm<T> generationAlgorithm, Field field,
			GenerationContext generationContext) {
		super(generationAlgorithm, generationContext);
		this.field = field;
	}

	/** {@inheritDoc} */
	public List<ResultField> generate(Integer numberOfRequiredItems)
			throws NotExistingGeneratorException, ExprLangParsingException, ParseException {
		List<ResultField> results = new ArrayList<>();

		for (int i = 0; i < numberOfRequiredItems; ++i)
			results.add(new ResultSimpleField(field.getName(), field.getType(), field.getQuotes(),
					generationAlgorithm.generate().toString()));
		
		if(field != null && field.getInjectable())
			generationContext.putInjectableResultField(field.getName(), results.get(results.size() - 1));

		return results;
	}
}
