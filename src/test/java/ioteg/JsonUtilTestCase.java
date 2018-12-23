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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.ioteg.EventGenerator;
import com.ioteg.JsonUtil;
import com.ioteg.Trio;

import static org.hamcrest.Matchers.matchesPattern;
import static org.junit.Assert.assertThat;


public class JsonUtilTestCase {

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
	public void testJsonComplexField() throws IOException, JDOMException {

		JsonUtil.JsonFormatValues(values, doc);

		String jsonResult = new String(Files.readAllBytes(Paths.get(tempFile.getPath())));
		
		String resultRegex = "\\{\"testFormatValues\":\\[" +
				"\\{\"lugar\":\\{" +
				"\"nombre\":[ABCDEFGHIJKLMNOPQRSTUVWXYZ]{4}," +
				"\"latitud\":\"-?\\d+\\.\\d{5}\"," +
				"\"longitud\":-?\\d+\\.\\d{5}\\}\\}\\]\\}";

		assertThat(jsonResult, matchesPattern(resultRegex));
		
	}
	
	@AfterEach
	public void teardown() throws IOException {
		tempFile.delete();
		values.close();
	}

}
