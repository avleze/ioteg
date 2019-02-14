package com.ioteg.resultmodel.serializers;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.ioteg.resultmodel.ResultSimpleField;

public class ResultSimpleFieldSerializer extends StdSerializer<ResultSimpleField> {
    
	private static final long serialVersionUID = 1L;

	public ResultSimpleFieldSerializer(Class<ResultSimpleField> t) {
		super(t);
	}

	@Override
    public void serialize(
    		ResultSimpleField value, JsonGenerator jgen, SerializerProvider provider) 
      throws IOException, JsonProcessingException {

		jgen.writeFieldName(value.getName());
		jgen.writeRawValue(value.getValue());
    }
}