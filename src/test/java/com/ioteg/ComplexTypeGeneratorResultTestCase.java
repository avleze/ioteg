package com.ioteg;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.jdom2.Document;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.ioteg.builders.EventTypeBuilder;
import com.ioteg.exprlang.ExprParser.ExprLangParsingException;
import com.ioteg.generators.context.GenerationContext;
import com.ioteg.generators.eventtype.EventTypeGenerationAlgorithm;
import com.ioteg.generators.eventtype.EventTypeGenerator;
import com.ioteg.generators.exceptions.NotExistingGeneratorException;
import com.ioteg.model.EventType;
import com.ioteg.resultmodel.ArrayResultBlock;
import com.ioteg.resultmodel.ResultBlock;
import com.ioteg.resultmodel.ResultComplexField;
import com.ioteg.resultmodel.ResultEvent;
import com.ioteg.resultmodel.ResultField;
import com.ioteg.resultmodel.ResultSimpleField;

import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.matchesPattern;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.lessThanOrEqualTo;

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
	public void testGenerateResultEvent() throws IOException, JDOMException, ParseException, NotExistingGeneratorException, ExprLangParsingException {

		EventTypeBuilder eventTypeBuilder = new EventTypeBuilder();
		EventType eventType = eventTypeBuilder.build(document);
		GenerationContext context = new GenerationContext();
		EventTypeGenerator eventTypeGenerator = new EventTypeGenerator(new EventTypeGenerationAlgorithm(eventType, context), context);
		ResultEvent result = eventTypeGenerator.generate(1).get(0);

		assertThat(result.getName(), equalTo("Channel 5186"));
		assertThat(result.getArrayResultBlocks(), not(nullValue()));
		assertThat(result.getArrayResultBlocks().size(), equalTo(2));

		ResultBlock resultBlock = result.getArrayResultBlocks().get(0).getResultBlocks().get(0);

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
		assertThat(resultSimpleField.getValue(), equalTo("47.52896"));

		resultField = resultBlock.getResultFields().get(4);
		assertThat(resultField, instanceOf(ResultSimpleField.class));
		resultSimpleField = (ResultSimpleField) resultField;

		assertThat(resultSimpleField.getName(), equalTo("longitude"));
		assertThat(resultSimpleField.getValue(), equalTo("10.257247"));

		resultField = resultBlock.getResultFields().get(5);
		assertThat(resultField, instanceOf(ResultSimpleField.class));
		resultSimpleField = (ResultSimpleField) resultField;

		assertThat(resultSimpleField.getName(), equalTo("field1"));
		assertThat(resultSimpleField.getValue(), matchesPattern("0|1"));
		
		resultField = resultBlock.getResultFields().get(6);
		assertThat(resultField, instanceOf(ResultSimpleField.class));
		resultSimpleField = (ResultSimpleField) resultField;

		assertThat(resultSimpleField.getName(), equalTo("field2"));
		assertThat(resultSimpleField.getValue(), matchesPattern("[0123456789ABCDEF]*"));
		
		resultField = resultBlock.getResultFields().get(7);
		assertThat(resultField, instanceOf(ResultSimpleField.class));
		resultSimpleField = (ResultSimpleField) resultField;

		assertThat(resultSimpleField.getName(), equalTo("field3"));
		SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-DD");
		sdf.parse(resultSimpleField.getValue());
		
		resultField = resultBlock.getResultFields().get(8);
		assertThat(resultField, instanceOf(ResultSimpleField.class));
		resultSimpleField = (ResultSimpleField) resultField;

		assertThat(resultSimpleField.getName(), equalTo("created_at"));
		assertThat(resultSimpleField.getValue(), equalTo("2013-04-04T12:12:05Z"));
		
		resultField = resultBlock.getResultFields().get(9);
		assertThat(resultField, instanceOf(ResultSimpleField.class));
		resultSimpleField = (ResultSimpleField) resultField;

		assertThat(resultSimpleField.getName(), equalTo("updated_at"));
		assertThat(resultSimpleField.getValue(), equalTo("2016-04-19T07:39:07Z"));
		
		resultField = resultBlock.getResultFields().get(10);
		assertThat(resultField, instanceOf(ResultSimpleField.class));
		resultSimpleField = (ResultSimpleField) resultField;

		assertThat(resultSimpleField.getName(), equalTo("last_entry_id"));
		assertThat(resultSimpleField.getValue(), equalTo("1201242"));
		
		ArrayResultBlock resultBlocks = result.getArrayResultBlocks().get(1);
		
		
		assertThat(resultBlocks.getResultBlocks().size(), equalTo(300));

		for (ResultBlock rB : resultBlocks.getResultBlocks()) {
			resultField = rB.getResultFields().get(0);
			assertThat(resultField, instanceOf(ResultComplexField.class));
			ResultComplexField resultComplexField = (ResultComplexField) resultField;

			assertThat(resultComplexField.getName(), equalTo("create_at"));
			assertThat(resultComplexField.getValue(), not(nullValue()));
			
			resultField = resultComplexField.getValue().get(0);
			assertThat(resultField, instanceOf(ResultSimpleField.class));
			resultSimpleField = (ResultSimpleField) resultField;
			
			assertThat(resultSimpleField.getName(), nullValue());
			if(!resultSimpleField.getType().equalsIgnoreCase("Time"))
				assertThat(resultSimpleField.getValue(), matchesPattern("2016|-|5|-|14|T|Z"));
			else
			{
				sdf = new SimpleDateFormat("hh:mm:ss");
				sdf.parse(resultSimpleField.getValue());
			}
			
			resultField = rB.getResultFields().get(1);
			assertThat(resultField, instanceOf(ResultSimpleField.class));
			resultSimpleField = (ResultSimpleField) resultField;
			
			assertThat(resultSimpleField.getName(), equalTo("entry_id"));
			assertThat(resultSimpleField.getType(), equalTo("Integer"));
			assertThat(Integer.valueOf(resultSimpleField.getValue()), greaterThanOrEqualTo(100000));
			assertThat(Integer.valueOf(resultSimpleField.getValue()), lessThanOrEqualTo(999999));
			
			resultField = rB.getResultFields().get(2);
			assertThat(resultField, instanceOf(ResultSimpleField.class));
			resultSimpleField = (ResultSimpleField) resultField;
			
			assertThat(resultSimpleField.getName(), equalTo("field1"));
			assertThat(resultSimpleField.getType(), equalTo("Float"));
			assertThat(Float.valueOf(resultSimpleField.getValue()), greaterThanOrEqualTo(19f));
			assertThat(Float.valueOf(resultSimpleField.getValue()), lessThanOrEqualTo(20f));
			assertThat(resultSimpleField.getValue(), matchesPattern("\\d{2}\\.\\d{2}"));
			
			resultField = rB.getResultFields().get(3);
			assertThat(resultField, instanceOf(ResultSimpleField.class));
			resultSimpleField = (ResultSimpleField) resultField;
			
			assertThat(resultSimpleField.getName(), equalTo("field2"));
			assertThat(resultSimpleField.getType(), equalTo("Float"));
			assertThat(Float.valueOf(resultSimpleField.getValue()), greaterThanOrEqualTo(5f));
			assertThat(Float.valueOf(resultSimpleField.getValue()), lessThanOrEqualTo(10f));
			assertThat(resultSimpleField.getValue(), matchesPattern("\\d{1,2}\\.\\d{2}"));
			
			resultField = rB.getResultFields().get(4);
			assertThat(resultField, instanceOf(ResultSimpleField.class));
			resultSimpleField = (ResultSimpleField) resultField;
			
			assertThat(resultSimpleField.getName(), equalTo("field3"));
			assertThat(resultSimpleField.getType(), equalTo("Float"));
			assertThat(Float.valueOf(resultSimpleField.getValue()), greaterThanOrEqualTo(40f));
			assertThat(Float.valueOf(resultSimpleField.getValue()), lessThanOrEqualTo(50f));
			assertThat(resultSimpleField.getValue(), matchesPattern("\\d{2}\\.\\d{2}"));
			
			if(rB.getResultFields().size() == 6)
			{
				resultField = rB.getResultFields().get(5);
				assertThat(resultField, instanceOf(ResultSimpleField.class));
				resultSimpleField = (ResultSimpleField) resultField;
				
				assertThat(resultSimpleField.getName(), equalTo("optionalField1"));
				assertThat(resultSimpleField.getType(), equalTo("Float"));
				assertThat(Float.valueOf(resultSimpleField.getValue()), greaterThanOrEqualTo(19f));
				assertThat(Float.valueOf(resultSimpleField.getValue()), lessThanOrEqualTo(20f));
				assertThat(resultSimpleField.getValue(), matchesPattern("\\d{2}\\.\\d{2}"));
			}
		}
		
	}

}
