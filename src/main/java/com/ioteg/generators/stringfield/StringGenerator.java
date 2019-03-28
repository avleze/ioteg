package com.ioteg.generators.stringfield;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import com.ioteg.generators.GenerationAlgorithm;
import com.ioteg.generators.context.GenerationContext;
import com.ioteg.generators.exceptions.NotExistingGeneratorException;
import com.ioteg.exprlang.ExprParser.ExprLangParsingException;
import com.ioteg.generators.FieldGenerator;
import com.ioteg.model.Field;
import com.ioteg.resultmodel.ResultField;
import com.ioteg.resultmodel.ResultSimpleField;

public class StringGenerator extends FieldGenerator<String>{


	public StringGenerator(GenerationAlgorithm<String> generationAlgorithm, Field field,
			GenerationContext generationContext) {
		super(generationAlgorithm, field, generationContext);
	}

	@Override
	public List<ResultField> generate(Integer numberOfRequiredItems) throws NotExistingGeneratorException, ExprLangParsingException, ParseException {
		List<ResultField> results = new ArrayList<>();
		String caseStr = field.getCase();
		
		if (caseStr != null && caseStr.equalsIgnoreCase("low")) 
			for (int i = 0; i < numberOfRequiredItems; ++i)
				results.add(new ResultSimpleField(field.getName(), field.getType(), field.getQuotes(), generationAlgorithm.generate().toLowerCase()));
		else
			results = super.generate(numberOfRequiredItems);
		
		return results;
	}

	
}
