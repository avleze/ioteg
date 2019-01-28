package ioteg;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;

import org.jdom2.JDOMException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.ioteg.ValidationUtil;

public class ValidationUtilTestCase {
	private static File xmlFile;
	private static ClassLoader classLoader;

	@BeforeAll
	public static void loadSchema() throws JDOMException, IOException {
		classLoader = ValidationUtilTestCase.class.getClassLoader();
	}

	@Test
	public void testEventWithoutBlockTag() throws JDOMException, IOException, ParseException {
		xmlFile = new File(classLoader.getResource("./Validation/testValidateEventWithoutBlockTag.xml").getFile());
		assertFalse(ValidationUtil.validStandart(xmlFile));
	}

	@Test
	public void testEventWithBlockWithoutName() throws JDOMException, IOException, ParseException {
		xmlFile = new File(classLoader.getResource("./Validation/testValidateEventWithBlockWithoutName.xml").getFile());
		assertFalse(ValidationUtil.validStandart(xmlFile));
	}

	@Test
	public void testValidateStringField() throws JDOMException, IOException, ParseException {
		xmlFile = new File(classLoader.getResource("./Validation/testValidateStringField.xml").getFile());
		assertFalse(ValidationUtil.validStandart(xmlFile));
	}

	@Test
	public void testValidateStringFieldInvalidCase() throws JDOMException, IOException, ParseException {
		xmlFile = new File(classLoader.getResource("./Validation/testValidateStringFieldInvalidCase.xml").getFile());
		assertFalse(ValidationUtil.validStandart(xmlFile));
	}

	@Test
	public void testValidateFloatField() throws JDOMException, IOException, ParseException {
		xmlFile = new File(classLoader.getResource("./Validation/testValidateFloatField.xml").getFile());
		assertFalse(ValidationUtil.validStandart(xmlFile));
	}

	@Test
	public void testValidateFloatFieldWithMinOnly() throws JDOMException, IOException, ParseException {
		xmlFile = new File(classLoader.getResource("./Validation/testValidateFloatFieldWithMinOnly.xml").getFile());
		assertFalse(ValidationUtil.validStandart(xmlFile));
	}

	@Test
	public void testValidateFloatFieldWithMaxOnly() throws JDOMException, IOException, ParseException {
		xmlFile = new File(classLoader.getResource("./Validation/testValidateFloatFieldWithMaxOnly.xml").getFile());
		assertFalse(ValidationUtil.validStandart(xmlFile));
	}

	@Test
	public void testValidateDateField() throws JDOMException, IOException, ParseException {
		xmlFile = new File(classLoader.getResource("./Validation/testValidateDateField.xml").getFile());
		assertFalse(ValidationUtil.validStandart(xmlFile));
	}

	@Test
	public void testValidateIntegerField() throws JDOMException, IOException, ParseException {
		xmlFile = new File(classLoader.getResource("./Validation/testValidateIntegerField.xml").getFile());
		assertFalse(ValidationUtil.validStandart(xmlFile));
	}

	@Test
	public void testValidateIntegerFieldWithMinOnly() throws JDOMException, IOException, ParseException {
		xmlFile = new File(classLoader.getResource("./Validation/testValidateIntegerFieldWithMinOnly.xml").getFile());
		assertFalse(ValidationUtil.validStandart(xmlFile));
	}

	@Test
	public void testValidateIntegerFieldWithMaxOnly() throws JDOMException, IOException, ParseException {
		xmlFile = new File(classLoader.getResource("./Validation/testValidateIntegerFieldWithMaxOnly.xml").getFile());
		assertFalse(ValidationUtil.validStandart(xmlFile));
	}

	@Test
	public void testValidateBooleanField() throws JDOMException, IOException, ParseException {
		xmlFile = new File(classLoader.getResource("./Validation/testValidateBooleanField.xml").getFile());
		assertFalse(ValidationUtil.validStandart(xmlFile));
	}

