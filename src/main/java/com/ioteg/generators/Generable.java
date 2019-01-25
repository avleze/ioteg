package com.ioteg.generators;

import java.util.List;

import com.ioteg.model.Field;

public interface Generable {
	public List<String> generate(Field field, Integer numberOfRequiredItems);
}
