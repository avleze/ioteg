package com.ioteg;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import com.afowd.constants.Const;

public class RandomUtil {
	public static final List<String> listOfBasicTypes = Arrays.asList(BigInteger.class.getName(),
			BigDecimal.class.getName(), Integer.class.getName(), Long.class.getName(), Short.class.getName(),
			Byte.class.getName(), Boolean.class.getName(), Double.class.getName(), Float.class.getName(),
			String.class.getName(), Date.class.getName(), int.class.getName(), long.class.getName(),
			short.class.getName(), byte.class.getName(), boolean.class.getName(), double.class.getName(),
			float.class.getName(), char.class.getName(), Character.class.getName(), BigDecimal.class.getName(),
			BigInteger.class.getName());
	public static final List<String> listOfHashTypes = Arrays.asList(HashMap.class.getName(), HashSet.class.getName(),
			TreeMap.class.getName(), Hashtable.class.getName());

	public static final List<String> listOfArrayTypes = Arrays.asList(ArrayList.class.getName(),
			Collections.class.getName(), List.class.getName());
	public static final Map<String, String> mMappingMap = new HashMap<String, String>();
	static {
		mMappingMap.put("int", Integer.class.getName());
		mMappingMap.put("long", Long.class.getName());
		mMappingMap.put("double", Double.class.getName());
		mMappingMap.put("float", Float.class.getName());
		mMappingMap.put("boolean", Boolean.class.getName());
		mMappingMap.put("char", Character.class.getName());
		mMappingMap.put("byte", Byte.class.getName());
		mMappingMap.put("void", Void.class.getName());
		mMappingMap.put("short", Short.class.getName());
	}

	/**
	 * return random Boolean or null
	 * 
	 * @return
	 */
	public static Boolean getRandomBoolean(boolean bAllowNulls) {
		if (bAllowNulls && getRandomValueOrNull()) {
			return null;
		}
		int lRandomNumber = (int) (Math.random() * Const.TWO);
		if (lRandomNumber == Const.ZERO) {
			return Boolean.FALSE;
		}
		return Boolean.TRUE;
	}

	/**
	 * return random primitive type float
	 * 
	 * @param minRange
	 * @param maxRange
	 * @return
	 */
	public static float getRandomPrimitiveFloat(float minRange, float maxRange, BigDecimal precision) {
		if (minRange == Float.MIN_VALUE) {
			float value = Float.valueOf((float) ((Math.random() * (maxRange - Const.ZERO + Const.ONE)) + Const.ZERO));
			BigDecimal bgValue = new BigDecimal(value);
			if (getPositiveOrNegative()) {
				return new Float(
						bgValue.round(new MathContext(bgValue.precision() - bgValue.scale() + precision.intValue(),
								RoundingMode.HALF_UP)).toString()).floatValue();
			} else {
				return new Float(
						bgValue.round(new MathContext(bgValue.precision() - bgValue.scale() + precision.intValue(),
								RoundingMode.HALF_UP)).toString()).floatValue()
						* Const.MINUS_ONE;
			}
		} else {
			Float value = Float.valueOf((float) ((Math.random() * (maxRange - minRange + Const.ONE)) + minRange));
			BigDecimal bgValue = new BigDecimal(value);
			return new Float(bgValue.round(
					new MathContext(bgValue.precision() - bgValue.scale() + precision.intValue(), RoundingMode.HALF_UP))
					.toString()).floatValue();
		}
	}

	/**
	 * return random Float or null
	 * 
	 * @param minRange
	 * @param maxRange
	 * @return
	 */
	public static Float getRandomFloat(Float minRange, Float maxRange, BigDecimal precision, boolean bAllowNulls) {
		if (bAllowNulls && getRandomValueOrNull()) {
			return null;
		}
		if (minRange == Float.MIN_VALUE) {
			Float value = Float.valueOf((float) ((Math.random() * (maxRange - Const.ZERO + Const.ONE)) + Const.ZERO));
			BigDecimal bgValue = new BigDecimal(value);
			if (getPositiveOrNegative()) {
				return new Float(
						bgValue.round(new MathContext(bgValue.precision() - bgValue.scale() + precision.intValue(),
								RoundingMode.HALF_UP)).toString());
			} else {
				return new Float(
						bgValue.round(new MathContext(bgValue.precision() - bgValue.scale() + precision.intValue(),
								RoundingMode.HALF_UP)).toString()).floatValue()
						* Const.MINUS_ONE;
			}
		} else {
			Float value = Float.valueOf((float) ((Math.random() * (maxRange - minRange + Const.ONE)) + minRange));
			BigDecimal bgValue = new BigDecimal(value);
			return new Float(bgValue.round(
					new MathContext(bgValue.precision() - bgValue.scale() + precision.intValue(), RoundingMode.HALF_UP))
					.toString());
		}
	}

