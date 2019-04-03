package com.ioteg.generation;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import com.ioteg.exprlang.ExprParser.ExprLangParsingException;
import com.ioteg.model.Field;
import com.ioteg.resultmodel.ResultField;
import com.ioteg.resultmodel.ResultSimpleField;

/**
 * <p>StringGenerator class.</p>
 *
 * @author antonio
 * @version $Id: $Id
 */
public class StringGenerator extends FieldGenerator<String> {

	/**
	 * <p>Constructor for StringGenerator.</p>
	 *
	 * @param generationAlgorithm a {@link com.ioteg.generation.GenerationAlgorithm} object.
	 * @param field a {@link com.ioteg.model.Field} object.
	 * @param generationContext a {@link com.ioteg.generation.GenerationContext} object.
	 */
	public StringGenerator(GenerationAlgorithm<String> generationAlgorithm, Field field,
			GenerationContext generationContext) {
		super(generationAlgorithm, field, generationContext);
	}

	/** {@inheritDoc} */
	@Override
	public List<ResultField> generate(Integer numberOfRequiredItems)
			throws NotExistingGeneratorException, ExprLangParsingException, ParseException {
		List<ResultField> results = new ArrayList<>();
		String caseStr = field.getCase();

		if (caseStr != null && caseStr.equalsIgnoreCase("low")) {
			for (int i = 0; i < numberOfRequiredItems; ++i)
				results.add(new ResultSimpleField(field.getName(), field.getType(), field.getQuotes(),
						generationAlgorithm.generate().toLowerCase()));
			if(field.getInjectable())
				generationContext.putInjectableResultField(field.getName(), results.get(results.size() - 1));
		}

		else
			results = super.generate(numberOfRequiredItems);

		return results;
	}

}
