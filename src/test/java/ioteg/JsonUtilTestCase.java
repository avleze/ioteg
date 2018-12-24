package ioteg;

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

import com.ioteg.EventGenerator;
import com.ioteg.JsonUtil;
import com.ioteg.Trio;

import static org.hamcrest.Matchers.matchesPattern;
import static org.junit.Assert.assertThat;

public class JsonUtilTestCase {

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
		EventGenerator.fieldvalues = new ArrayList<List<Trio<String, String, String>>>();
	}

	@Test
	public void testJsonComplexField() throws IOException, JDOMException {
		File xmlFile = new File(classLoader.getResource("./FormatValueTestFiles/testFormatValues.xml").getFile());
		Document doc = builder.build(xmlFile);

		JsonUtil.JsonFormatValues(values, doc);

		String jsonResult = new String(Files.readAllBytes(Paths.get(tempFile.getPath())));

		String resultRegex = "\\{\"testFormatValues\":\\[" + "\\{\"lugar\":\\{"
				+ "\"nombre\":[ABCDEFGHIJKLMNOPQRSTUVWXYZ]{4}," + "\"latitud\":\"-?\\d+\\.\\d{5}\","
				+ "\"longitud\":-?\\d+\\.\\d{5}\\}\\}\\]\\}";

		assertThat(jsonResult, matchesPattern(resultRegex));

	}

	@Test
	public void testJsonComplexFieldNotRepeatTag() throws IOException, JDOMException {
		File xmlFile = new File(
				classLoader.getResource("./FormatValueTestFiles/testFormatValuesNotRepeatTag.xml").getFile());
		Document doc = builder.build(xmlFile);

		JsonUtil.JsonFormatValues(values, doc);

		String jsonResult = new String(Files.readAllBytes(Paths.get(tempFile.getPath())));

		String resultRegex = "\\{\"testFormatValues\":" + "\\{\"lugar\":\\{"
				+ "\"nombre\":[ABCDEFGHIJKLMNOPQRSTUVWXYZ]{4}," + "\"latitud\":\"-?\\d+\\.\\d{5}\","
				+ "\"longitud\":-?\\d+\\.\\d{5}\\}\\}\\}";

		assertThat(jsonResult, matchesPattern(resultRegex));

	}

	@AfterEach
	public void teardown() throws IOException {
		tempFile.delete();
		values.close();
	}

}
