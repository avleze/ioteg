package com.ioteg.resultmodel.serializers;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.ioteg.resultmodel.ResultBlock;
import com.ioteg.resultmodel.ResultField;
import com.ioteg.resultmodel.ResultSimpleField;

public class ResultBlockSerializer extends StdSerializer<ResultBlock> {
    
	private static final long serialVersionUID = 1L;

	public ResultBlockSerializer(Class<ResultBlock> t) {
		super(t);
	}

	@Override
	public void serialize(ResultBlock value, JsonGenerator jgen, SerializerProvider provider)
			throws IOException, JsonProcessingException {

		jgen.writeStartObject();
		for (ResultField resultField : value.getResultFields()) {
			if (resultField instanceof ResultSimpleField) {
				ResultSimpleField resultSimpleField = (ResultSimpleField) resultField;
				jgen.writeObject(resultSimpleField);
			}
			else {
				jgen.writeObject(resultField);
			}
		}
		jgen.writeEndObject();

	}
}