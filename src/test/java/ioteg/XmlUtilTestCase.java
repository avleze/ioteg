package ioteg;

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
import com.ioteg.XmlUtil;

public class XmlUtilTestCase {

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
	public void testXmlComplexField() throws IOException, JDOMException {

		XmlUtil.XmlFormatValues(values, doc);

		values.close();
		
		String xmlResult = new String(Files.readAllBytes(Paths.get(tempFile.getPath())));
		
		String[] resultSplitted = xmlResult.toString().split("\n");
		
		assertThat(resultSplitted[0], matchesPattern("<xml>"));
		assertThat(resultSplitted[1], matchesPattern("<testFormatValues>"));
		assertThat(resultSplitted[2], matchesPattern("<feeds>"));
		assertThat(resultSplitted[3], matchesPattern("<feed>"));
		assertThat(resultSplitted[4], matchesPattern("<lugar>"));
		assertThat(resultSplitted[5], matchesPattern("<nombre type=\"String\">[ABCDEFGHIJKLMNOPQRSTUVWXYZ]{4}</nombre>"));
		assertThat(resultSplitted[6], matchesPattern("<latitud type=\"Float\">\"-?\\d+\\.\\d{5}\"</latitud>"));
		assertThat(resultSplitted[7], matchesPattern("<longitud type=\"Float\">-?\\d+\\.\\d{5}</longitud>"));
		assertThat(resultSplitted[8], matchesPattern("</lugar>"));
		assertThat(resultSplitted[9], matchesPattern("</feed>"));
		assertThat(resultSplitted[10], matchesPattern("</feeds>"));
		assertThat(resultSplitted[11], matchesPattern("</xml>"));

	}
	
	@AfterEach
	public void teardown() throws IOException {
		tempFile.delete();
		values.close();
	}

}
