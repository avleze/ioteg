package com.ioteg.generators.normal;

import static org.hamcrest.MatcherAssert.assertThat;
import org.junit.jupiter.api.Test;

import com.ioteg.generators.Generable;
import com.ioteg.generators.GeneratorsFactory;
import com.ioteg.model.Field;
import com.ioteg.resultmodel.ResultField;
import com.ioteg.resultmodel.ResultSimpleField;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.matchesPattern;

import java.util.List;

public class StringGeneratorTestCase {

	@Test
	public void testRandomWithDefaultLength() throws Exception {
		Field field = new Field("test", false, "String");
		field.setLength(10);

		/* <field name="test" type="String"></field> */

		Generable generator = GeneratorsFactory.makeGenerator(field, null);
		String strResult = ((ResultSimpleField) generator.generate(1).get(0)).getValue();

		assertThat(strResult, matchesPattern("[ABCDEFGHIJKLMNOPQRSTUVWXYZ]{10}"));
	}

	@Test
	public void testRandomWithSpecifiedLength() throws Exception {
		Field field = new Field("test", false, "String");
		field.setLength(24);

		/* <field name="test" type="String" length="24"></field> */

		Generable generator = GeneratorsFactory.makeGenerator(field, null);
		String strResult = ((ResultSimpleField) generator.generate(1).get(0)).getValue();

		assertThat(strResult, matchesPattern("[ABCDEFGHIJKLMNOPQRSTUVWXYZ]{24}"));
	}

	@Test
	public void testRandomWithLowercase() throws Exception {
		Field field = new Field("test", false, "String");
		field.setLength(24);
		field.setCase("low");

		/* <field name="test" type="String" case="low" length="24"></field> */

		Generable generator = GeneratorsFactory.makeGenerator(field, null);
		String strResult = ((ResultSimpleField) generator.generate(1).get(0)).getValue();

		assertThat(strResult, matchesPattern("[abcdefghijklmnopqrstuvwxyz]{24}"));
	}

	@Test
	public void testRandomWithDefaultLengthAndLowercase()
			throws Exception {
		Field field = new Field("test", false, "String");
		field.setLength(10);
		field.setCase("low");

		/* <field name="test" type="String" case="low"></field> */

		Generable generator = GeneratorsFactory.makeGenerator(field, null);
		String strResult = ((ResultSimpleField) generator.generate(1).get(0)).getValue();

		assertThat(strResult, matchesPattern("[abcdefghijklmnopqrstuvwxyz]{10}"));
	}

	@Test
	public void testRandomWithDefaultLengthAndEndCharacter()
			throws Exception {
		Field field = new Field("test", false, "String");
		field.setLength(10);
		field.setEndcharacter("G");
		field.setCase("low");

		/* <field name="test" type="String" case="low" endcharacter="G"></field> */

		Generable generator = GeneratorsFactory.makeGenerator(field, null);
		String strResult = ((ResultSimpleField) generator.generate(1).get(0)).getValue();

		assertThat(strResult, matchesPattern("[abcdefg]*"));
	}

	@Test
	public void testRandomWithSpecifiedLengthAndEndCharacter()
			throws Exception {
		Field field = new Field("test", false, "String");
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
	public void testFixedValue() throws Exception {
		Field field = new Field("test", false, "String");
		field.setValue("ABC");

		/* <field name="test" type="String" value="ABC"></field> */

		Generable generator = GeneratorsFactory.makeGenerator(field, null);
		String strResult = ((ResultSimpleField) generator.generate(1).get(0)).getValue();

		assertThat(strResult, equalTo("ABC"));
	}

	@Test
	public void testSequential() throws Exception {
		Field field = new Field("test", false, "String");
		field.setBegin("Z");
		field.setEnd("AE");
		field.setStep("2");

		/* <field name="test" type="String" begin="Z" step="2" end="AE"></field> */

		Generable generator = GeneratorsFactory.makeGenerator(field, null);
		List<ResultField> results = generator.generate(4);

		ResultSimpleField rSF = (ResultSimpleField) results.get(0);
		assertThat(rSF.getValue(), equalTo("Z"));

		rSF = (ResultSimpleField) results.get(1);
		assertThat(rSF.getValue(), equalTo("AB"));

		rSF = (ResultSimpleField) results.get(2);
		assertThat(rSF.getValue(), equalTo("AD"));

		rSF = (ResultSimpleField) results.get(3);
		assertThat(rSF.getValue(), equalTo("Z"));

		
		field.setBegin("AE");
		field.setEnd("Z");
		field.setStep("-2");
		/* <field name="test" type="String" begin="AE" step="-2" end="Z"></field> */


		generator = GeneratorsFactory.makeGenerator(field, null);
		results = generator.generate(4);

		rSF = (ResultSimpleField) results.get(0);
		assertThat(rSF.getValue(), equalTo("AE"));

		rSF = (ResultSimpleField) results.get(1);
		assertThat(rSF.getValue(), equalTo("AC"));

		rSF = (ResultSimpleField) results.get(2);
		assertThat(rSF.getValue(), equalTo("AA"));

		rSF = (ResultSimpleField) results.get(3);
		assertThat(rSF.getValue(), equalTo("AE"));
	}
}
