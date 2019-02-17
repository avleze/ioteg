package com.ioteg.resultmodel.jsonserializers;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
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
			throws IOException {

		if (value.getHasRepetitionTag()) {
			jgen.writeStartArray();
			for (ResultBlock resultBlock : value.getResultBlocks())
				jgen.writeObject(resultBlock);
			jgen.writeEndArray();

		} else {
			jgen.writeObject(value.getResultBlocks().get(0));
		}
	}
}