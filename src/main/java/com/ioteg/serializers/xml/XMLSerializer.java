package com.ioteg.serializers.xml;

import java.io.IOException;

/**
 * <p>XMLSerializer interface.</p>
 *
 * @author antonio
 * @version $Id: $Id
 */
public interface XMLSerializer<T> {
	/**
	 * <p>serialize.</p>
	 *
	 * @param value a T object.
	 * @param xmlGen a {@link com.ioteg.serializers.xml.XMLGenerator} object.
	 * @throws java.io.IOException if any.
	 */
	public void serialize(T value, XMLGenerator xmlGen) throws IOException;
}
