package com.ioteg.generators.datefield;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.ioteg.generators.GenerationAlgorithm;
import com.ioteg.generators.Generator;
import com.ioteg.model.Field;

public class DateGenerator extends Generator<Date>{

	public DateGenerator(GenerationAlgorithm<Date> generationAlgorithm) {
		super(generationAlgorithm);
	}

	@Override
	public List<String> generate(Field dateField, Integer numberOfRequiredItems) {
		List<String> results = new ArrayList<>();
		
		if (dateField.getFormat() != null) {	
			SimpleDateFormat sdf = new SimpleDateFormat(dateField.getFormat());
			for (int i = 0; i < numberOfRequiredItems; ++i)
			{
				Date result = generationAlgorithm.generate();
				results.add(sdf.format(result));
			}
		}
		else
			results = super.generate(dateField, numberOfRequiredItems);
		
		return results;
	}

	
}
