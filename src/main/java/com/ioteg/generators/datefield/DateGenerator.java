package com.ioteg.generators.datefield;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.ioteg.generators.GenerationAlgorithm;
import com.ioteg.generators.exceptions.NotExistingGeneratorException;
import com.ioteg.generators.FieldGenerator;
import com.ioteg.model.Field;
import com.ioteg.resultmodel.ResultField;
import com.ioteg.resultmodel.ResultSimpleField;

public class DateGenerator extends FieldGenerator<Date>{

	public DateGenerator(GenerationAlgorithm<Date> generationAlgorithm, Field field) {
		super(generationAlgorithm, field);
	}

	@Override
	public List<ResultField> generate(Integer numberOfRequiredItems) throws NotExistingGeneratorException, IOException {
		List<ResultField> results = new ArrayList<>();
		
		if (field.getFormat() != null) {	
			SimpleDateFormat sdf = new SimpleDateFormat(field.getFormat());
			for (int i = 0; i < numberOfRequiredItems; ++i)
			{
				Date result = generationAlgorithm.generate();
				results.add(new ResultSimpleField(field.getName(), field.getType(), field.getQuotes(), sdf.format(result)));
			}
		}
		else
			results = super.generate(numberOfRequiredItems);
		
		return results;
	}

	
}
