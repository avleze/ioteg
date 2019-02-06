package com.ioteg;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.lessThanOrEqualTo;
import static org.hamcrest.Matchers.equalTo;

import static org.hamcrest.Matchers.allOf;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.ioteg.EventGenerator;

public class FloatCustomBehaviourTestCase {
	private static List<Element> fields;

	@BeforeAll
	public static void loadSchema() throws JDOMException, IOException {
		SAXBuilder builder = new SAXBuilder();
		ClassLoader classLoader = FloatCustomBehaviourTestCase.class.getClassLoader();
		File xmlFile = new File(classLoader.getResource("./generators/testFloatCustomBehaviour.xml").getFile());
		Document document = builder.build(xmlFile);

		List<Element> blocks = document.getRootElement().getChildren("block");
		fields = blocks.get(0).getChildren("field");
		EventGenerator.fieldvalues = new ArrayList<>();
	}

	@Test
	public void testSequenceIncreasing() throws JDOMException, IOException {

		Element field = fields.get(0);
		EventGenerator.totalnumevents = 100;
		String strResult = null;
		Double result = null;
		for (int events = 0; events < 10; ++events) {

			List<Double> resultsOfSimulation = new LinkedList<>();

			for (int i = 0; i < 10; ++i) {
				strResult = EventGenerator.generateValueSimpleType(field.getAttributeValue("type"), field);
				result = Double.parseDouble(strResult);
				assertThat(result, allOf(greaterThanOrEqualTo(156.96), lessThanOrEqualTo(300.0)));
				resultsOfSimulation.add(result);
			}

			assertTrue(resultsOfSimulation.stream().sorted().collect(Collectors.toList()).equals(resultsOfSimulation));
		}
	}

	@Test
	public void testSequenceDecreasing() throws JDOMException, IOException {

		Element field = fields.get(1);
		EventGenerator.totalnumevents = 100;
		String strResult = null;
		Double result = null;

		for (int events = 0; events < 10; ++events) {
			List<Double> resultsOfSimulation = new LinkedList<>();

			for (int simulation = 0; simulation < 10; ++simulation) {
				strResult = EventGenerator.generateValueSimpleType(field.getAttributeValue("type"), field);
				result = Double.parseDouble(strResult);
				resultsOfSimulation.add(result);
				assertThat(result, allOf(greaterThanOrEqualTo(156.96), lessThanOrEqualTo(300.0)));
			}
			assertTrue(resultsOfSimulation.stream().sorted(Collections.reverseOrder()).collect(Collectors.toList())
					.equals(resultsOfSimulation));
		}
	}

	@Test
	public void testRuleWithFixedVariable() throws JDOMException, IOException {

		Element field = fields.get(2);
		EventGenerator.totalnumevents = 100;
		String strResult = null;
		Double result = null;
		for (int events = 0; events < 100; ++events) {
			strResult = EventGenerator.generateValueSimpleType(field.getAttributeValue("type"), field);
			Double previous = Double.parseDouble(strResult);
			for (int simulation = 1; simulation < 10; ++simulation) {
				strResult = EventGenerator.generateValueSimpleType(field.getAttributeValue("type"), field);
				result = Double.parseDouble(strResult);
				assertThat(result, equalTo(previous));
				previous = result;
			}

		}
	}

	@Test
	public void testObtainVariableValueWithMinVariableDependence() throws JDOMException, IOException {

		Element field = fields.get(3);
		EventGenerator.totalnumevents = 100;
		String strResult = null;
		Double result = null;

		for (int i = 0; i < 1000; ++i) {
			strResult = EventGenerator.generateValueSimpleType(field.getAttributeValue("type"), field);
			result = Double.parseDouble(strResult);
			assertThat(result, allOf(greaterThanOrEqualTo(0.0), lessThanOrEqualTo(100.0)));
		}

	}

	@Test
	public void testObtainVariableValueWithMaxVariableDependence() throws JDOMException, IOException {

		Element field = fields.get(4);
		EventGenerator.totalnumevents = 100;
		String strResult = null;
		Double result = null;

		for (int i = 0; i < 1000; ++i) {
			strResult = EventGenerator.generateValueSimpleType(field.getAttributeValue("type"), field);
			result = Double.parseDouble(strResult);
			assertThat(result, allOf(greaterThanOrEqualTo(0.0), lessThanOrEqualTo(100.0)));
		}

	}

	@Test
	public void testObtainVariableValueWithSum() throws JDOMException, IOException {

		Element field = fields.get(5);
		EventGenerator.totalnumevents = 100;

		String strResult = null;
		Double result = null;

		for (int i = 0; i < 100; ++i) {
			strResult = EventGenerator.generateValueSimpleType(field.getAttributeValue("type"), field);
			result = Double.parseDouble(strResult);
			assertThat(result, equalTo(101.0));
		}

	}

	@Test
	public void testObtainVariableValueWithSubstraction() throws JDOMException, IOException {

		Element field = fields.get(6);
		EventGenerator.totalnumevents = 100;

		String strResult = null;
		Double result = null;

		for (int i = 0; i < 50; ++i) {
			strResult = EventGenerator.generateValueSimpleType(field.getAttributeValue("type"), field);
			result = Double.parseDouble(strResult);
			assertThat(result, equalTo(99.0));
		}

		for (int i = 0; i < 50; ++i) {
			strResult = EventGenerator.generateValueSimpleType(field.getAttributeValue("type"), field);
			result = Double.parseDouble(strResult);
			assertThat(result, equalTo(-99.0));
		}

	}

	@Test
	public void testObtainVariableValueWithDivision() throws JDOMException, IOException {

		Element field = fields.get(7);
		EventGenerator.totalnumevents = 100;
		String strResult = null;
		Double result = null;

		for (int i = 0; i < 50; ++i) {
			strResult = EventGenerator.generateValueSimpleType(field.getAttributeValue("type"), field);
			result = Double.parseDouble(strResult);
			assertThat(result, equalTo(50.0));
		}

		for (int i = 0; i < 50; ++i) {
			strResult = EventGenerator.generateValueSimpleType(field.getAttributeValue("type"), field);
			result = Double.parseDouble(strResult);
			assertThat(result, equalTo(.02));
		}

	}

	@Test
	public void testObtainVariableValueWithProduct() throws JDOMException, IOException {

		Element field = fields.get(8);
		EventGenerator.totalnumevents = 100;
		String strResult = null;
		Double result = null;

		for (int i = 0; i < 100; ++i) {
			strResult = EventGenerator.generateValueSimpleType(field.getAttributeValue("type"), field);
			result = Double.parseDouble(strResult);
			assertThat(result, equalTo(200.0));
		}

	}

	@Test
	public void testRuleWithFixedValue() throws JDOMException, IOException {

		Element field = fields.get(9);
		EventGenerator.totalnumevents = 100;

		String strResult = null;
		Double result = null;

		for (int i = 0; i < 100; ++i) {
			strResult = EventGenerator.generateValueSimpleType(field.getAttributeValue("type"), field);
			result = Double.parseDouble(strResult);
			assertThat(result, equalTo(50.0));
		}

	}

	@Test
	public void testRuleWithOperationValue() throws JDOMException, IOException {

		Element field = fields.get(10);
		EventGenerator.totalnumevents = 100;

		String strResult = null;
		Double result = null;

		for (int i = 0; i < 100; ++i) {
			strResult = EventGenerator.generateValueSimpleType(field.getAttributeValue("type"), field);
			result = Double.parseDouble(strResult);
			assertThat(result, equalTo(101.0));
		}

	}

}
