package com.ioteg.resultmodel.xmlserializers;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>XMLSerializerMapper class.</p>
 *
 * @author antonio
 * @version $Id: $Id
 */
public class XMLSerializerMapper {
	
	private static Map<String, XMLSerializer<?>> customSerializers;
	
	static {
		customSerializers = new HashMap<>();
	}
	
	/**
	 * <p>writeValueAsString.</p>
	 *
	 * @param object a T object.
	 * @param <T> a T object.
	 * @return a {@link java.lang.String} object.
	 * @throws java.io.IOException if any.
	 */
	public <T> String writeValueAsString(T object) throws IOException {
		@SuppressWarnings("unchecked")
		XMLSerializer<T> serializer = (XMLSerializer<T>) customSerializers.get(object.getClass().getName());
		OutputStream out = new ByteArrayOutputStream();
		XMLGenerator xmlGenerator = new XMLGenerator(out);
		
		if(serializer != null)
			serializer.serialize(object, xmlGenerator);
		
		xmlGenerator.close();
		return out.toString();
	}
	
	/**
	 * <p>registerCustomSerializer.</p>
	 *
	 * @param clazz a {@link java.lang.Class} object.
	 * @param serializer a {@link com.ioteg.resultmodel.xmlserializers.XMLSerializer} object.
	 */
	public void registerCustomSerializer(Class<?> clazz, XMLSerializer<?> serializer) {
		customSerializers.put(clazz.getName(), serializer);
	}
}
