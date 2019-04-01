package com.ioteg.generators.datefield;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.ioteg.RandomUtil;
import com.ioteg.eplutils.Trio;
import com.ioteg.generators.QueryRestrictionGenerationAlgorithm;
import com.ioteg.generators.context.GenerationContext;
import com.ioteg.model.Field;

/**
 * <p>DateQueryRestrictionGenerationAlgorithm class.</p>
 *
 * @author antonio
 * @version $Id: $Id
 */
public class DateQueryRestrictionGenerationAlgorithm extends QueryRestrictionGenerationAlgorithm<Date> {

	/**
	 * <p>Constructor for DateQueryRestrictionGenerationAlgorithm.</p>
	 *
	 * @param field a {@link com.ioteg.model.Field} object.
	 * @param generationContext a {@link com.ioteg.generators.context.GenerationContext} object.
	 * @param restrictions a {@link java.util.List} object.
	 */
	public DateQueryRestrictionGenerationAlgorithm(Field field, GenerationContext generationContext, List<Trio<String, String, String>> restrictions) {
		super(field, generationContext, restrictions);
	}

	/** {@inheritDoc} */
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
