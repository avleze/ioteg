package com.ioteg.resultmodel.jsonserializers;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.ioteg.resultmodel.ResultSimpleField;

public class ResultSimpleFieldSerializer extends StdSerializer<ResultSimpleField> {
    
	private static final long serialVersionUID = 1L;

	public ResultSimpleFieldSerializer() {
		this(null);
	}
	
	
	public ResultSimpleFieldSerializer(Class<ResultSimpleField> t) {
		super(t);
	}

	@Override
    public void serialize(
    		ResultSimpleField value, JsonGenerator jgen, SerializerProvider provider) 
      throws IOException {

		jgen.writeFieldName(value.getName());
		if(value.getQuotes())
			jgen.writeString(value.getValue());
		else
			jgen.writeRawValue(value.getValue());

    }
}