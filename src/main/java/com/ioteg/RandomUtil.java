package com.ioteg;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import com.afowd.constants.Const;

public class RandomUtil {

	/**
	 * return random primitive type long
	 * 
	 * @param minRange
	 * @param maxRange
	 * @return
	 */
	public static long getRandomPrimitiveLong(long minRange, long maxRange) {
		if (minRange == Long.MIN_VALUE) {
			long value = (long) ((Math.random() * (maxRange - Const.ZERO + Const.ONE) + Const.ZERO + Const.ONE));
			return value;
		} else {
			return (long) ((Math.random() * (maxRange - minRange + Const.ONE)) + minRange);
		}
	}

	/**
	 * return false if value need to be null
	 * 
	 * @return
	 */
	private static boolean getRandomValueOrNull() {
		int lRandomNumber = (int) (Math.random() * Const.FIVE);
		if (lRandomNumber % Const.FIVE == Const.ZERO) {
			return true;
		}
		return false;
	}

	/**
	 * return random date in range of min max date
	 * 
	 * @param minDate
	 * @param maxDate
	 * @return
	 */
	public static Date getRandomDate(Date minDate, Date maxDate, boolean bAllowNulls) {
		if (bAllowNulls && getRandomValueOrNull()) {
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

}
