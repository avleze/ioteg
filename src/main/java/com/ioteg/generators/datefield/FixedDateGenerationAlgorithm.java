package com.ioteg.generators.datefield;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.ioteg.generators.GenerationAlgorithm;
import com.ioteg.model.Field;

public class FixedDateGenerationAlgorithm extends GenerationAlgorithm<Date> {
	
	@Override
	public Date generate(Field dateField) {
		SimpleDateFormat parser = new SimpleDateFormat(dateField.getFormat());
       
		Date result = null;
		try {
			 result = parser.parse(dateField.getValue());
		} catch (ParseException e) {
			logger.error(e);
		}
		
		return result;
	}

}
