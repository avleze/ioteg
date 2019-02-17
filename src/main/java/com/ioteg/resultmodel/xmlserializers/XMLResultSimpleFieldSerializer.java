package com.ioteg.resultmodel.xmlserializers;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.ioteg.resultmodel.ResultSimpleField;

public class XMLResultSimpleFieldSerializer implements XMLSerializer<ResultSimpleField> {

	@Override
	public void serialize(ResultSimpleField value, XMLGenerator xmlGen) throws IOException {

		Map<String, String> attributes = new HashMap<>();
		attributes.put("type", value.getType());
		xmlGen.writeStartFieldWithAttributes(value.getName(), attributes, false);
		String val = value.getValue();
		if(value.getQuotes())
			val = "\"" + val + "\"";
		
		xmlGen.writeRaw(val);
		xmlGen.writeEndField(value.getName(), false);
	}

}