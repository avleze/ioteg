package com.ioteg.resultmodel.jsonserializers;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.ioteg.resultmodel.ResultBlock;
import com.ioteg.resultmodel.ResultField;
import com.ioteg.resultmodel.ResultSimpleField;

/**
 * <p>ResultBlockSerializer class.</p>
 *
 * @author antonio
 * @version $Id: $Id
 */
public class ResultBlockSerializer extends StdSerializer<ResultBlock> {
    
	private static final long serialVersionUID = 1L;

	/**
	 * <p>Constructor for ResultBlockSerializer.</p>
	 */
	public ResultBlockSerializer() {
		this(null);
	}
	
	/**
	 * <p>Constructor for ResultBlockSerializer.</p>
	 *
	 * @param t a {@link java.lang.Class} object.
	 */
	public ResultBlockSerializer(Class<ResultBlock> t) {
		super(t);
	}

	/** {@inheritDoc} */
	@Override
	public void serialize(ResultBlock value, JsonGenerator jgen, SerializerProvider provider)
			throws IOException {

		jgen.writeStartObject();
		for (ResultField resultField : value.getResultFields()) {
			if (resultField instanceof ResultSimpleField) {
				ResultSimpleField resultSimpleField = (ResultSimpleField) resultField;
				jgen.writeObject(resultSimpleField);
			}
			else {
				jgen.writeObjectField(resultField.getName(), resultField);
			}
		}
		jgen.writeEndObject();

	}
}
