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
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.ioteg.EventGenerator;
import com.ioteg.Trio;
import com.ioteg.XmlUtil;

public class XmlUtilTestCase {

	private static File tempFile;
	private FileWriter values;
	private static SAXBuilder builder;
	private static ClassLoader classLoader;
	
	@BeforeAll
	public static void setup() {
		builder = new SAXBuilder();
		classLoader = XmlUtilTestCase.class.getClassLoader();
	}
	
	@BeforeEach
	public void loadSchema() throws JDOMException, IOException {

		tempFile = File.createTempFile("temp", "file");
		values = new FileWriter(tempFile);
		EventGenerator.fieldvalues = new ArrayList<>();
	}
	
	@Test
	public void testXmlComplexField() throws IOException, JDOMException {
		
		File xmlFile = new File(classLoader.getResource("./FormatValueTestFiles/testFormatValues.xml").getFile());
		Document doc = builder.build(xmlFile);
		
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
	
	@Test
	public void testXmlComplexFieldNotRepeatTag() throws IOException, JDOMException {
		
		File xmlFile = new File(classLoader.getResource("./FormatValueTestFiles/testFormatValuesNotRepeatTag.xml").getFile());
		Document doc = builder.build(xmlFile);
		
		XmlUtil.XmlFormatValues(values, doc);

		values.close();
		
		String xmlResult = new String(Files.readAllBytes(Paths.get(tempFile.getPath())));
		String[] resultSplitted = xmlResult.toString().split("\n");
		
		assertThat(resultSplitted[0], matchesPattern("<xml>"));
		assertThat(resultSplitted[1], matchesPattern("<testFormatValues>"));
		assertThat(resultSplitted[2], matchesPattern("<lugar>"));
		assertThat(resultSplitted[3], matchesPattern("<nombre type=\"String\">[ABCDEFGHIJKLMNOPQRSTUVWXYZ]{4}</nombre>"));
		assertThat(resultSplitted[4], matchesPattern("<latitud type=\"Float\">\"-?\\d+\\.\\d{5}\"</latitud>"));
		assertThat(resultSplitted[5], matchesPattern("<longitud type=\"Float\">-?\\d+\\.\\d{5}</longitud>"));
		assertThat(resultSplitted[6], matchesPattern("</lugar>"));
		assertThat(resultSplitted[7], matchesPattern("</testFormatValues>"));
		assertThat(resultSplitted[8], matchesPattern("</xml>"));

	}
	
	@Test
	public void testXmlWithOptionalFields() throws IOException, JDOMException {
		
		File xmlFile = new File(classLoader.getResource("./FormatValueTestFiles/testFormatValuesWithOptionalFields.xml").getFile());
		Document doc = builder.build(xmlFile);
		
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
		assertThat(resultSplitted[9], matchesPattern("<nombreOpcional type=\"String\">[ABCDEFGHIJKLMNOPQRSTUVWXYZ]{4}</nombreOpcional>"));
		assertThat(resultSplitted[10], matchesPattern("<cadenaOpcional type=\"String\">\"[ABCDEFGHIJKLMNOPQRSTUVWXYZ]{4}\"</cadenaOpcional>"));
		assertThat(resultSplitted[11], matchesPattern("</feed>"));
		assertThat(resultSplitted[12], matchesPattern("</feeds>"));
		assertThat(resultSplitted[13], matchesPattern("</xml>"));

	}
	
	@AfterEach
	public void teardown() throws IOException {
		tempFile.delete();
		values.close();
	}

}
