package com.ioteg;

import static org.hamcrest.MatcherAssert.assertThat;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.ioteg.builders.FieldBuilder;
import com.ioteg.exprlang.ExprParser.ExprLangParsingException;
import com.ioteg.generators.Generable;
import com.ioteg.generators.GeneratorsFactory;
import com.ioteg.generators.exceptions.NotExistingGeneratorException;
import com.ioteg.model.Field;
import com.ioteg.resultmodel.ArrayResultBlock;
import com.ioteg.resultmodel.ResultBlock;
import com.ioteg.resultmodel.ResultComplexField;
import com.ioteg.resultmodel.ResultEvent;
import com.ioteg.resultmodel.ResultField;
import com.ioteg.resultmodel.ResultSimpleField;
import com.ioteg.resultmodel.jsonserializers.ArrayResultBlockSerializer;
import com.ioteg.resultmodel.jsonserializers.ResultBlockSerializer;
import com.ioteg.resultmodel.jsonserializers.ResultComplexFieldSerializer;
import com.ioteg.resultmodel.jsonserializers.ResultEventSerializer;
import com.ioteg.resultmodel.jsonserializers.ResultSimpleFieldSerializer;
import com.ioteg.resultmodel.xmlserializers.XMLArrayResultBlockSerializer;
import com.ioteg.resultmodel.xmlserializers.XMLResultBlockSerializer;
import com.ioteg.resultmodel.xmlserializers.XMLResultComplexFieldSerializer;
import com.ioteg.resultmodel.xmlserializers.XMLResultEventSerializer;
import com.ioteg.resultmodel.xmlserializers.XMLResultSimpleFieldSerializer;
import com.ioteg.resultmodel.xmlserializers.XMLSerializerMapper;

import static org.hamcrest.Matchers.matchesPattern;

public class ComplexTypeGeneratorTestCase {

	private static File xmlFile;
	private static ClassLoader classLoader;
	private static List<Element> fields;

	@BeforeEach
	public void loadSchema() throws JDOMException, IOException {
		SAXBuilder builder = new SAXBuilder();
		classLoader = ComplexTypeGeneratorTestCase.class.getClassLoader();
		xmlFile = new File(classLoader.getResource("./EPLSamples/testEplQuery.xml").getFile());
		Document document = builder.build(xmlFile);

		List<Element> blocks = document.getRootElement().getChildren("block");
		fields = blocks.get(0).getChildren("field");
	}

	/*@Disabled(value="needs refactoring")
	@Test
	public void testGenerateComplexValueCsv()
			throws IOException, JDOMException, NotExistingGeneratorException, ExprLangParsingException {
		Element field = fields.get(36);

		StringBuilder result = EventGenerator.generateValueComplexType(field, "csv");
		String[] resultSplitted = result.toString().split(",");

		assertThat(resultSplitted[0].length(), equalTo(4));
		assertThat(resultSplitted[1], matchesPattern("-?\\d+\\.\\d{5}"));
		assertThat(resultSplitted[2], matchesPattern("-?\\d+\\.\\d{5}"));
	}

	@Disabled(value="needs refactoring")
	@Test
	public void testGenerateComplexValueCsvWithChooseoneAttributes()
			throws IOException, JDOMException, NotExistingGeneratorException, ExprLangParsingException {
		Element field = fields.get(37);

		StringBuilder result = GeneratorsFactory.makeGenerator(field, totalNumEvent)(field, "csv");

		String[] resultSplitted = result.toString().split(",");

		assertThat(resultSplitted[0].length(), equalTo(4));
		assertThat(resultSplitted[1], matchesPattern("-?\\d+\\.\\d{5}"));
		assertThat(resultSplitted[2], matchesPattern("-?\\d+\\.\\d{5}"));
		assertThat(resultSplitted[3], matchesPattern("\"(red|green|blue)\""));
	}
	
	@Disabled(value="needs refactoring")
	@Test
	public void testGenerateComplexValueCsvWithChooseoneFields()
			throws IOException, JDOMException, NotExistingGeneratorException, ExprLangParsingException {
		Element field = fields.get(38);

		StringBuilder result = EventGenerator.generateValueComplexType(field, "csv");
		String[] resultSplitted = result.toString().split(",");

		assertThat(resultSplitted[0].length(), equalTo(4));
		assertThat(resultSplitted[1], matchesPattern("-?\\d+\\.\\d{5}"));
		assertThat(resultSplitted[2], matchesPattern("-?\\d+\\.\\d{5}"));
		assertThat(resultSplitted[3], matchesPattern("-?\\d+\\.\\d{5}"));
	}*/

