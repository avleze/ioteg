package com.ioteg.serializers.json;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;

import org.jdom2.Document;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.ioteg.builders.EventTypeBuilder;
import com.ioteg.exprlang.ExprParser.ExprLangParsingException;
import com.ioteg.generation.EventTypeGenerationAlgorithm;
import com.ioteg.generation.EventTypeGenerator;
import com.ioteg.generation.GenerationContext;
import com.ioteg.generation.NotExistingGeneratorException;
import com.ioteg.model.EventType;
import com.ioteg.resultmodel.ArrayResultBlock;
import com.ioteg.resultmodel.ResultBlock;
import com.ioteg.resultmodel.ResultComplexField;
import com.ioteg.resultmodel.ResultEvent;
import com.ioteg.resultmodel.ResultSimpleField;
import com.ioteg.serializers.csv.CsvUtilTestCase;
import com.ioteg.serializers.json.ArrayResultBlockSerializer;
import com.ioteg.serializers.json.ResultBlockSerializer;
import com.ioteg.serializers.json.ResultComplexFieldSerializer;
import com.ioteg.serializers.json.ResultEventSerializer;
import com.ioteg.serializers.json.ResultSimpleFieldSerializer;

import static org.hamcrest.Matchers.matchesPattern;
import static org.hamcrest.MatcherAssert.assertThat;

public class JsonSerializerTestCase {

	private static File tempFile;
	private FileWriter values;
	private static SAXBuilder builder;
	private static ClassLoader classLoader;
	private static ObjectMapper jsonSerializer;
	private static EventTypeBuilder eventTypeBuilder;

	@BeforeAll
	public static void setup() {
		builder = new SAXBuilder();
		classLoader = CsvUtilTestCase.class.getClassLoader();
		eventTypeBuilder = new EventTypeBuilder();
		SimpleModule module = new SimpleModule();
		jsonSerializer = new ObjectMapper();
		module.addSerializer(ArrayResultBlock.class, new ArrayResultBlockSerializer(null));
		module.addSerializer(ResultBlock.class, new ResultBlockSerializer(null));
		module.addSerializer(ResultEvent.class, new ResultEventSerializer(null));
		module.addSerializer(ResultSimpleField.class, new ResultSimpleFieldSerializer(null));
		module.addSerializer(ResultComplexField.class, new ResultComplexFieldSerializer(null));

		jsonSerializer.registerModule(module);
	}

	@BeforeEach
	public void loadSchema() throws JDOMException, IOException {
		tempFile = File.createTempFile("temp", "file");
		values = new FileWriter(tempFile);
	}

	@Test
	public void testJsonComplexField()
			throws IOException, JDOMException, NotExistingGeneratorException, ExprLangParsingException, ParseException {
		File xmlFile = new File(classLoader.getResource("./FormatValueTestFiles/testFormatValues.xml").getFile());
		Document doc = builder.build(xmlFile);

		EventType eventType = eventTypeBuilder.build(doc);
		GenerationContext context = new GenerationContext();
		EventTypeGenerator eventTypeGenerator = new EventTypeGenerator(
				new EventTypeGenerationAlgorithm(eventType, context), context);

		String result = jsonSerializer.writeValueAsString(eventTypeGenerator.generate(1).get(0));

		String resultRegex = "\\{\"testFormatValues\":\\[" + "\\{\"lugar\":\\{"
				+ "\"nombre\":[ABCDEFGHIJKLMNOPQRSTUVWXYZ]{4}," + "\"latitud\":\"-?\\d+\\.\\d{5}\","
				+ "\"longitud\":-?\\d+\\.\\d{5}\\}\\}\\]\\}";

		assertThat(result, matchesPattern(resultRegex));

	}

	@Test
	public void testJsonComplexFieldNotRepeatTag()
			throws IOException, JDOMException, NotExistingGeneratorException, ExprLangParsingException, ParseException {
		File xmlFile = new File(
				classLoader.getResource("./FormatValueTestFiles/testFormatValuesNotRepeatTag.xml").getFile());
		Document doc = builder.build(xmlFile);

		EventType eventType = eventTypeBuilder.build(doc);

		GenerationContext context = new GenerationContext();
		EventTypeGenerator eventTypeGenerator = new EventTypeGenerator(
				new EventTypeGenerationAlgorithm(eventType, context), context);

		String result = jsonSerializer.writeValueAsString(eventTypeGenerator.generate(1).get(0));
		String resultRegex = "\\{\"testFormatValues\":" + "\\{\"lugar\":\\{"
				+ "\"nombre\":[ABCDEFGHIJKLMNOPQRSTUVWXYZ]{4}," + "\"latitud\":\"-?\\d+\\.\\d{5}\","
				+ "\"longitud\":-?\\d+\\.\\d{5}\\}\\}\\}";

		assertThat(result, matchesPattern(resultRegex));

	}

	@Test
	public void testJsonComplexFieldWithOptionalFields()
			throws IOException, JDOMException, NotExistingGeneratorException, ExprLangParsingException, ParseException {
		File xmlFile = new File(
				classLoader.getResource("./FormatValueTestFiles/testFormatValuesWithOptionalFields.xml").getFile());
		Document doc = builder.build(xmlFile);

		EventType eventType = eventTypeBuilder.build(doc);

		GenerationContext context = new GenerationContext();
		EventTypeGenerator eventTypeGenerator = new EventTypeGenerator(new EventTypeGenerationAlgorithm(eventType, context), context);
	
		String result = jsonSerializer.writeValueAsString(eventTypeGenerator.generate(1).get(0));
		String resultRegex = "\\{\"testFormatValues\":\\[" + "\\{\"lugar\":\\{"
				+ "\"nombre\":[ABCDEFGHIJKLMNOPQRSTUVWXYZ]{4}," + "\"latitud\":\"-?\\d+\\.\\d{5}\","
				+ "\"longitud\":-?\\d+\\.\\d{5}\\}(,\"nombreOpcional\":[ABCDEFGHIJKLMNOPQRSTUVWXYZ]{4})?(,\"cadenaOpcional\":\"[ABCDEFGHIJKLMNOPQRSTUVWXYZ]{4}\")?\\}\\]\\}";

		assertThat(result, matchesPattern(resultRegex));

	}

	@AfterEach
	public void teardown() throws IOException {
		tempFile.delete();
		values.close();
	}

}
