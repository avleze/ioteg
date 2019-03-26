package com.ioteg;

import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;

import org.jdom2.Document;
import org.jdom2.JDOMException;

import com.ioteg.builders.EventTypeBuilder;
import com.ioteg.exprlang.ExprParser.ExprLangParsingException;
import com.ioteg.generators.exceptions.NotExistingGeneratorException;
import com.ioteg.model.EventType;
import com.ioteg.resultmodel.ResultEvent;
import com.ioteg.resultmodel.csvserializers.CSVUtil;

public class CsvUtil {

	private CsvUtil() {
		
	}
	
	public static void csvFormatValues(FileWriter values, Document document) throws IOException, NotExistingGeneratorException, ExprLangParsingException, JDOMException, ParseException {

		EventTypeBuilder eventTypeBuilder = new EventTypeBuilder();
		EventType eventType = eventTypeBuilder.build(document);
		ResultEvent resultEvent = EventGenerator.generateEvent(eventType);

		values.write(CSVUtil.serializeResultEvent(eventType, resultEvent));
		values.close(); 
	}

	
}
