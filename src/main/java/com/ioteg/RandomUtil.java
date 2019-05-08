package com.ioteg;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import com.afowd.constants.Const;

/**
 * <p>RandomUtil class.</p>
 *
 * @author antonio
 * @version $Id: $Id
 */
public class RandomUtil {

	/** Constant <code>r</code> */
	protected static Random r;
	/** Constant <code>logger</code> */
	protected static Logger logger;

	private RandomUtil() {
		throw new IllegalStateException("This is an utility class and can't be instantiated.");
	}

	static {
		logger = Logger.getRootLogger();
		r = new Random();
	}


	/**
	 * return random date in range of min max date
	 *
	 * @param minDate a {@link java.util.Date} object.
	 * @param maxDate a {@link java.util.Date} object.
	 * @param bAllowNulls a boolean.
	 * @return a {@link java.util.Date} object.
	 */
	public static Date getRandomDate(Date minDate, Date maxDate, boolean bAllowNulls) {
		if (bAllowNulls && r.nextBoolean()) {
			return null;
		}
		long beginTime = minDate.getTime();
		long endTime = maxDate.getTime() + TimeUnit.MILLISECONDS.convert(1, TimeUnit.DAYS);
		long lNewDate = r.longs(beginTime, endTime).findFirst().getAsLong();
		return new Date(lNewDate);
	}

	/**
	 * minimum date 01.00.0001 or long value -62135773200000
	 *
	 * @return a long.
	 */
	public static long getMinimumDate() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Const.MIN_YEAR, calendar.getActualMinimum(Calendar.MONTH),
				calendar.getActualMinimum(Calendar.DAY_OF_MONTH), calendar.getActualMinimum(Calendar.HOUR),
				calendar.getActualMinimum(Calendar.MINUTE), calendar.getActualMinimum(Calendar.SECOND));

		calendar.set(Calendar.MILLISECOND, calendar.getActualMinimum(Calendar.MILLISECOND));
		return calendar.getTimeInMillis();
	}

	/**
	 * maximum date 17.34.292269054 or long value -9223372025090751617
	 *
	 * @return a long.
	 */
	public static long getMaximumDate() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Const.MAX_YEAR, calendar.getActualMaximum(Calendar.MONTH),
				calendar.getActualMaximum(Calendar.DAY_OF_MONTH), calendar.getActualMaximum(Calendar.HOUR),
				calendar.getActualMaximum(Calendar.MINUTE), calendar.getActualMaximum(Calendar.SECOND));

		calendar.set(Calendar.MILLISECOND, calendar.getActualMaximum(Calendar.MILLISECOND));
		return calendar.getTimeInMillis();
	}

	/**
	 * Generate a randomString
	 *
	 * @param length        determines the length of the String and
	 * @param endcharacter  determines the last character for the range
	 * @param possibleChars the possible chars to form the string.
	 * @return a random Alphanumeric String with a long length
	 */
	public static String getRandStringRange(int length, String endcharacter, String possibleChars) {
		StringBuilder result = new StringBuilder();
		int lastPossibleIndex = endcharacter == null ? possibleChars.length() : possibleChars.indexOf(endcharacter);
		while (result.length() < length) {
			int index = r.nextInt(lastPossibleIndex);
			result.append(possibleChars.charAt(index));
		}

		return result.toString();
	}

}
