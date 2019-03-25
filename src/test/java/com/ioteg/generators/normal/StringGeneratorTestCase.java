package com.ioteg.generators.normal;

import static org.hamcrest.MatcherAssert.assertThat;
import org.junit.jupiter.api.Test;

import com.ioteg.exprlang.ExprParser.ExprLangParsingException;
import com.ioteg.generators.Generable;
import com.ioteg.generators.GeneratorsFactory;
import com.ioteg.generators.exceptions.NotExistingGeneratorException;
import com.ioteg.model.Field;
import com.ioteg.resultmodel.ResultSimpleField;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.matchesPattern;

public class StringGeneratorTestCase {

	@Test
	public void testRandomWithDefaultLength() throws NotExistingGeneratorException, ExprLangParsingException {
		Field field = new Field();
		field.setName("test");
		field.setType("String");
		field.setLength(10);

		/* <field name="test" type="String"></field> */

		Generable generator = GeneratorsFactory.makeGenerator(field, null);
		String strResult = ((ResultSimpleField) generator.generate(1).get(0)).getValue();

		assertThat(strResult, matchesPattern("[ABCDEFGHIJKLMNOPQRSTUVWXYZ]{10}"));
	}

	@Test
	public void testRandomWithSpecifiedLength() throws NotExistingGeneratorException, ExprLangParsingException {
		Field field = new Field();
		field.setName("test");
		field.setType("String");
		field.setLength(24);

		/* <field name="test" type="String" length="24"></field> */

		Generable generator = GeneratorsFactory.makeGenerator(field, null);
		String strResult = ((ResultSimpleField) generator.generate(1).get(0)).getValue();

		assertThat(strResult, matchesPattern("[ABCDEFGHIJKLMNOPQRSTUVWXYZ]{24}"));
	}

	@Test
	public void testRandomWithLowercase() throws NotExistingGeneratorException, ExprLangParsingException {
		Field field = new Field();
		field.setName("test");
		field.setType("String");
		field.setLength(24);
		field.setCase("low");

		/* <field name="test" type="String" case="low" length="24"></field> */

		Generable generator = GeneratorsFactory.makeGenerator(field, null);
		String strResult = ((ResultSimpleField) generator.generate(1).get(0)).getValue();

		assertThat(strResult, matchesPattern("[abcdefghijklmnopqrstuvwxyz]{24}"));
	}

	@Test
	public void testRandomWithDefaultLengthAndLowercase() throws NotExistingGeneratorException, ExprLangParsingException {
		Field field = new Field();
		field.setName("test");
		field.setType("String");
		field.setLength(10);
		field.setCase("low");

		/* <field name="test" type="String" case="low"></field> */

		Generable generator = GeneratorsFactory.makeGenerator(field, null);
		String strResult = ((ResultSimpleField) generator.generate(1).get(0)).getValue();

		assertThat(strResult, matchesPattern("[abcdefghijklmnopqrstuvwxyz]{10}"));
	}

	@Test
	public void testRandomWithDefaultLengthAndEndCharacter() throws NotExistingGeneratorException, ExprLangParsingException {
		Field field = new Field();
		field.setName("test");
		field.setType("String");
		field.setLength(10);
		field.setEndcharacter("G");
		field.setCase("low");
		
		/* <field name="test" type="String" case="low" endcharacter="G"></field> */

		Generable generator = GeneratorsFactory.makeGenerator(field, null);
		String strResult = ((ResultSimpleField) generator.generate(1).get(0)).getValue();

		assertThat(strResult, matchesPattern("[abcdefg]*"));
	}

	@Test
	public void testRandomWithSpecifiedLengthAndEndCharacter() throws NotExistingGeneratorException, ExprLangParsingException
			 {
		Field field = new Field();
		field.setName("test");
		field.setType("String");
		field.setLength(24);
		field.setEndcharacter("G");
		field.setCase("low");
		
		/*
		 * <field name="test" type="String" case="low" endcharacter="G"
		 * length="24"></field>
		 */

		Generable generator = GeneratorsFactory.makeGenerator(field, null);
		String strResult = ((ResultSimpleField) generator.generate(1).get(0)).getValue();

		assertThat(strResult, matchesPattern("[abcdefg]{24}"));
	}
	/**/

	@Test
	public void testFixedValue() throws NotExistingGeneratorException, ExprLangParsingException
			 {
		Field field = new Field();
		field.setName("test");
		field.setType("String");
		field.setValue("ABC");

		/* <field name="test" type="String" value="ABC"></field> */

		Generable generator = GeneratorsFactory.makeGenerator(field, null);
		String strResult = ((ResultSimpleField) generator.generate(1).get(0)).getValue();

		assertThat(strResult, equalTo("ABC"));
	}
}
