package com.ioteg.generation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.ioteg.exprlang.ExprParser.ExprLangParsingException;
import com.ioteg.model.Field;
import com.ioteg.resultmodel.ResultField;
import com.ioteg.resultmodel.ResultSimpleField;

/**
 * <p>DateGenerator class.</p>
 *
 * @author antonio
 * @version $Id: $Id
 */
public class DateGenerator extends FieldGenerator<Date>{


	/**
	 * <p>Constructor for DateGenerator.</p>
	 *
	 * @param generationAlgorithm a {@link com.ioteg.generation.GenerationAlgorithm} object.
	 * @param field a {@link com.ioteg.model.Field} object.
	 * @param generationContext a {@link com.ioteg.generation.GenerationContext} object.
	 */
	public DateGenerator(GenerationAlgorithm<Date> generationAlgorithm, Field field,
			GenerationContext generationContext) {
		super(generationAlgorithm, field, generationContext);
	}

	/** {@inheritDoc} */
	@Override
	public List<ResultField> generate(Integer numberOfRequiredItems) throws NotExistingGeneratorException, ExprLangParsingException, ParseException {
		List<ResultField> results = new ArrayList<>();
		
		if (field.getFormat() != null) {	
			SimpleDateFormat sdf = new SimpleDateFormat(field.getFormat());
			for (int i = 0; i < numberOfRequiredItems; ++i)
			{
				Date result = generationAlgorithm.generate();
				results.add(new ResultSimpleField(field.getName(), field.getType(), field.getQuotes(), sdf.format(result)));
			}
			
			if(field.getInjectable())
				generationContext.putInjectableResultField(field.getName(), results.get(results.size() - 1));
		}
		else
			results = super.generate(numberOfRequiredItems);
		
		return results;
	}

	
}
