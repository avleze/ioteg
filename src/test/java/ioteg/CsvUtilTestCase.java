package ioteg;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.matchesPattern;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.ioteg.CsvUtil;
import com.ioteg.EventGenerator;
import com.ioteg.Trio;

public class CsvUtilTestCase {

	private static File tempFile;
	private FileWriter values;
	private static SAXBuilder builder;
	private static ClassLoader classLoader;

	@BeforeAll
	public static void setup() {
		builder = new SAXBuilder();
		classLoader = CsvUtilTestCase.class.getClassLoader();
	}

	@BeforeEach
	public void loadSchema() throws JDOMException, IOException {

		tempFile = File.createTempFile("temp", "file");
		values = new FileWriter(tempFile);
		EventGenerator.fieldvalues = new ArrayList<>();
	}

	@Test
	public void testCsvComplexField() throws IOException, JDOMException {
		File xmlFile = new File(classLoader.getResource("./FormatValueTestFiles/testFormatValues.xml").getFile());
		Document doc = builder.build(xmlFile);

		CsvUtil.CsvFormatValues(values, doc);

		values.close();

		String csvResult = new String(Files.readAllBytes(Paths.get(tempFile.getPath())));

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
	public void testCsvComplexFieldNotRepeatTag() throws IOException, JDOMException {
		File xmlFile = new File(
				classLoader.getResource("./FormatValueTestFiles/testFormatValuesNotRepeatTag.xml").getFile());
		Document doc = builder.build(xmlFile);

		CsvUtil.CsvFormatValues(values, doc);

		values.close();

		String csvResult = new String(Files.readAllBytes(Paths.get(tempFile.getPath())));
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
	public void testCsvWithOptionalFields() throws IOException, JDOMException {
		File xmlFile = new File(
				classLoader.getResource("./FormatValueTestFiles/testFormatValuesWithOptionalFields.xml").getFile());
		Document doc = builder.build(xmlFile);

		CsvUtil.CsvFormatValues(values, doc);

		values.close();

		String csvResult = new String(Files.readAllBytes(Paths.get(tempFile.getPath())));
		String[] lines = csvResult.split("\n");

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
		assertThat(result[3], matchesPattern("[ABCDEFGHIJKLMNOPQRSTUVWXYZ]{4}"));
		assertThat(result[4], matchesPattern("\"[ABCDEFGHIJKLMNOPQRSTUVWXYZ]{4}\""));

	}
	
	@AfterEach
	public void teardown() throws IOException {
		tempFile.delete();
		values.close();
	}

}
