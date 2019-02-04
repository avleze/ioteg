package com.ioteg.generators.datefield;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.ioteg.RandomUtil;
import com.ioteg.Trio;
import com.ioteg.generators.QueryRestrictionGenerationAlgorithm;
import com.ioteg.model.Field;

public class DateQueryRestrictionGenerationAlgorithm extends QueryRestrictionGenerationAlgorithm<Date> {

	public DateQueryRestrictionGenerationAlgorithm(Field field, List<Trio<String, String, String>> restrictions) {
		super(field, restrictions);
	}

	@Override
	public Date generate() {
		Trio<String, String, String> fieldRestrictionInformation = restrictions.get(0);

		String operator = fieldRestrictionInformation.getSecond();

		Date value = parseDateValue(field, fieldRestrictionInformation.getThird());

		Date result = null;

		if (operator.equals("="))
			result = value;
		else if (operator.equals("!=")) 
			result = generateNotEqualValue(value);
		
		return result;
	}

	/**
	 * @param value The value that the generated one has to be different.
	 * @return A date different than value.
	 */
	private Date generateNotEqualValue(Date value) {
		Date result;
		do {
			result = RandomUtil.getRandomDate(new Date(RandomUtil.getMinimumDate()),
					new Date(RandomUtil.getMaximumDate()), false);
		} while (result.equals(value));
		
		return result;
	}

	/**
	 * @param dateField The field that must have the format attribute.
	 * @param value The value to parse.
	 * @return A new Date object formatted with the format of the dateField.
	 */
	private Date parseDateValue(Field dateField, String value) {
		SimpleDateFormat parser = new SimpleDateFormat(dateField.getFormat());
		Date result = null;
		try {
			result = parser.parse(value);
		} catch (ParseException e) {
			logger.error(e);
		}
		return result;
	}
}
