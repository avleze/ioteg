package com.ioteg.resultmodel.csvserializers;

import java.io.IOException;

/**
 * <p>CSVSerializer interface.</p>
 *
 * @author antonio
 * @version $Id: $Id
 */
public interface CSVSerializer<T> {
	/**
	 * <p>serialize.</p>
	 *
	 * @param value a T object.
	 * @param csvGen a {@link com.ioteg.resultmodel.csvserializers.CSVGenerator} object.
	 * @throws java.io.IOException if any.
	 */
	public void serialize(T value, CSVGenerator csvGen) throws IOException;
}
