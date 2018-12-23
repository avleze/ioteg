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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.ioteg.EventGenerator;
import com.ioteg.Trio;
import com.ioteg.CsvUtil;

public class CsvUtilTestCase {

	private static Document doc;
	private static File tempFile;
	private FileWriter values;

	@BeforeEach
	public void loadSchema() throws JDOMException, IOException {
		SAXBuilder builder = new SAXBuilder();
		ClassLoader classLoader = JsonUtilTestCase.class.getClassLoader();
		File xmlFile = new File(classLoader.getResource("./FormatValueTestFiles/testFormatValues.xml").getFile());
		doc = builder.build(xmlFile);
		tempFile = File.createTempFile("temp", "file");
		values = new FileWriter(tempFile);

		EventGenerator.fieldvalues = new ArrayList<List<Trio<String, String, String>>>();
	}

	@Test
	public void testCsvComplexField() throws IOException, JDOMException {

		CsvUtil.CsvFormatValues(values, doc);

		values.close();

		String csvResult = new String(Files.readAllBytes(Paths.get(tempFile.getPath())));

		String[] lines = csvResult.split("\n");

		String[] headings = lines[0].split(",");
		String[] result = lines[1].split(",");

		assertThat(headings[0], equalTo("lugar.nombre"));
		assertThat(headings[1], equalTo("lugar.latitud"));
		assertThat(headings[2], equalTo("lugar.longitud"));

		assertThat(result[0].length(), equalTo(4));
		assertThat(result[1], matchesPattern("\"-?\\d+\\.\\d{5}\""));
		assertThat(result[2], matchesPattern("-?\\d+\\.\\d{5}"));
	}

	@AfterEach
	public void teardown() throws IOException {
		tempFile.delete();
		values.close();
	}

}
