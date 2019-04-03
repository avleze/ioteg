package com.ioteg.http.converters;

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
import com.ioteg.serializers.xml.XMLArrayResultBlockSerializer;
import com.ioteg.serializers.xml.XMLResultBlockSerializer;
import com.ioteg.serializers.xml.XMLResultComplexFieldSerializer;
import com.ioteg.serializers.xml.XMLResultEventListSerializer;
import com.ioteg.serializers.xml.XMLResultEventSerializer;
import com.ioteg.serializers.xml.XMLResultSimpleFieldSerializer;
import com.ioteg.serializers.xml.XMLSerializerMapper;

/**
 * <p>XMLHttpMessageConverter class.</p>
 *
 * @author antonio
 * @version $Id: $Id
 */
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

	/**
	 * <p>Constructor for XMLHttpMessageConverter.</p>
	 */
	public XMLHttpMessageConverter() {
         super(new MediaType("application", "xml", StandardCharsets.UTF_8));
	}

	/** {@inheritDoc} */
	@Override
	protected boolean supports(Class<?> clazz) {		
		return supportedTypes.contains(clazz.getSimpleName());
	}

	/** {@inheritDoc} */
	@Override
	protected Object readInternal(Class<? extends Object> clazz, HttpInputMessage inputMessage)
			throws IOException {

		return null;
	}

	/** {@inheritDoc} */
	@Override
	protected void writeInternal(Object t, HttpOutputMessage outputMessage)
			throws IOException {
		outputMessage.getBody().write(xmlSerializerMapper.writeValueAsString(t).getBytes());
	}

}
