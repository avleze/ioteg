package com.ioteg.resultmodel.csvserializers;

import java.io.IOException;

public interface CSVSerializer<T> {
	public void serialize(T value, CSVGenerator csvGen) throws IOException;
}