	@Test
	public void testGenerateComplexValueJson()
			throws JDOMException, NotExistingGeneratorException, ExprLangParsingException, IOException, ParseException {
		Element field = fields.get(36);
		FieldBuilder fB = new FieldBuilder();
		Field modelField = fB.build(field);
		Generable generator = GeneratorsFactory.makeGenerator(modelField, 1);

		SimpleModule module = new SimpleModule();
		ObjectMapper jsonSerializer = new ObjectMapper();

		module.addSerializer(ArrayResultBlock.class, new ArrayResultBlockSerializer(null));
		module.addSerializer(ResultBlock.class, new ResultBlockSerializer(null));
		module.addSerializer(ResultEvent.class, new ResultEventSerializer(null));
		module.addSerializer(ResultSimpleField.class, new ResultSimpleFieldSerializer(null));
		module.addSerializer(ResultComplexField.class, new ResultComplexFieldSerializer(null));

		jsonSerializer.registerModule(module);

		ResultField result = generator.generate(1).get(0);
		String[] resultSplitted = jsonSerializer.writeValueAsString(result).split(",");

		assertThat(resultSplitted[0], matchesPattern("\\{\"nombre\":[ABCDEFGHIJKLMNOPQRSTUVWXYZ]{4}"));
		assertThat(resultSplitted[1], matchesPattern("\"latitud\":-?\\d+\\.\\d{5}"));
		assertThat(resultSplitted[2], matchesPattern("\"longitud\":-?\\d+\\.\\d{5}\\}"));

	}

	@Test
	public void testGenerateComplexValueJsonWithChooseoneAttributes()
			throws IOException, JDOMException, NotExistingGeneratorException, ExprLangParsingException, ParseException {
		Element field = fields.get(37);
		FieldBuilder fB = new FieldBuilder();
		Field modelField = fB.build(field);
		Generable generator = GeneratorsFactory.makeGenerator(modelField, 1);

		SimpleModule module = new SimpleModule();
		ObjectMapper jsonSerializer = new ObjectMapper();

		module.addSerializer(ArrayResultBlock.class, new ArrayResultBlockSerializer(null));
		module.addSerializer(ResultBlock.class, new ResultBlockSerializer(null));
		module.addSerializer(ResultEvent.class, new ResultEventSerializer(null));
		module.addSerializer(ResultSimpleField.class, new ResultSimpleFieldSerializer(null));
		module.addSerializer(ResultComplexField.class, new ResultComplexFieldSerializer(null));

		jsonSerializer.registerModule(module);

		ResultField result = generator.generate(1).get(0);
		String[] resultSplitted = jsonSerializer.writeValueAsString(result).split(",");
		assertThat(resultSplitted[0], matchesPattern("\\{\"nombre\":[ABCDEFGHIJKLMNOPQRSTUVWXYZ]{4}"));
		assertThat(resultSplitted[1], matchesPattern("\"latitud\":-?\\d+\\.\\d{5}"));
		assertThat(resultSplitted[2], matchesPattern("\"longitud\":-?\\d+\\.\\d{5}"));
		assertThat(resultSplitted[3], matchesPattern("\"color\":\"(red|green|blue)\"\\}"));
	}

