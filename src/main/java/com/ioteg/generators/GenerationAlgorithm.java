package com.ioteg.generators;

import com.ioteg.model.Field;

public abstract class GenerationAlgorithm<T> {
	public abstract T generate(Field field);
}
