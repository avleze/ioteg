package com.ioteg;

import java.util.ArrayList;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.ioteg.http.converters.CSVResultEventHttpMessageConverter;
import com.ioteg.http.converters.XMLHttpMessageConverter;
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
 * <p>
 * Application class.
 * </p>
 *
 * @author antonio
 * @version $Id: $Id
 */
@SpringBootApplication
public class Application {


	/**
	 * <p>
	 * main.
	 * </p>
	 *
	 * @param args an array of {@link java.lang.String} objects.
	 */
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	/**
	 * <p>
	 * additionalConverters.
	 * </p>
	 *
	 * @return a
	 *         {@link org.springframework.boot.autoconfigure.http.HttpMessageConverters}
	 *         object.
	 */
	@Bean
	public HttpMessageConverters additionalConverters() {
		return new HttpMessageConverters(new XMLHttpMessageConverter(xmlSerializerMapper()), new CSVResultEventHttpMessageConverter());
	}

	@Bean
	public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}
	
	@Bean
	public XMLSerializerMapper xmlSerializerMapper() {
		XMLSerializerMapper xmlSerializerMapper = new XMLSerializerMapper();
		xmlSerializerMapper.registerCustomSerializer(ResultEvent.class, new XMLResultEventSerializer());
		xmlSerializerMapper.registerCustomSerializer(ArrayResultBlock.class, new XMLArrayResultBlockSerializer());
		xmlSerializerMapper.registerCustomSerializer(ResultBlock.class, new XMLResultBlockSerializer());
		xmlSerializerMapper.registerCustomSerializer(ResultSimpleField.class, new XMLResultSimpleFieldSerializer());
		xmlSerializerMapper.registerCustomSerializer(ResultComplexField.class, new XMLResultComplexFieldSerializer());
		xmlSerializerMapper.registerCustomSerializer(ArrayList.class , new XMLResultEventListSerializer());
		return xmlSerializerMapper;
	}
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	
	
}
