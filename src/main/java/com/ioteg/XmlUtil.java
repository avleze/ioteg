package com.ioteg;

import java.io.FileWriter;
import java.io.IOException;

import org.jdom2.Document;
import org.jdom2.JDOMException;

import com.ioteg.builders.EventTypeBuilder;
import com.ioteg.exprlang.ExprParser.ExprLangParsingException;
import com.ioteg.generators.exceptions.NotExistingGeneratorException;
import com.ioteg.model.EventType;
import com.ioteg.resultmodel.ArrayResultBlock;
import com.ioteg.resultmodel.ResultBlock;
import com.ioteg.resultmodel.ResultComplexField;
import com.ioteg.resultmodel.ResultEvent;
import com.ioteg.resultmodel.ResultSimpleField;
import com.ioteg.resultmodel.xmlserializers.XMLArrayResultBlockSerializer;
import com.ioteg.resultmodel.xmlserializers.XMLResultBlockSerializer;
import com.ioteg.resultmodel.xmlserializers.XMLResultComplexFieldSerializer;
import com.ioteg.resultmodel.xmlserializers.XMLResultEventSerializer;
import com.ioteg.resultmodel.xmlserializers.XMLResultSimpleFieldSerializer;
import com.ioteg.resultmodel.xmlserializers.XMLSerializerMapper;

public class XmlUtil extends EventGenerator {

	public static void xmlFormatValues(FileWriter values, Document document) throws IOException, JDOMException, NotExistingGeneratorException, ExprLangParsingException {
		EventTypeBuilder eventTypeBuilder = new EventTypeBuilder();
		EventType eventType = eventTypeBuilder.build(document);
		XMLSerializerMapper xmlSerializerMapper = new XMLSerializerMapper();
		
		xmlSerializerMapper.registerCustomSerializer(ResultEvent.class, new XMLResultEventSerializer());
		xmlSerializerMapper.registerCustomSerializer(ArrayResultBlock.class, new XMLArrayResultBlockSerializer());
		xmlSerializerMapper.registerCustomSerializer(ResultBlock.class, new XMLResultBlockSerializer());
		xmlSerializerMapper.registerCustomSerializer(ResultSimpleField.class, new XMLResultSimpleFieldSerializer());
		xmlSerializerMapper.registerCustomSerializer(ResultComplexField.class, new XMLResultComplexFieldSerializer());
		
		ResultEvent rEvent = EventGenerator.generateEvent(eventType);

		String result = xmlSerializerMapper.writeValueAsString(rEvent);
		
		values.write(result);
		
		values.close();
	}
}
