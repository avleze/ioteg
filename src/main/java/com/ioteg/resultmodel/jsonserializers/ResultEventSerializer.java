package com.ioteg.resultmodel.jsonserializers;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.ioteg.resultmodel.ArrayResultBlock;
import com.ioteg.resultmodel.ResultEvent;

/**
 * <p>ResultEventSerializer class.</p>
 *
 * @author antonio
 * @version $Id: $Id
 */
public class ResultEventSerializer extends StdSerializer<ResultEvent> {

	private static final long serialVersionUID = 1L;

	/**
	 * <p>Constructor for ResultEventSerializer.</p>
	 */
	public ResultEventSerializer() {
		this(null);
	}

	/**
	 * <p>Constructor for ResultEventSerializer.</p>
	 *
	 * @param t a {@link java.lang.Class} object.
	 */
	public ResultEventSerializer(Class<ResultEvent> t) {
		super(t);
	}

	/** {@inheritDoc} */
	@Override
	public void serialize(ResultEvent value, JsonGenerator jgen, SerializerProvider provider) throws IOException {

		jgen.writeStartObject();

		if (value.getArrayResultBlocks() != null) {
			for (ArrayResultBlock arrayResultBlock : value.getArrayResultBlocks())
				jgen.writeObjectField(arrayResultBlock.getResultBlocks().get(0).getName(), arrayResultBlock);
		}
		
		jgen.writeEndObject();

	}
}