	@Test
	public void testGenerateComplexValueJsonWithChooseoneFields()
			throws IOException, JDOMException, NotExistingGeneratorException, ExprLangParsingException, ParseException {
		Element field = fields.get(38);
		FieldBuilder fB = new FieldBuilder();
		Field modelField = fB.build(field);
		Generable generator = GeneratorsFactory.makeGenerator(modelField, 1);

		SimpleModule module = new SimpleModule();
		ObjectMapper jsonSerializer = new ObjectMapper();

		module.addSerializer(ArrayResultBlock.class, new ArrayResultBlockSerializer(null));
		module.addSerializer(ResultBlock.class, new ResultBlockSerializer(null));
		module.addSerializer(ResultEvent.class, new ResultEventSerializer(null));
		module.addSerializer(ResultSimpleField.class, new ResultSimpleFieldSerializer(null));
		module.addSerializer(ResultComplexField.class, new ResultComplexFieldSerializer(null));

		jsonSerializer.registerModule(module);

		ResultField result = generator.generate(1).get(0);
		String[] resultSplitted = jsonSerializer.writeValueAsString(result).split(",");
		assertThat(resultSplitted[0], matchesPattern("\\{\"nombre\":[ABCDEFGHIJKLMNOPQRSTUVWXYZ]{4}"));
		assertThat(resultSplitted[1], matchesPattern("\"latitud\":-?\\d+\\.\\d{5}"));
		assertThat(resultSplitted[2], matchesPattern("\"longitud\":-?\\d+\\.\\d{5}"));
		assertThat(resultSplitted[3], matchesPattern("\"fields\":\\{\"(f1|f2)\":-?\\d+\\.\\d{5}\\}\\}"));
	}

	@Test
	public void testGenerateComplexValueXml()
			throws IOException, JDOMException, NotExistingGeneratorException, ExprLangParsingException, ParseException {
		Element field = fields.get(36);
		FieldBuilder fB = new FieldBuilder();
		Field modelField = fB.build(field);
		Generable generator = GeneratorsFactory.makeGenerator(modelField, 1);

		XMLSerializerMapper xmlSerializerMapper = new XMLSerializerMapper();

		xmlSerializerMapper.registerCustomSerializer(ResultEvent.class, new XMLResultEventSerializer());
		xmlSerializerMapper.registerCustomSerializer(ArrayResultBlock.class, new XMLArrayResultBlockSerializer());
		xmlSerializerMapper.registerCustomSerializer(ResultBlock.class, new XMLResultBlockSerializer());
		xmlSerializerMapper.registerCustomSerializer(ResultSimpleField.class, new XMLResultSimpleFieldSerializer());
		xmlSerializerMapper.registerCustomSerializer(ResultComplexField.class, new XMLResultComplexFieldSerializer());

		ResultComplexField rCF = (ResultComplexField) generator.generate(1).get(0);

		String result = xmlSerializerMapper.writeValueAsString(rCF);

		String[] resultSplitted = result.toString().split("\n");

		assertThat(resultSplitted[0], matchesPattern(" *<lugar type=\"ComplexType\"> *"));
		assertThat(resultSplitted[1],
				matchesPattern(" *<nombre type=\"String\">[ABCDEFGHIJKLMNOPQRSTUVWXYZ]{4}</nombre> *"));
		assertThat(resultSplitted[2], matchesPattern(" *<latitud type=\"Float\">-?\\d+\\.\\d{5}</latitud> *"));
		assertThat(resultSplitted[3], matchesPattern(" *<longitud type=\"Float\">-?\\d+\\.\\d{5}</longitud> *"));
		assertThat(resultSplitted[4], matchesPattern(" *</lugar> *"));
	}

