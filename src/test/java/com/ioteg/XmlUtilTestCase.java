package com.ioteg;

import static org.hamcrest.Matchers.matchesPattern;
import static org.hamcrest.MatcherAssert.assertThat;

import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.jdom2.Document;
import org.jdom2.input.SAXBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
	public void loadSchema() throws Exception {

		tempFile = File.createTempFile("temp", "file");
		values = new FileWriter(tempFile);
	}
	
	@Test
	public void testXmlComplexField() throws Exception {
		
		File xmlFile = new File(classLoader.getResource("./FormatValueTestFiles/testFormatValues.xml").getFile());
		Document doc = builder.build(xmlFile);
		
		XmlUtil.xmlFormatValues(values, doc);

		values.close();
		
		String result = new String(Files.readAllBytes(Paths.get(tempFile.getPath())));
		result = result.replace("\n", "");
		result = result.replaceAll(">\\s*<", "><");
		
		assertThat(result, matchesPattern("<xml>"
				+ "<testFormatValues>"
				+ "<feed>"
				+ "<lugar type=\"ComplexType\">"
				+ "<nombre type=\"String\">[ABCDEFGHIJKLMNOPQRSTUVWXYZ]{4}</nombre>"
				+ "<latitud type=\"Float\">\"-?\\d+\\.\\d{5}\"</latitud>"
				+ "<longitud type=\"Float\">-?\\d+\\.\\d{5}</longitud>"
				+ "</lugar>"
				+ "</feed>"
				+ "</testFormatValues>"
				+ "</xml>"));

	}
	
	@Test
	public void testXmlComplexFieldNotRepeatTag() throws Exception {
		
		File xmlFile = new File(classLoader.getResource("./FormatValueTestFiles/testFormatValuesNotRepeatTag.xml").getFile());
		Document doc = builder.build(xmlFile);
		
		XmlUtil.xmlFormatValues(values, doc);

		values.close();
		
		String result = new String(Files.readAllBytes(Paths.get(tempFile.getPath())));
		result = result.replace("\n", "");
		result = result.replaceAll(">\\s*<", "><");
		
		assertThat(result, matchesPattern("<xml>"
				+ "<testFormatValues>"
				+ "<lugar type=\"ComplexType\">"
				+ "<nombre type=\"String\">[ABCDEFGHIJKLMNOPQRSTUVWXYZ]{4}</nombre>"
				+ "<latitud type=\"Float\">\"-?\\d+\\.\\d{5}\"</latitud>"
				+ "<longitud type=\"Float\">-?\\d+\\.\\d{5}</longitud>"
				+ "</lugar>"
				+ "</testFormatValues>"
				+ "</xml>"));

	}
	
	
	
	@Test
	public void testXmlSerializerWithOptionalFields() throws Exception {
		
		File xmlFile = new File(classLoader.getResource("./FormatValueTestFiles/testFormatValuesWithOptionalFields.xml").getFile());
		Document doc = builder.build(xmlFile);
		
		XmlUtil.xmlFormatValues(values, doc);

		String result = new String(Files.readAllBytes(Paths.get(tempFile.getPath())));

		result = result.replace("\n", "");
		result = result.replaceAll(">\\s*<", "><");
		assertThat(result, matchesPattern("<xml>"
				+ "<testFormatValues>"
				+ "<feed>"
				+ "<lugar type=\"ComplexType\">"
				+ "<nombre type=\"String\">[ABCDEFGHIJKLMNOPQRSTUVWXYZ]{4}</nombre>"
				+ "<latitud type=\"Float\">\"-?\\d+\\.\\d{5}\"</latitud>"
				+ "<longitud type=\"Float\">-?\\d+\\.\\d{5}</longitud>"
				+ "</lugar>"
				+ "(<nombreOpcional type=\"String\">[ABCDEFGHIJKLMNOPQRSTUVWXYZ]{4}</nombreOpcional>)?"
				+ "(<cadenaOpcional type=\"String\">\"[ABCDEFGHIJKLMNOPQRSTUVWXYZ]{4}\"</cadenaOpcional>)?"
				+ "</feed>"
				+ "</testFormatValues>"
				+ "</xml>"));

	}
	
	@AfterEach
	public void teardown() throws Exception {
		tempFile.delete();
		values.close();
	}

}
