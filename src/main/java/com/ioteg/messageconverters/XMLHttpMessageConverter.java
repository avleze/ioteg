package com.ioteg.messageconverters;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import com.ioteg.resultmodel.ArrayResultBlock;
import com.ioteg.resultmodel.ResultBlock;
import com.ioteg.resultmodel.ResultComplexField;
import com.ioteg.resultmodel.ResultEvent;
import com.ioteg.resultmodel.ResultSimpleField;
import com.ioteg.resultmodel.xmlserializers.XMLArrayResultBlockSerializer;
import com.ioteg.resultmodel.xmlserializers.XMLResultBlockSerializer;
import com.ioteg.resultmodel.xmlserializers.XMLResultComplexFieldSerializer;
import com.ioteg.resultmodel.xmlserializers.XMLResultEventListSerializer;
import com.ioteg.resultmodel.xmlserializers.XMLResultEventSerializer;
import com.ioteg.resultmodel.xmlserializers.XMLResultSimpleFieldSerializer;
import com.ioteg.resultmodel.xmlserializers.XMLSerializerMapper;

public class XMLHttpMessageConverter extends AbstractHttpMessageConverter<Object> {

	private static XMLSerializerMapper xmlSerializerMapper;
	private static Set<String> supportedTypes;
	
	static {
		supportedTypes = new TreeSet<>();
		supportedTypes.add(ResultEvent.class.getSimpleName());
		supportedTypes.add(ResultBlock.class.getSimpleName());
		supportedTypes.add(ResultComplexField.class.getSimpleName());
		supportedTypes.add(ArrayList.class.getSimpleName());

		xmlSerializerMapper = new XMLSerializerMapper();
		xmlSerializerMapper.registerCustomSerializer(ResultEvent.class, new XMLResultEventSerializer());
		xmlSerializerMapper.registerCustomSerializer(ArrayResultBlock.class, new XMLArrayResultBlockSerializer());
		xmlSerializerMapper.registerCustomSerializer(ResultBlock.class, new XMLResultBlockSerializer());
		xmlSerializerMapper.registerCustomSerializer(ResultSimpleField.class, new XMLResultSimpleFieldSerializer());
		xmlSerializerMapper.registerCustomSerializer(ResultComplexField.class, new XMLResultComplexFieldSerializer());
		xmlSerializerMapper.registerCustomSerializer(ArrayList.class , new XMLResultEventListSerializer());

	}

	public XMLHttpMessageConverter() {
         super(new MediaType("application", "xml", StandardCharsets.UTF_8));
	}

	@Override
	protected boolean supports(Class<?> clazz) {		
		return supportedTypes.contains(clazz.getSimpleName());
	}

	@Override
	protected Object readInternal(Class<? extends Object> clazz, HttpInputMessage inputMessage)
			throws IOException {

		return null;
	}

	@Override
	protected void writeInternal(Object t, HttpOutputMessage outputMessage)
			throws IOException {
		outputMessage.getBody().write(xmlSerializerMapper.writeValueAsString(t).getBytes());
	}

}
