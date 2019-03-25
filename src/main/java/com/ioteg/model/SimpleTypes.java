package com.ioteg.model;

import java.util.Set;
import java.util.TreeSet;

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
	
	public static boolean exists(Object object) {
		return types.contains(object);
	}
}
