package ioteg;

import static org.junit.Assert.assertThat;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.xml.sax.InputSource;

import com.ioteg.EventGenerator;
import com.ioteg.Trio;

import static org.hamcrest.Matchers.matchesPattern;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;



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
		EventGenerator.fieldvalues = new ArrayList<List<Trio<String, String, String>>>();
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
	public void testGenerateComplexValueJson() throws IOException, JDOMException {		
		Element field = fields.get(36);

		StringBuilder result = EventGenerator.GenerateValueComplexType(field, "json");
		String[] resultSplitted = result.toString().split(",");

		assertThat(resultSplitted[0], matchesPattern("\\{\"nombre\":[ABCDEFGHIJKLMNOPQRSTUVWXYZ]{4}"));
		assertThat(resultSplitted[1], matchesPattern("\"latitud\":-?\\d+\\.\\d{5}"));
		assertThat(resultSplitted[2], matchesPattern("\"longitud\":-?\\d+\\.\\d{5}\\}"));

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

}
