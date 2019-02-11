package com.ioteg.generators;

import java.util.List;

import com.ioteg.resultmodel.ResultField;

public interface Generable {
	public List<ResultField> generate(Integer numberOfRequiredItems);
}
