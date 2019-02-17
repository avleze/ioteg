package com.ioteg.resultmodel.xmlserializers;

import java.io.IOException;

public interface XMLSerializer<T> {
	public void serialize(T value, XMLGenerator xmlGen) throws IOException;
}
