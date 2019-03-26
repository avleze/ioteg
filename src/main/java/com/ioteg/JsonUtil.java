package com.ioteg;

import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;

import org.jdom2.Document;
import org.jdom2.JDOMException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.ioteg.builders.EventTypeBuilder;
import com.ioteg.exprlang.ExprParser.ExprLangParsingException;
import com.ioteg.generators.exceptions.NotExistingGeneratorException;
import com.ioteg.model.EventType;
import com.ioteg.resultmodel.ArrayResultBlock;
import com.ioteg.resultmodel.ResultBlock;
import com.ioteg.resultmodel.ResultComplexField;
import com.ioteg.resultmodel.ResultEvent;
import com.ioteg.resultmodel.ResultSimpleField;
import com.ioteg.resultmodel.jsonserializers.ArrayResultBlockSerializer;
import com.ioteg.resultmodel.jsonserializers.ResultBlockSerializer;
import com.ioteg.resultmodel.jsonserializers.ResultComplexFieldSerializer;
import com.ioteg.resultmodel.jsonserializers.ResultEventSerializer;
import com.ioteg.resultmodel.jsonserializers.ResultSimpleFieldSerializer;

public class JsonUtil {

	private JsonUtil() {
		
	}
	
	public static void jsonFormatValues(FileWriter values, Document document) throws IOException, JDOMException, NotExistingGeneratorException, ExprLangParsingException, ParseException {
		EventTypeBuilder eventTypeBuilder = new EventTypeBuilder();
		EventType eventType = eventTypeBuilder.build(document);
		
		SimpleModule module = new SimpleModule();
		ObjectMapper jsonSerializer = new ObjectMapper();
		module.addSerializer(ArrayResultBlock.class, new ArrayResultBlockSerializer(null));
		module.addSerializer(ResultBlock.class, new ResultBlockSerializer(null));
		module.addSerializer(ResultEvent.class, new ResultEventSerializer(null));
		module.addSerializer(ResultSimpleField.class, new ResultSimpleFieldSerializer(null));
		module.addSerializer(ResultComplexField.class, new ResultComplexFieldSerializer(null));

		jsonSerializer.registerModule(module);

		ResultEvent resultEvent = EventGenerator.generateEvent(eventType);

		values.write(jsonSerializer.writeValueAsString(resultEvent));
		values.close();
	}
}