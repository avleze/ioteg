package com.ioteg.generators.datefield;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.ioteg.generators.GenerationAlgorithm;
import com.ioteg.generators.context.GenerationContext;
import com.ioteg.model.Field;

public class FixedDateGenerationAlgorithm extends GenerationAlgorithm<Date> {

	public FixedDateGenerationAlgorithm(Field field, GenerationContext generationContext) {
		super(field, generationContext);
	}

	@Override
	public Date generate() {
		SimpleDateFormat parser = new SimpleDateFormat(field.getFormat());
       
		Date result = null;
		try {
			 result = parser.parse(field.getValue());
		} catch (ParseException e) {
			logger.error(e);
		}
		
		return result;
	}

}
