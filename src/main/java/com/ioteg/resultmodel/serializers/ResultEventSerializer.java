package com.ioteg.resultmodel.serializers;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.ioteg.resultmodel.ArrayResultBlock;
import com.ioteg.resultmodel.ResultEvent;

public class ResultEventSerializer extends StdSerializer<ResultEvent> {

	private static final long serialVersionUID = 1L;

	public ResultEventSerializer(Class<ResultEvent> t) {
		super(t);
	}

	@Override
	public void serialize(ResultEvent value, JsonGenerator jgen, SerializerProvider provider)
			throws IOException, JsonProcessingException {

		jgen.writeStartObject();
		for(ArrayResultBlock arrayResultBlock : value.getArrayResultBlocks())
		{
			jgen.writeObject(arrayResultBlock);
			
		}
		jgen.writeEndObject();

	}
}