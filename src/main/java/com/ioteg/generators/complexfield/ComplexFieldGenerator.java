package com.ioteg.generators.complexfield;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import com.ioteg.generators.GenerationAlgorithm;
import com.ioteg.generators.exceptions.NotExistingGeneratorException;
import com.ioteg.exprlang.ExprParser.ExprLangParsingException;
import com.ioteg.generators.FieldGenerator;
import com.ioteg.model.Field;
import com.ioteg.resultmodel.ResultField;

public class ComplexFieldGenerator extends FieldGenerator<ResultField> {


	public ComplexFieldGenerator(GenerationAlgorithm<ResultField> generationAlgorithm, Field field) {
		super(generationAlgorithm, field);
	}

	@Override
	public List<ResultField> generate(Integer numberOfRequiredItems) throws NotExistingGeneratorException, ExprLangParsingException, ParseException {
		List<ResultField> results = new ArrayList<>();
		
		for(int i = 0; i < numberOfRequiredItems; ++i)
			results.add(generationAlgorithm.generate());


		return results;
	}
}