	/**
	 * return random primitive type double
	 * 
	 * @param minRange
	 * @param maxRange
	 * @return
	 */
	public static Double getRandomPrimitiveDouble(double minRange, double maxRange, BigDecimal precision) {
		if (minRange == Double.MIN_VALUE) {
			double value = Double.valueOf(((Math.random() * (maxRange - Const.ZERO + Const.ONE)) + Const.ZERO));
			BigDecimal bgValue = new BigDecimal(value);
			if (getPositiveOrNegative()) {
				return new Double(
						bgValue.round(new MathContext(bgValue.precision() - bgValue.scale() + precision.intValue(),
								RoundingMode.HALF_UP)).toString()).doubleValue();
			} else {
				return new Double(
						bgValue.round(new MathContext(bgValue.precision() - bgValue.scale() + precision.intValue(),
								RoundingMode.HALF_UP)).toString()).doubleValue()
						* Const.MINUS_ONE;
			}
		} else {
			Double value = Double.valueOf((Math.random() * (maxRange - minRange + Const.ONE)) + minRange);
			BigDecimal bgValue = new BigDecimal(value);
			return new Double(bgValue.round(
					new MathContext(bgValue.precision() - bgValue.scale() + precision.intValue(), RoundingMode.HALF_UP))
					.toString());
		}
	}

	/**
	 * return random Double or null
	 * 
	 * @param minRange
	 * @param maxRange
	 * @return
	 */
	public static Double getRandomDouble(Double minRange, Double maxRange, BigDecimal precision, boolean bAllowNulls) {
		if (bAllowNulls && getRandomValueOrNull()) {
			return null;
		}
		if (minRange.doubleValue() == Double.MIN_VALUE) {
			Double value = Double.valueOf(((Math.random() * (maxRange - Const.ZERO + Const.ONE)) + Const.ZERO));
			BigDecimal bgValue = new BigDecimal(value);
			if (getPositiveOrNegative()) {
				return new Double(
						bgValue.round(new MathContext(bgValue.precision() - bgValue.scale() + precision.intValue(),
								RoundingMode.HALF_UP)).toString());
			} else {
				return new Double(
						bgValue.round(new MathContext(bgValue.precision() - bgValue.scale() + precision.intValue(),
								RoundingMode.HALF_UP)).toString())
						* Const.MINUS_ONE;
			}
		} else {
			Double value = Double.valueOf((Math.random() * (maxRange - minRange + Const.ONE)) + minRange);
			BigDecimal bgValue = new BigDecimal(value);
			return new Double(bgValue.round(
					new MathContext(bgValue.precision() - bgValue.scale() + precision.intValue(), RoundingMode.HALF_UP))
					.toString());
		}
	}

	/**
	 * return random primitive type int
	 * 
	 * @param minRange
	 * @param maxRange
	 * @return
	 */
	public static int getRandomPrimitiveInt(int minRange, int maxRange) {
		if (minRange == Integer.MIN_VALUE) {
			int value = (int) ((Math.random() * (maxRange - Const.ZERO + Const.ONE)) + Const.ZERO);
			return value;
		} else {
			return (int) ((Math.random() * (maxRange - minRange + Const.ONE)) + minRange);
		}
	}

