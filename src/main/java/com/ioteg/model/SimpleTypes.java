package com.ioteg.model;

import java.util.Set;
import java.util.TreeSet;

/**
 * <p>SimpleTypes class.</p>
 *
 * @author antonio
 * @version $Id: $Id
 */
public class SimpleTypes {
	
	private static final Set<String> types;
	static {
		types = new TreeSet<>();
		types.add("Integer");
		types.add("Float");
		types.add("Long");
		types.add("Boolean");
		types.add("String");
		types.add("Alphanumeric");
		types.add("Date");
		types.add("Time");
	}
	
	private SimpleTypes() {
		
	}
	
	/**
	 * <p>exists.</p>
	 *
	 * @param object a {@link java.lang.Object} object.
	 * @return a boolean.
	 */
	public static boolean exists(Object object) {
		return types.contains(object);
	}
}