	@Test
	public void testValidateBooleanFieldInvalidIsNumeric() throws JDOMException, IOException, ParseException {
		xmlFile = new File(
				classLoader.getResource("./Validation/testValidateBooleanFieldInvalidIsNumeric.xml").getFile());
		assertFalse(ValidationUtil.validStandart(xmlFile));
	}

	@Test
	public void testValidateLongField() throws JDOMException, IOException, ParseException {
		xmlFile = new File(classLoader.getResource("./Validation/testValidateLongField.xml").getFile());
		assertFalse(ValidationUtil.validStandart(xmlFile));
	}

	@Test
	public void testValidateLongFieldWithMinOnly() throws JDOMException, IOException, ParseException {
		xmlFile = new File(classLoader.getResource("./Validation/testValidateLongFieldWithMinOnly.xml").getFile());
		assertFalse(ValidationUtil.validStandart(xmlFile));
	}

	@Test
	public void testValidateLongFieldWithMaxOnly() throws JDOMException, IOException, ParseException {
		xmlFile = new File(classLoader.getResource("./Validation/testValidateLongFieldWithMaxOnly.xml").getFile());
		assertFalse(ValidationUtil.validStandart(xmlFile));
	}

	@Test
	public void testValidateTimeField() throws JDOMException, IOException, ParseException {
		xmlFile = new File(classLoader.getResource("./Validation/testValidateTimeField.xml").getFile());
		assertFalse(ValidationUtil.validStandart(xmlFile));
	}

	@Test
	public void testValidateFieldWithoutType() throws JDOMException, IOException, ParseException {
		xmlFile = new File(classLoader.getResource("./Validation/testValidateFieldWithoutType.xml").getFile());
		assertFalse(ValidationUtil.validStandart(xmlFile));
	}

	@Test
	public void testValidateOptionalFieldWithoutValidChildrens() throws JDOMException, IOException, ParseException {
		xmlFile = new File(
				classLoader.getResource("./Validation/testValidateOptionalFieldWithoutValidChildrens.xml").getFile());
		assertFalse(ValidationUtil.validStandart(xmlFile));
	}

	@Test
	public void testValidateOptionalFieldWithValidChildrens() throws JDOMException, IOException, ParseException {
		xmlFile = new File(
				classLoader.getResource("./Validation/testValidateOptionalFieldWithValidChildrens.xml").getFile());
		assertTrue(ValidationUtil.validStandart(xmlFile));
	}

	@Test
	public void testInvalidFieldInsideField() throws JDOMException, IOException, ParseException {
		xmlFile = new File(classLoader.getResource("./Validation/testInvalidFieldInsideField.xml").getFile());
		assertFalse(ValidationUtil.validStandart(xmlFile));
	}

	@Test
	public void testValidFieldInsideField() throws JDOMException, IOException, ParseException {
		xmlFile = new File(classLoader.getResource("./Validation/testValidFieldInsideField.xml").getFile());
		assertTrue(ValidationUtil.validStandart(xmlFile));
	}

	@Test
	public void testComplexTypeWithoutValidType() throws JDOMException, IOException, ParseException {
		xmlFile = new File(classLoader.getResource("./Validation/testComplexTypeWithoutValidType.xml").getFile());
		assertFalse(ValidationUtil.validStandart(xmlFile));
	}

	@Test
	public void testInvalidAttributeType() throws JDOMException, IOException, ParseException {
		xmlFile = new File(classLoader.getResource("./Validation/testInvalidAttributeType.xml").getFile());
		assertFalse(ValidationUtil.validStandart(xmlFile));
	}

	@Test
	public void testValidComplexTypeWithAttributes() throws JDOMException, IOException, ParseException {
		xmlFile = new File(classLoader.getResource("./Validation/testValidComplexTypeWithAttributes.xml").getFile());
		assertTrue(ValidationUtil.validStandart(xmlFile));
	}

	@Test
	public void testInvalidTag() throws JDOMException, IOException, ParseException {
		xmlFile = new File(classLoader.getResource("./Validation/testInvalidTag.xml").getFile());
		assertFalse(ValidationUtil.validStandart(xmlFile));
	}

}
