package com.ioteg.resultmodel.jsonserializers;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.ioteg.resultmodel.ResultComplexField;
import com.ioteg.resultmodel.ResultField;
import com.ioteg.resultmodel.ResultSimpleField;

/**
 * <p>ResultComplexFieldSerializer class.</p>
 *
 * @author antonio
 * @version $Id: $Id
 */
public class ResultComplexFieldSerializer extends StdSerializer<ResultComplexField> {

	private static final long serialVersionUID = 1L;

	/**
	 * <p>Constructor for ResultComplexFieldSerializer.</p>
	 */
	public ResultComplexFieldSerializer() {
		this(null);
	}

	/**
	 * <p>Constructor for ResultComplexFieldSerializer.</p>
	 *
	 * @param t a {@link java.lang.Class} object.
	 */
	public ResultComplexFieldSerializer(Class<ResultComplexField> t) {
		super(t);
	}

	/** {@inheritDoc} */
	@Override
	public void serialize(ResultComplexField value, JsonGenerator jgen, SerializerProvider provider)
			throws IOException {

		if (!value.getIsAComplexFieldFormedWithAttributes()) {
			jgen.writeStartObject();
			for (ResultField r : value.getValue())
				if (r instanceof ResultSimpleField)
					jgen.writeObject(r);
				else {
					ResultComplexField rCF = (ResultComplexField) r;
					jgen.writeObjectField(rCF.getName(), rCF);
				}

			jgen.writeEndObject();
		} else {
			StringBuilder str = new StringBuilder();
			for (ResultField r : value.getValue())
				str.append(((ResultSimpleField) r).getValue());

			if (value.getQuotes())
				jgen.writeString(str.toString());
			else
				jgen.writeRawValue(str.toString());
		}
	}
}
