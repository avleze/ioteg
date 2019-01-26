package ioteg;

import static org.junit.Assert.assertThat;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.ioteg.EventGenerator;

import static org.hamcrest.Matchers.matchesPattern;

import static org.hamcrest.Matchers.equalTo;



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
		EventGenerator.fieldvalues = new ArrayList<>();
	}

	@Test
	public void testGenerateComplexValueCsv() throws IOException, JDOMException {		
		Element field = fields.get(36);

		StringBuilder result = EventGenerator.GenerateValueComplexType(field, "csv");
		String[] resultSplitted = result.toString().split(",");
		
		assertThat(resultSplitted[0].length(), equalTo(4));
		assertThat(resultSplitted[1], matchesPattern("-?\\d+\\.\\d{5}"));
		assertThat(resultSplitted[2], matchesPattern("-?\\d+\\.\\d{5}"));
	}
	
	@Test
	public void testGenerateComplexValueCsvWithChooseoneAttributes() throws IOException, JDOMException {		
		Element field = fields.get(37);

		StringBuilder result = EventGenerator.GenerateValueComplexType(field, "csv");

		String[] resultSplitted = result.toString().split(",");
		
		assertThat(resultSplitted[0].length(), equalTo(4));
		assertThat(resultSplitted[1], matchesPattern("-?\\d+\\.\\d{5}"));
		assertThat(resultSplitted[2], matchesPattern("-?\\d+\\.\\d{5}"));
		assertThat(resultSplitted[3], matchesPattern("\"(red|green|blue)\""));
	}
	
	@Test
	public void testGenerateComplexValueCsvWithChooseoneFields() throws IOException, JDOMException {		
		Element field = fields.get(38);

		StringBuilder result = EventGenerator.GenerateValueComplexType(field, "csv");
		String[] resultSplitted = result.toString().split(",");
		
		assertThat(resultSplitted[0].length(), equalTo(4));
		assertThat(resultSplitted[1], matchesPattern("-?\\d+\\.\\d{5}"));
		assertThat(resultSplitted[2], matchesPattern("-?\\d+\\.\\d{5}"));
		assertThat(resultSplitted[3], matchesPattern("-?\\d+\\.\\d{5}"));
	}
	
	@Test
	public void testGenerateComplexValueJson() throws IOException, JDOMException {		
		Element field = fields.get(36);

		StringBuilder result = EventGenerator.GenerateValueComplexType(field, "json");
		String[] resultSplitted = result.toString().split(",");

		assertThat(resultSplitted[0], matchesPattern("\\{\"nombre\":[ABCDEFGHIJKLMNOPQRSTUVWXYZ]{4}"));
		assertThat(resultSplitted[1], matchesPattern("\"latitud\":-?\\d+\\.\\d{5}"));
		assertThat(resultSplitted[2], matchesPattern("\"longitud\":-?\\d+\\.\\d{5}\\}"));

	}
	
	@Test
	public void testGenerateComplexValueJsonWithChooseoneAttributes() throws IOException, JDOMException {		
		Element field = fields.get(37);

		StringBuilder result = EventGenerator.GenerateValueComplexType(field, "json");
		String[] resultSplitted = result.toString().split(",");
		assertThat(resultSplitted[0], matchesPattern("\\{\"nombre\":[ABCDEFGHIJKLMNOPQRSTUVWXYZ]{4}"));
		assertThat(resultSplitted[1], matchesPattern("\"latitud\":-?\\d+\\.\\d{5}"));
		assertThat(resultSplitted[2], matchesPattern("\"longitud\":-?\\d+\\.\\d{5}"));
		assertThat(resultSplitted[3], matchesPattern("\"color\":\"(red|green|blue)\"\\}"));
	}
	
	@Test
	public void testGenerateComplexValueJsonWithChooseoneFields() throws IOException, JDOMException {		
		Element field = fields.get(38);

		StringBuilder result = EventGenerator.GenerateValueComplexType(field, "json");
		String[] resultSplitted = result.toString().split(",");
		assertThat(resultSplitted[0], matchesPattern("\\{\"nombre\":[ABCDEFGHIJKLMNOPQRSTUVWXYZ]{4}"));
		assertThat(resultSplitted[1], matchesPattern("\"latitud\":-?\\d+\\.\\d{5}"));
		assertThat(resultSplitted[2], matchesPattern("\"longitud\":-?\\d+\\.\\d{5}"));
		assertThat(resultSplitted[3], matchesPattern("\"fields\":\\{\"(f1|f2)\":-?\\d+\\.\\d{5}\\}\\}"));
	}
	
	@Test
	public void testGenerateComplexValueXml() throws IOException, JDOMException {		
		Element field = fields.get(36);
		StringBuilder result = EventGenerator.GenerateValueComplexType(field, "xml");
		String[] resultSplitted = result.toString().split("\n");

		assertThat(resultSplitted[1], matchesPattern("<nombre type=\"String\">[ABCDEFGHIJKLMNOPQRSTUVWXYZ]{4}</nombre>"));
		assertThat(resultSplitted[2], matchesPattern("<latitud type=\"Float\">-?\\d+\\.\\d{5}</latitud>"));
		assertThat(resultSplitted[3], matchesPattern("<longitud type=\"Float\">-?\\d+\\.\\d{5}</longitud>"));
	}
	
	@Test
	public void testGenerateComplexValueXmlWithChooseoneAttributes() throws IOException, JDOMException {		
		Element field = fields.get(37);
		StringBuilder result = EventGenerator.GenerateValueComplexType(field, "xml");
		String[] resultSplitted = result.toString().split("\n");

		assertThat(resultSplitted[1], matchesPattern("<nombre type=\"String\">[ABCDEFGHIJKLMNOPQRSTUVWXYZ]{4}</nombre>"));
		assertThat(resultSplitted[2], matchesPattern("<latitud type=\"Float\">-?\\d+\\.\\d{5}</latitud>"));
		assertThat(resultSplitted[3], matchesPattern("<longitud type=\"Float\">-?\\d+\\.\\d{5}</longitud>"));
		assertThat(resultSplitted[4], matchesPattern("<color type=\"String\">\"(red|green|blue)\"</color>"));
	}
	
	@Test
	public void testGenerateComplexValueXmlWithChooseoneFields() throws IOException, JDOMException {		
		Element field = fields.get(38);
		StringBuilder result = EventGenerator.GenerateValueComplexType(field, "xml");
		String[] resultSplitted = result.toString().split("\n");
		assertThat(resultSplitted[1], matchesPattern("<nombre type=\"String\">[ABCDEFGHIJKLMNOPQRSTUVWXYZ]{4}</nombre>"));
		assertThat(resultSplitted[2], matchesPattern("<latitud type=\"Float\">-?\\d+\\.\\d{5}</latitud>"));
		assertThat(resultSplitted[3], matchesPattern("<longitud type=\"Float\">-?\\d+\\.\\d{5}</longitud>"));
		assertThat(resultSplitted[4], matchesPattern("<fields type=\"Float\">((<f1 type=\"Float\">-?\\d+\\.\\d{5}</f1>)|(<f2 type=\"Float\">-?\\d+\\.\\d{5}</f2>))"));
		assertThat(resultSplitted[5], matchesPattern("</fields>"));
	}
	

}
