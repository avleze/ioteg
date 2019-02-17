package com.ioteg.resultmodel.xmlserializers;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.ioteg.resultmodel.ResultComplexField;
import com.ioteg.resultmodel.ResultField;
import com.ioteg.resultmodel.ResultSimpleField;

public class XMLResultComplexFieldSerializer implements XMLSerializer<ResultComplexField> {

	private static XMLResultSimpleFieldSerializer xmlResultSimpleFieldSerializer;

	static {
		xmlResultSimpleFieldSerializer = new XMLResultSimpleFieldSerializer();
	}
	
	@Override
	public void serialize(ResultComplexField value, XMLGenerator xmlGen) throws IOException {

		Map<String, String> attributes = new HashMap<>();
		attributes.put("type", value.getType());
		
		if (!value.getIsAComplexFieldFormedWithAttributes()) {
			xmlGen.writeStartFieldWithAttributes(value.getName(), attributes, true);
			for(ResultField field : value.getValue())
				xmlResultSimpleFieldSerializer.serialize((ResultSimpleField) field, xmlGen);
			xmlGen.writeEndField(value.getName(), true);
		} else {
			StringBuilder str = new StringBuilder();
			for (ResultField r : value.getValue())
				str.append(((ResultSimpleField) r).getValue());

			xmlGen.writeStartFieldWithAttributes(value.getName(), attributes, false);
			xmlGen.writeRaw(str.toString());
			xmlGen.writeEndField(value.getName(), false);
		}
	}

}
