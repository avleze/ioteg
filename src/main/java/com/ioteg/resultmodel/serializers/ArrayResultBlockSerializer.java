package com.ioteg.resultmodel.serializers;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.ioteg.resultmodel.ArrayResultBlock;
import com.ioteg.resultmodel.ResultBlock;

public class ArrayResultBlockSerializer extends StdSerializer<ArrayResultBlock> {

	private static final long serialVersionUID = 1L;

	public ArrayResultBlockSerializer(Class<ArrayResultBlock> t) {
		super(t);
	}

	@Override
	public void serialize(ArrayResultBlock value, JsonGenerator jgen, SerializerProvider provider)
			throws IOException, JsonProcessingException {

		if (value.getResultBlocks().size() != 1) {

			jgen.writeFieldName("feeds");
			jgen.writeStartArray();
			for (ResultBlock resultBlock : value.getResultBlocks()) {
				jgen.writeObject(resultBlock);
			}
			jgen.writeEndArray();

		} else {
			jgen.writeObjectField(value.getResultBlocks().get(0).getName(), value.getResultBlocks().get(0));
		}
	}
}