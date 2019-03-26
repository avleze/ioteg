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
	protected Calendar begin;
	protected Calendar end;
	protected Integer unit;
	protected Calendar calendar;

	public SequentialDateGenerationAlgorithm(Field field) throws ParseException {
		super(field);
		SimpleDateFormat parser = new SimpleDateFormat(field.getFormat());
		this.calendar = GregorianCalendar.getInstance();
		this.step = Integer.parseInt(field.getStep());
		this.begin = Calendar.getInstance();
		this.end = Calendar.getInstance();
		this.begin.setTime(parser.parse(field.getBegin()));
		this.end.setTime(parser.parse(field.getEnd()));

		String unitStep = field.getUnit();

		if (unitStep.equalsIgnoreCase("MILLISECOND"))
			unit = Calendar.MILLISECOND;
		else if (unitStep.equalsIgnoreCase("SECOND"))
			unit = Calendar.SECOND;
		else if (unitStep.equalsIgnoreCase("MINUTE"))
			unit = Calendar.MINUTE;
		else if (unitStep.equalsIgnoreCase("HOUR"))
			unit = Calendar.HOUR_OF_DAY;
		else if (unitStep.equalsIgnoreCase("DAY"))
			unit = Calendar.DATE;
		else if (unitStep.equalsIgnoreCase("MONTH"))
			unit = Calendar.MONTH;
		else if (unitStep.equalsIgnoreCase("YEAR"))
			unit = Calendar.YEAR;

		this.calendar.setTime(this.begin.getTime());
	}

	@Override
	public Date generate() {
		Date timeToReturn = this.calendar.getTime();
		if(step > 0 && this.calendar.compareTo(end) < 0)
			this.calendar.add(unit, step);
		else if(step < 0 && this.calendar.compareTo(end) > 0)
			this.calendar.add(unit, step);
		else
			this.calendar.setTime(begin.getTime());
		
		return timeToReturn;
	}
	

}
