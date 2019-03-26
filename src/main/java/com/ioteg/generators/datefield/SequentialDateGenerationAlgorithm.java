package com.ioteg.generators.datefield;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import com.ioteg.generators.GenerationAlgorithm;
import com.ioteg.model.Field;

public class SequentialDateGenerationAlgorithm extends GenerationAlgorithm<Date> {

	protected Integer step;
	protected Date begin;
	protected Date end;
	protected Integer unit;
	protected Calendar calendar;

	public SequentialDateGenerationAlgorithm(Field field) throws ParseException {
		super(field);
		SimpleDateFormat parser = new SimpleDateFormat(field.getFormat());
		this.calendar = GregorianCalendar.getInstance();
		this.step = Integer.parseInt(field.getStep());
		if (step < 0) {
			this.end = parser.parse(field.getBegin());
			this.begin = parser.parse(field.getEnd());
		} else {
			this.begin = parser.parse(field.getBegin());
			this.end = parser.parse(field.getEnd());
		}
		
		String unitStep = field.getUnit();
	
		if(unitStep.equalsIgnoreCase("MILLISECOND"))
			unit = Calendar.MILLISECOND;
		else if(unitStep.equalsIgnoreCase("SECOND"))
			unit = Calendar.SECOND;
		else if(unitStep.equalsIgnoreCase("MINUTE"))
			unit = Calendar.MINUTE;
		else if(unitStep.equalsIgnoreCase("HOUR"))
			unit = Calendar.HOUR_OF_DAY;
		else if(unitStep.equalsIgnoreCase("DAY"))
			unit = Calendar.DATE;
		else if(unitStep.equalsIgnoreCase("MONTH"))
			unit = Calendar.MONTH;
		else if(unitStep.equalsIgnoreCase("YEAR"))
			unit = Calendar.YEAR;

		this.calendar.setTime(this.begin);
	}

	@Override
	public Date generate() {
		Date timeToReturn = this.calendar.getTime();
		
		if(step < 0 && timeToReturn.after(end))
			this.calendar.add(unit, step);
		else if(step > 0 && timeToReturn.before(end))
			this.calendar.add(unit, step);
		else
			this.calendar.setTime(begin);
		
		return timeToReturn;
	}

}
