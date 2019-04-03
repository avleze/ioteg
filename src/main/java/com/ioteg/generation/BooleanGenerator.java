package com.ioteg.generation;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import com.ioteg.exprlang.ExprParser.ExprLangParsingException;
import com.ioteg.model.Field;
import com.ioteg.resultmodel.ResultField;
import com.ioteg.resultmodel.ResultSimpleField;

/**
 * <p>BooleanGenerator class.</p>
 *
 * @author antonio
 * @version $Id: $Id
 */
public class BooleanGenerator extends FieldGenerator<Boolean> {

	/**
	 * <p>Constructor for BooleanGenerator.</p>
	 *
	 * @param generationAlgorithm a {@link com.ioteg.generation.GenerationAlgorithm} object.
	 * @param field a {@link com.ioteg.model.Field} object.
	 * @param generationContext a {@link com.ioteg.generation.GenerationContext} object.
	 */
	public BooleanGenerator(GenerationAlgorithm<Boolean> generationAlgorithm, Field field,
			GenerationContext generationContext) {
		super(generationAlgorithm, field, generationContext);
	}

	/** {@inheritDoc} */
	@Override
	public List<ResultField> generate(Integer numberOfRequiredItems)
			throws NotExistingGeneratorException, ExprLangParsingException, ParseException {
		List<ResultField> results = new ArrayList<>();

		if (field.getIsNumeric()) {
			for (int i = 0; i < numberOfRequiredItems; ++i)
				results.add(new ResultSimpleField(field.getName(), field.getType(), field.getQuotes(),
						booleanToNumericalString(generationAlgorithm.generate())));
			if(field.getInjectable())
				generationContext.putInjectableResultField(field.getName(), results.get(results.size() - 1));
		}

		else
			results = super.generate(numberOfRequiredItems);

		return results;
	}

	private String booleanToNumericalString(Boolean b) {
		return b.booleanValue() ? "1" : "0";
	}
}
