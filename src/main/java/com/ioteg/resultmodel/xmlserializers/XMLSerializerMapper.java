package com.ioteg.resultmodel.xmlserializers;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class XMLSerializerMapper {
	
	private static Map<String, XMLSerializer<?>> customSerializers;
	
	static {
		customSerializers = new ConcurrentHashMap<>();
	}
	
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
	
	public void registerCustomSerializer(Class<?> clazz, XMLSerializer<?> serializer) {
		customSerializers.put(clazz.getName(), serializer);
	}
}