	@Test
	public void testGenerateComplexValueXmlWithChooseoneAttributes()
			throws IOException, JDOMException, NotExistingGeneratorException, ExprLangParsingException, ParseException {
		Element field = fields.get(37);
		FieldBuilder fB = new FieldBuilder();
		Field modelField = fB.build(field);
		Generable generator = GeneratorsFactory.makeGenerator(modelField, 1);

		XMLSerializerMapper xmlSerializerMapper = new XMLSerializerMapper();

		xmlSerializerMapper.registerCustomSerializer(ResultEvent.class, new XMLResultEventSerializer());
		xmlSerializerMapper.registerCustomSerializer(ArrayResultBlock.class, new XMLArrayResultBlockSerializer());
		xmlSerializerMapper.registerCustomSerializer(ResultBlock.class, new XMLResultBlockSerializer());
		xmlSerializerMapper.registerCustomSerializer(ResultSimpleField.class, new XMLResultSimpleFieldSerializer());
		xmlSerializerMapper.registerCustomSerializer(ResultComplexField.class, new XMLResultComplexFieldSerializer());

		ResultComplexField rCF = (ResultComplexField) generator.generate(1).get(0);

		String result = xmlSerializerMapper.writeValueAsString(rCF);

		String[] resultSplitted = result.toString().split("\n");

		assertThat(resultSplitted[0], matchesPattern(" *<lugarchooseone type=\"ComplexType\"> *"));
		assertThat(resultSplitted[1],
				matchesPattern(" *<nombre type=\"String\">[ABCDEFGHIJKLMNOPQRSTUVWXYZ]{4}</nombre> *"));
		assertThat(resultSplitted[2], matchesPattern(" *<latitud type=\"Float\">-?\\d+\\.\\d{5}</latitud> *"));
		assertThat(resultSplitted[3], matchesPattern(" *<longitud type=\"Float\">-?\\d+\\.\\d{5}</longitud> *"));
		assertThat(resultSplitted[4], matchesPattern(" *<color type=\"String\">\"(red|green|blue)\"</color> *"));
		assertThat(resultSplitted[5], matchesPattern(" *</lugarchooseone> *"));
	}

	@Test
	public void testGenerateComplexValueXmlWithChooseoneFields()
			throws IOException, JDOMException, NotExistingGeneratorException, ExprLangParsingException, ParseException {
		Element field = fields.get(38);
		FieldBuilder fB = new FieldBuilder();
		Field modelField = fB.build(field);
		Generable generator = GeneratorsFactory.makeGenerator(modelField, 1);

		XMLSerializerMapper xmlSerializerMapper = new XMLSerializerMapper();

		xmlSerializerMapper.registerCustomSerializer(ResultEvent.class, new XMLResultEventSerializer());
		xmlSerializerMapper.registerCustomSerializer(ArrayResultBlock.class, new XMLArrayResultBlockSerializer());
		xmlSerializerMapper.registerCustomSerializer(ResultBlock.class, new XMLResultBlockSerializer());
		xmlSerializerMapper.registerCustomSerializer(ResultSimpleField.class, new XMLResultSimpleFieldSerializer());
		xmlSerializerMapper.registerCustomSerializer(ResultComplexField.class, new XMLResultComplexFieldSerializer());

		ResultComplexField rCF = (ResultComplexField) generator.generate(1).get(0);

		String result = xmlSerializerMapper.writeValueAsString(rCF);

		String[] resultSplitted = result.toString().split("\n");
		assertThat(resultSplitted[0], matchesPattern(" *<lugarchooseone type=\"ComplexType\"> *"));
		assertThat(resultSplitted[1],
				matchesPattern(" *<nombre type=\"String\">[ABCDEFGHIJKLMNOPQRSTUVWXYZ]{4}</nombre> *"));
		assertThat(resultSplitted[2], matchesPattern(" *<latitud type=\"Float\">-?\\d+\\.\\d{5}</latitud> *"));
		assertThat(resultSplitted[3], matchesPattern(" *<longitud type=\"Float\">-?\\d+\\.\\d{5}</longitud> *"));
		assertThat(resultSplitted[4], matchesPattern(" *<fields type=\"Float\"> *"));
		assertThat(resultSplitted[5], matchesPattern(
				" *((<f1 type=\"Float\">-?\\d+\\.\\d{5}</f1>)|(<f2 type=\"Float\">-?\\d+\\.\\d{5}</f2>)) *"));
		assertThat(resultSplitted[6], matchesPattern(" *</fields>"));
		assertThat(resultSplitted[7], matchesPattern(" *</lugarchooseone> *"));
	}

}