	/**
	 * get random positive or negative number
	 */
	private static boolean getPositiveOrNegative() {
		int lRandomNumber = (int) (Math.random() * Const.THREE);
		if (lRandomNumber % Const.TWO == Const.ZERO) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * return random Integer or null
	 * 
	 * @param minRange
	 * @param maxRange
	 * @return
	 */
	public static Integer getRandomInteger(Integer minRange, Integer maxRange, boolean bAllowNulls) {
		if (bAllowNulls && getRandomValueOrNull()) {
			return null;
		}
		if (minRange.intValue() == Integer.MIN_VALUE) {
			Integer value = (int) ((Math.random() * (maxRange - Const.ZERO + Const.ONE)) + Const.ZERO);
			return value;
		} else {
			return Integer.valueOf((int) ((Math.random() * (maxRange - minRange + Const.ONE)) + minRange));
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
		if (minRange == Long.MIN_VALUE) {
			long value = (long) ((Math.random() * (maxRange - Const.ZERO + Const.ONE) + Const.ZERO + Const.ONE));
			return value;
		} else {
			return (long) ((Math.random() * (maxRange - minRange + Const.ONE)) + minRange);
		}
	}

	/**
	 * return random Long or null
	 * 
	 * @param minRange
	 * @param maxRange
	 * @return
	 */
	public static Long getRandomLong(Long minRange, Long maxRange, boolean bAllowNulls) {
		if (bAllowNulls && getRandomValueOrNull()) {
			return null;
		}
		if (minRange.longValue() == Long.MIN_VALUE) {
			Long value = (long) ((Math.random() * (maxRange - Const.ZERO + Const.ONE)) + Const.ZERO);
			return value;
		} else {
			return Long.valueOf((long) ((Math.random() * (maxRange - minRange + Const.ONE)) + minRange));
		}
	}

	/**
	 * return random primitive type short
	 * 
	 * @param minRange
	 * @param maxRange
	 * @return
	 */
	public static short getRandomPrimitiveShort(short minRange, short maxRange) {
		if (minRange == Short.MIN_VALUE) {
			short value = (short) ((Math.random() * (maxRange - Const.ZERO + Const.ONE)) + Const.ZERO);
			return value;
		} else {
			return (short) ((Math.random() * (maxRange - minRange + Const.ONE)) + minRange);
		}
	}

	/**
	 * return random Short or null
	 * 
	 * @param minRange
	 * @param maxRange
	 * @return
	 */
	public static Short getRandomShort(Short minRange, Short maxRange, boolean bAllowNulls) {
		if (bAllowNulls && getRandomValueOrNull()) {
			return null;
		}
		if (minRange.shortValue() == Short.MIN_VALUE) {
			Short value = (short) ((Math.random() * (maxRange - Const.ZERO + Const.ONE)) + Const.ZERO);
			return value;
		} else {
			return Short.valueOf((short) ((Math.random() * (maxRange - minRange + Const.ONE)) + minRange));
		}
	}

	/**
	 * return random primitive type byte
	 * 
	 * @param minRange
	 * @param maxRange
	 * @return
	 */
	public static byte getRandomPrimitiveByte(byte minRange, byte maxRange) {
		if (minRange == Byte.MIN_VALUE) {
			byte value = (byte) ((Math.random() * (maxRange - Const.ZERO + Const.ONE)) + Const.ZERO);
			return value;
		} else {
			return (byte) ((Math.random() * (maxRange - minRange + Const.ONE)) + minRange);
		}
	}

	/**
	 * return random Byte or null
	 * 
	 * @param minRange
	 * @param maxRange
	 * @return
	 */
	public static Byte getRandomByte(Byte minRange, Byte maxRange, boolean bAllowNulls) {
		if (bAllowNulls && getRandomValueOrNull()) {
			return null;
		}
		if (minRange.byteValue() == Byte.MIN_VALUE) {
			Byte value = (byte) ((Math.random() * (maxRange - Const.ZERO + Const.ONE)) + Const.ZERO);
			return value;
		} else {
			return Byte.valueOf((byte) ((Math.random() * (maxRange - minRange + Const.ONE)) + minRange));
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
	 * return random BigInteger in range of bytes or null
	 * 
	 * @param minNumberOfBytes
	 * @param maxNumberOfBytes
	 * @return
	 */
	public static BigInteger getRandomBigInteger(BigInteger minRange, BigInteger maxRange, boolean bAllowNulls) {
		BigDecimal bgValue = getRandomBigDecimal(new BigDecimal(minRange), new BigDecimal(maxRange),
				new BigDecimal(Const.ONE), bAllowNulls);
		BigInteger biValue = null;
		if (bgValue != null) {
			biValue = bgValue.round(new MathContext(bgValue.precision() - bgValue.scale())).toBigInteger();
		}
		return biValue;
	}

	/**
	 * return random BigDecimal in range of bytes or null
	 * 
	 * @param minNumberOfBytes
	 * @param maxNumberOfBytes
	 * @param precision
	 * @return
	 */
	public static BigDecimal getRandomBigDecimal(BigDecimal minRange, BigDecimal maxRange, BigDecimal precision,
			boolean bAllowNulls) {
		if (bAllowNulls && getRandomValueOrNull()) {
			return null;
		}
		BigDecimal bgValue = new BigDecimal(
				minRange.add(new BigDecimal(Math.random()).multiply(maxRange.subtract(minRange))).toString());
		bgValue = bgValue.round(
				new MathContext(bgValue.precision() - bgValue.scale() + precision.intValue(), RoundingMode.HALF_UP));
		return bgValue;
	}

	/**
	 * return random array of formated characters or null
	 * 
	 * @return
	 */
	public static String getRandomUUID(boolean bAllowNulls) {
		if (bAllowNulls && getRandomValueOrNull()) {
			return null;
		}
		return UUID.randomUUID().toString();
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

	public static char getRandomChar() {
		return (char) getRandomPrimitiveInt(Const.ZERO_INTEGER, Const.MAX_CHAR);
	}

	public static char getRandomChar(int minChar, int maxChar) {
		return (char) getRandomPrimitiveInt(minChar, maxChar);
	}

	/**
	 * return random names
	 * 
	 * @param alRandomNames
	 * @return
	 */
	public static String getRandomNames(int name, boolean bAllowNulls) {
		String[] alOfFirstNames = { "Joe", "Donna", "Ronald", "Sarah", "David", "Courtney", "Irwin", "Linda", "Michael",
				"Cindy", "Tom", "Rebekah", "Todd", "Tracy", "Peter", "Nicole", "Marcelo", "Jennifer", "Rick", "Andrea",
				"Bruce", "Jaclyn", "Doug", "Shirley", "Steve", "Liz", "Waldo", "Theresa", "Scott", "Colby", "Beth",
				"Larry", "Emily", "Paul", "Kate", "Sam", "Dianne", "Dustin", "Alethea", "Wayne", "Kristina",
				"Christian", "Danny", "Breya", "Andrew", "Alison", "Tim", "Mary", "Chris", "Susie", "Jeremy", "Willy",
				"Jessica", "Marcus", "Kelly", "Kyle", "Stephanie", "Isaiah", "Hillary", "Eric", "Julia", "Donald",
				"Meredith", "Kevin", "Leslie", "Blake", "Angela", "Cliff", "Debbie", "Dylan", "Erin", "Alex", "Monica",
				"Nathan", "Wendy", "Josh", "Megan", "Adam", "Michelle", "Carey", "Ashley", "Brian", "Jason", "Melanie",
				"Jim", "Monica", "Jamie", "Rhonda", "Steven", "Perry", "Byron", "Laura", "Harry", "Brooke", "Drew",
				"Vicki", "Gary", "Anita", "Felipe", "Josie" };
		String[] alOfLastNames = { "Smith", "Johnson", "Williams", "Jones", "Brown", "Davis", "Miller", "Wilson",
				"Moore", "Taylor", "Washington", "Jefferson", "Lincoln", "Hamilton", "Jackson", "Grant", "Franklin",
				"McKinley", "Cleveland", "Madison", "Chase", "Nicholson", "Fauver", "Doe", "Southard", "Schmidt",
				"Hodson", "McDonald", "Stickley", "Miller", "Combs", "Bohus", "Krippner", "Amtower", "Banks", "Wallace",
				"Bannister", "Dehaven", "Yost", "Still", "Timbrook", "Peters", "Vaught", "Shellhammer", "Andrews",
				"Krippner", "McAlister", "Wright", "Kensinger", "McClellan", "Ganoe", "Shiley", "Layman", "Gearhart",
				"Yost", "Kushnir", "Bush", "Lowder", "Connolly", "Lowman", "Terveen", "Staton", "Settle", "Tinsman",
				"Nichols", "Baker", "Walters", "Dawe", "Renner", "Michaels", "Faircloth", "Looker", "Hastings",
				"Vaughan", "Anderson", "Zimmerman", "Deere", "Daher", "Lauck", "Stottlemyer", "Clinton", "Obama",
				"Reagan", "Montgomery", "Pugh", "Gavis", "Clark", "Bowers" };
		String sFirstName = alOfFirstNames[RandomUtil.getRandomPrimitiveInt(Const.ZERO,
				alOfFirstNames.length - Const.ONE)];
		String sLastName = alOfLastNames[RandomUtil.getRandomPrimitiveInt(Const.ZERO,
				alOfLastNames.length - Const.ONE)];
		char sMiddleName = RandomUtil.getRandomChar(Const.CHAR_A, Const.CHAR_Z);
		if (name == Const.NAME) {
			return sFirstName + " " + sMiddleName + " " + sLastName;
		} else if (name == Const.FIRST_NAME) {
			return sFirstName;
		} else if (name == Const.LAST_NAME) {
			return sLastName;
		} else if (name == Const.MIDDLE_NAME) {
			return String.valueOf(sMiddleName);
		} else {
			return null;
		}
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
