package com.ioteg;



import java.io.File;
import java.io.IOException;
import org.jdom2.Document;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.ioteg.EventGenerator;
import com.ioteg.builders.EventTypeBuilder;
import com.ioteg.model.EventType;
import com.ioteg.resultmodel.ArrayResultBlock;
import com.ioteg.resultmodel.ResultBlock;
import com.ioteg.resultmodel.ResultComplexField;
import com.ioteg.resultmodel.ResultEvent;
import com.ioteg.resultmodel.ResultSimpleField;
import com.ioteg.resultmodel.serializers.ArrayResultBlockSerializer;
import com.ioteg.resultmodel.serializers.ResultBlockSerializer;
import com.ioteg.resultmodel.serializers.ResultComplexFieldSerializer;
import com.ioteg.resultmodel.serializers.ResultEventSerializer;
import com.ioteg.resultmodel.serializers.ResultSimpleFieldSerializer;


public class ComplexTypeGeneratorResultTestCase {

	private static Document document;
	private static ClassLoader classLoader;

	@BeforeEach
	public void loadSchema() throws JDOMException, IOException {
		SAXBuilder builder = new SAXBuilder();
		classLoader = ComplexTypeGeneratorResultTestCase.class.getClassLoader();
		File xmlFile = new File(classLoader.getResource("./testEventType2.xml").getFile());
		document = builder.build(xmlFile);
	}

	@Test
	public void testGenerateResultEvent() throws IOException, JDOMException {		

		EventTypeBuilder eventTypeBuilder = new EventTypeBuilder();
		EventType eventType = eventTypeBuilder.build(document);
		ResultEvent result = EventGenerator.generateEvent(eventType);
		SimpleModule module = new SimpleModule();
		ObjectMapper jsonSerializer = new ObjectMapper();

		module.addSerializer(ArrayResultBlock.class, new ArrayResultBlockSerializer(null));
		module.addSerializer(ResultBlock.class, new ResultBlockSerializer(null));
		module.addSerializer(ResultEvent.class, new ResultEventSerializer(null));
		module.addSerializer(ResultSimpleField.class, new ResultSimpleFieldSerializer(null));
		module.addSerializer(ResultComplexField.class, new ResultComplexFieldSerializer(null));

		jsonSerializer.registerModule(module);

		
		jsonSerializer.enable(SerializationFeature.INDENT_OUTPUT);
		jsonSerializer.writeValue(System.out, result);
		/*
		assertThat(result.getName(), equalTo("Channel 5186"));
		assertThat(result.getResultBlocks(), not(nullValue()));
		assertThat(result.getResultBlocks().size(), equalTo(3));
		
		ResultBlock resultBlock = result.getResultBlocks().get(0);
		
		assertThat(resultBlock.getName(), equalTo("channel"));
		assertThat(resultBlock.getResultFields(), not(nullValue()));
		assertThat(resultBlock.getResultFields().size(), equalTo(11));
		
		ResultField resultField = resultBlock.getResultFields().get(0);
		assertThat(resultField, instanceOf(ResultSimpleField.class));
		ResultSimpleField resultSimpleField = (ResultSimpleField) resultField;
		
		assertThat(resultSimpleField.getName(), equalTo("id"));
		assertThat(resultSimpleField.getValue(), equalTo("5186"));
		
		resultField = resultBlock.getResultFields().get(1);
		assertThat(resultField, instanceOf(ResultSimpleField.class));
		resultSimpleField = (ResultSimpleField) resultField;
		
		assertThat(resultSimpleField.getName(), equalTo("name"));
		assertThat(resultSimpleField.getValue(), equalTo("Channel 5186"));
		
		resultField = resultBlock.getResultFields().get(2);
		assertThat(resultField, instanceOf(ResultSimpleField.class));
		resultSimpleField = (ResultSimpleField) resultField;
		
		assertThat(resultSimpleField.getName(), equalTo("description"));
		assertThat(resultSimpleField.getValue(), equalTo("Generated event type Channel 5186"));

		resultField = resultBlock.getResultFields().get(3);
		assertThat(resultField, instanceOf(ResultSimpleField.class));
		resultSimpleField = (ResultSimpleField) resultField;
		
		assertThat(resultSimpleField.getName(), equalTo("latitude"));
		assertThat(resultSimpleField.getValue(), equalTo("47.528936"));
		
		resultField = resultBlock.getResultFields().get(4);
		assertThat(resultField, instanceOf(ResultSimpleField.class));
		resultSimpleField = (ResultSimpleField) resultField;
		
		assertThat(resultSimpleField.getName(), equalTo("longitude"));
		assertThat(resultSimpleField.getValue(), equalTo("10.257247"));
		
		resultField = resultBlock.getResultFields().get(5);
		assertThat(resultField, instanceOf(ResultSimpleField.class));
		resultSimpleField = (ResultSimpleField) resultField;
		
		assertThat(resultSimpleField.getName(), equalTo("f"));
		assertThat(resultSimpleField.getValue(), equalTo("10.257247"));*/
	}
	
	

}
