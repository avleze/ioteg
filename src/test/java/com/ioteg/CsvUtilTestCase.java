package com.ioteg;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.matchesPattern;
import static org.hamcrest.MatcherAssert.assertThat;

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

import com.ioteg.builders.EventTypeBuilder;
import com.ioteg.exprlang.ExprParser.ExprLangParsingException;
import com.ioteg.generators.context.GenerationContext;
import com.ioteg.generators.eventtype.EventTypeGenerationAlgorithm;
import com.ioteg.generators.eventtype.EventTypeGenerator;
import com.ioteg.generators.exceptions.NotExistingGeneratorException;
import com.ioteg.model.EventType;
import com.ioteg.resultmodel.csvserializers.CSVUtil;

public class CsvUtilTestCase {

	private static File tempFile;
	private FileWriter values;
	private static SAXBuilder builder;
	private static ClassLoader classLoader;
	private static EventTypeBuilder eventTypeBuilder;

	@BeforeAll
	public static void setup() {
		builder = new SAXBuilder();
		classLoader = CsvUtilTestCase.class.getClassLoader();
		eventTypeBuilder = new EventTypeBuilder();
	}

	@BeforeEach
	public void loadSchema() throws JDOMException, IOException {

		tempFile = File.createTempFile("temp", "file");
		values = new FileWriter(tempFile);
	}

	@Test
	public void testCsvComplexField()
			throws IOException, JDOMException, NotExistingGeneratorException, ExprLangParsingException, ParseException {
		File xmlFile = new File(classLoader.getResource("./FormatValueTestFiles/testFormatValues.xml").getFile());
		Document doc = builder.build(xmlFile);
		EventType eventType = eventTypeBuilder.build(doc);

		GenerationContext context = new GenerationContext();
		EventTypeGenerator eventTypeGenerator = new EventTypeGenerator(new EventTypeGenerationAlgorithm(eventType, context), context);
	
		String csvResult = CSVUtil.serializeResultEvent(eventType, eventTypeGenerator.generate(1).get(0));

		String[] lines = csvResult.split("\n");

		String[] headings = lines[0].split(",");
		String[] result = lines[1].split(",");

		assertThat(headings[0], equalTo("lugar.nombre"));
		assertThat(headings[1], equalTo("lugar.latitud"));
		assertThat(headings[2], equalTo("lugar.longitud"));

		assertThat(result[0], matchesPattern("[ABCDEFGHIJKLMNOPQRSTUVWXYZ]{4}"));
		assertThat(result[1], matchesPattern("\"-?\\d+\\.\\d{5}\""));
		assertThat(result[2], matchesPattern("-?\\d+\\.\\d{5}"));
	}

	@Test
	public void testCsvWithOptionalFields()
			throws IOException, JDOMException, NotExistingGeneratorException, ExprLangParsingException, ParseException {
		File xmlFile = new File(
				classLoader.getResource("./FormatValueTestFiles/testFormatValuesWithOptionalFields.xml").getFile());
		Document doc = builder.build(xmlFile);
		EventType eventType = eventTypeBuilder.build(doc);

		GenerationContext context = new GenerationContext();
		EventTypeGenerator eventTypeGenerator = new EventTypeGenerator(new EventTypeGenerationAlgorithm(eventType, context), context);
	
		String csvResult = CSVUtil.serializeResultEvent(eventType, eventTypeGenerator.generate(1).get(0));		String[] lines = csvResult.split("\n");

		String[] headings = lines[0].split(",");
		String[] result = lines[1].split(",");

		assertThat(headings[0], equalTo("lugar.nombre"));
		assertThat(headings[1], equalTo("lugar.latitud"));
		assertThat(headings[2], equalTo("lugar.longitud"));
		assertThat(headings[3], equalTo("nombreOpcional"));
		assertThat(headings[4], equalTo("cadenaOpcional"));

		assertThat(result[0], matchesPattern("[ABCDEFGHIJKLMNOPQRSTUVWXYZ]{4}"));
		assertThat(result[1], matchesPattern("\"-?\\d+\\.\\d{5}\""));
		assertThat(result[2], matchesPattern("-?\\d+\\.\\d{5}"));

		if (result.length >= 4)
			assertThat(result[3], matchesPattern("[ABCDEFGHIJKLMNOPQRSTUVWXYZ]{4}|"));
		if (result.length == 5)
			assertThat(result[4], matchesPattern("\"[ABCDEFGHIJKLMNOPQRSTUVWXYZ]{4}\""));
	}

	@AfterEach
	public void teardown() throws IOException {
		tempFile.delete();
		values.close();
	}

}
