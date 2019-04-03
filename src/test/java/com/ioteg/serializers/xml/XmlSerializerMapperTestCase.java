package com.ioteg.serializers.xml;

import static org.hamcrest.Matchers.matchesPattern;
import static org.hamcrest.MatcherAssert.assertThat;

import java.io.File;
import java.io.FileWriter;
import org.jdom2.Document;
import org.jdom2.input.SAXBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.ioteg.builders.EventTypeBuilder;
import com.ioteg.generation.EventTypeGenerationAlgorithm;
import com.ioteg.generation.EventTypeGenerator;
import com.ioteg.generation.GenerationContext;
import com.ioteg.model.EventType;
import com.ioteg.resultmodel.ArrayResultBlock;
import com.ioteg.resultmodel.ResultBlock;
import com.ioteg.resultmodel.ResultComplexField;
import com.ioteg.resultmodel.ResultEvent;
import com.ioteg.resultmodel.ResultSimpleField;
import com.ioteg.serializers.xml.XMLArrayResultBlockSerializer;
import com.ioteg.serializers.xml.XMLResultBlockSerializer;
import com.ioteg.serializers.xml.XMLResultComplexFieldSerializer;
import com.ioteg.serializers.xml.XMLResultEventSerializer;
import com.ioteg.serializers.xml.XMLResultSimpleFieldSerializer;
import com.ioteg.serializers.xml.XMLSerializerMapper;

public class XmlSerializerMapperTestCase {

	private static File tempFile;
	private FileWriter values;
	private static SAXBuilder builder;
	private static ClassLoader classLoader;
	private static XMLSerializerMapper xmlSerializerMapper;
	private static EventTypeBuilder eventTypeBuilder;

	@BeforeAll
	public static void setup() {
		builder = new SAXBuilder();
		classLoader = XmlSerializerMapperTestCase.class.getClassLoader();
		eventTypeBuilder = new EventTypeBuilder();

		xmlSerializerMapper = new XMLSerializerMapper();

		xmlSerializerMapper.registerCustomSerializer(ResultEvent.class, new XMLResultEventSerializer());
		xmlSerializerMapper.registerCustomSerializer(ArrayResultBlock.class, new XMLArrayResultBlockSerializer());
		xmlSerializerMapper.registerCustomSerializer(ResultBlock.class, new XMLResultBlockSerializer());
		xmlSerializerMapper.registerCustomSerializer(ResultSimpleField.class, new XMLResultSimpleFieldSerializer());
		xmlSerializerMapper.registerCustomSerializer(ResultComplexField.class, new XMLResultComplexFieldSerializer());
	}

	@BeforeEach
	public void loadSchema() throws Exception {

		tempFile = File.createTempFile("temp", "file");
		values = new FileWriter(tempFile);
	}

	@Test
	public void testXmlComplexField() throws Exception {

		File xmlFile = new File(classLoader.getResource("./FormatValueTestFiles/testFormatValues.xml").getFile());
		Document doc = builder.build(xmlFile);

		EventType eventType = eventTypeBuilder.build(doc);
		GenerationContext context = new GenerationContext();
		EventTypeGenerator eventTypeGenerator = new EventTypeGenerator(
				new EventTypeGenerationAlgorithm(eventType, context), context);

		String result = xmlSerializerMapper.writeValueAsString(eventTypeGenerator.generate(1).get(0));
		result = result.replace("\n", "");
		result = result.replaceAll(">\\s*<", "><");

		assertThat(result,
				matchesPattern("<xml>" + "<testFormatValues>" + "<feed>" + "<lugar type=\"ComplexType\">"
						+ "<nombre type=\"String\">[ABCDEFGHIJKLMNOPQRSTUVWXYZ]{4}</nombre>"
						+ "<latitud type=\"Float\">\"-?\\d+\\.\\d{5}\"</latitud>"
						+ "<longitud type=\"Float\">-?\\d+\\.\\d{5}</longitud>" + "</lugar>" + "</feed>"
						+ "</testFormatValues>" + "</xml>"));

	}

	@Test
	public void testXmlComplexFieldNotRepeatTag() throws Exception {

		File xmlFile = new File(
				classLoader.getResource("./FormatValueTestFiles/testFormatValuesNotRepeatTag.xml").getFile());
		Document doc = builder.build(xmlFile);

		EventType eventType = eventTypeBuilder.build(doc);

		GenerationContext context = new GenerationContext();
		EventTypeGenerator eventTypeGenerator = new EventTypeGenerator(
				new EventTypeGenerationAlgorithm(eventType, context), context);

		String result = xmlSerializerMapper.writeValueAsString(eventTypeGenerator.generate(1).get(0));

		result = result.replace("\n", "");
		result = result.replaceAll(">\\s*<", "><");

		assertThat(result,
				matchesPattern("<xml>" + "<testFormatValues>" + "<lugar type=\"ComplexType\">"
						+ "<nombre type=\"String\">[ABCDEFGHIJKLMNOPQRSTUVWXYZ]{4}</nombre>"
						+ "<latitud type=\"Float\">\"-?\\d+\\.\\d{5}\"</latitud>"
						+ "<longitud type=\"Float\">-?\\d+\\.\\d{5}</longitud>" + "</lugar>" + "</testFormatValues>"
						+ "</xml>"));

	}

	@Test
	public void testXmlSerializerWithOptionalFields() throws Exception {

		File xmlFile = new File(
				classLoader.getResource("./FormatValueTestFiles/testFormatValuesWithOptionalFields.xml").getFile());
		Document doc = builder.build(xmlFile);

		EventType eventType = eventTypeBuilder.build(doc);

		GenerationContext context = new GenerationContext();
		EventTypeGenerator eventTypeGenerator = new EventTypeGenerator(
				new EventTypeGenerationAlgorithm(eventType, context), context);

		String result = xmlSerializerMapper.writeValueAsString(eventTypeGenerator.generate(1).get(0));
		result = result.replace("\n", "");
		result = result.replaceAll(">\\s*<", "><");
		assertThat(result,
				matchesPattern("<xml>" + "<testFormatValues>" + "<feed>" + "<lugar type=\"ComplexType\">"
						+ "<nombre type=\"String\">[ABCDEFGHIJKLMNOPQRSTUVWXYZ]{4}</nombre>"
						+ "<latitud type=\"Float\">\"-?\\d+\\.\\d{5}\"</latitud>"
						+ "<longitud type=\"Float\">-?\\d+\\.\\d{5}</longitud>" + "</lugar>"
						+ "(<nombreOpcional type=\"String\">[ABCDEFGHIJKLMNOPQRSTUVWXYZ]{4}</nombreOpcional>)?"
						+ "(<cadenaOpcional type=\"String\">\"[ABCDEFGHIJKLMNOPQRSTUVWXYZ]{4}\"</cadenaOpcional>)?"
						+ "</feed>" + "</testFormatValues>" + "</xml>"));

	}

	@AfterEach
	public void teardown() throws Exception {
		tempFile.delete();
		values.close();
	}

}
