package com.ioteg.generators;

import com.ioteg.model.Field;

public interface GenerationAlgorithm<T> {
	public T generate(Field field);
}
