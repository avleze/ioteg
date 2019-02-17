package com.ioteg.generators;

import java.io.IOException;
import java.util.List;

import com.ioteg.generators.exceptions.NotExistingGeneratorException;
import com.ioteg.resultmodel.ResultField;

public interface Generable {
	public List<ResultField> generate(Integer numberOfRequiredItems) throws NotExistingGeneratorException, IOException;
}
