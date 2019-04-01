package com.ioteg.resultmodel.jsonserializers;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.ioteg.resultmodel.ArrayResultBlock;
import com.ioteg.resultmodel.ResultBlock;

/**
 * <p>ArrayResultBlockSerializer class.</p>
 *
 * @author antonio
 * @version $Id: $Id
 */
public class ArrayResultBlockSerializer extends StdSerializer<ArrayResultBlock> {

	private static final long serialVersionUID = 1L;

	/**
	 * <p>Constructor for ArrayResultBlockSerializer.</p>
	 */
	public ArrayResultBlockSerializer() {
		this(null);
	}
	
	/**
	 * <p>Constructor for ArrayResultBlockSerializer.</p>
	 *
	 * @param t a {@link java.lang.Class} object.
	 */
	public ArrayResultBlockSerializer(Class<ArrayResultBlock> t) {
		super(t);
	}

	/** {@inheritDoc} */
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
