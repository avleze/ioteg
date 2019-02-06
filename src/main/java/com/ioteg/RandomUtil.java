package com.ioteg;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import com.afowd.constants.Const;

public class RandomUtil {

	protected static Random r;
	protected static Logger logger;

	private RandomUtil() {
		throw new IllegalStateException("This is an utility class and can't be instantiated.");
	}

	static {
		logger = Logger.getRootLogger();
		try {
			r = SecureRandom.getInstanceStrong();
		} catch (NoSuchAlgorithmException e) {
			logger.error(e);
		}
	}

	/**
	 * return random primitive type long
	 * 
	 * @param minRange
	 * @param maxRange
	 * @return
	 */
	public static long getRandomPrimitiveLong(long minRange, long maxRange) {
		if (minRange == Long.MIN_VALUE)
			return (long) ((Math.random() * (maxRange - Const.ZERO + Const.ONE) + Const.ZERO + Const.ONE));
		else
			return (long) ((Math.random() * (maxRange - minRange + Const.ONE)) + minRange);

	}

	/**
	 * return random date in range of min max date
	 * 
	 * @param minDate
	 * @param maxDate
	 * @return
	 */
	public static Date getRandomDate(Date minDate, Date maxDate, boolean bAllowNulls) {
		if (bAllowNulls && r.nextBoolean()) {
			return null;
		}
		long beginTime = minDate.getTime();
		long endTime = maxDate.getTime() + TimeUnit.MILLISECONDS.convert(1, TimeUnit.DAYS);
		long lNewDate = RandomUtil.getRandomPrimitiveLong(beginTime, endTime);
		return new Date(lNewDate);
	}

	/**
	 * minimum date 01.00.0001 or long value -62135773200000
	 * 
	 * @return
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
	 * @return
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
