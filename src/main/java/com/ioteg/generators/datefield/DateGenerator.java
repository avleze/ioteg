package com.ioteg.generators.datefield;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.ioteg.generators.GenerationAlgorithm;
import com.ioteg.generators.context.GenerationContext;
import com.ioteg.generators.exceptions.NotExistingGeneratorException;
import com.ioteg.exprlang.ExprParser.ExprLangParsingException;
import com.ioteg.generators.FieldGenerator;
import com.ioteg.model.Field;
import com.ioteg.resultmodel.ResultField;
import com.ioteg.resultmodel.ResultSimpleField;

public class DateGenerator extends FieldGenerator<Date>{


	public DateGenerator(GenerationAlgorithm<Date> generationAlgorithm, Field field,
			GenerationContext generationContext) {
		super(generationAlgorithm, field, generationContext);
	}

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
			
			if(field != null && field.getInjectable())
				generationContext.putInjectableResultField(field.getName(), results.get(results.size() - 1));
		}
		else
			results = super.generate(numberOfRequiredItems);
		
		return results;
	}

	
}
