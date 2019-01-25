package com.ioteg.generators.datefield;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

import com.ioteg.generators.GenerationAlgorithm;
import com.ioteg.model.Field;

public class FixedDateGenerationAlgorithm implements GenerationAlgorithm<Date> {

	private Logger logger = Logger.getRootLogger();
	
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
