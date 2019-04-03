package com.ioteg.serializers.json;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.ioteg.resultmodel.ResultSimpleField;

/**
 * <p>ResultSimpleFieldSerializer class.</p>
 *
 * @author antonio
 * @version $Id: $Id
 */
public class ResultSimpleFieldSerializer extends StdSerializer<ResultSimpleField> {
    
	private static final long serialVersionUID = 1L;

	/**
	 * <p>Constructor for ResultSimpleFieldSerializer.</p>
	 */
	public ResultSimpleFieldSerializer() {
		this(null);
	}
	
	
	/**
	 * <p>Constructor for ResultSimpleFieldSerializer.</p>
	 *
	 * @param t a {@link java.lang.Class} object.
	 */
	public ResultSimpleFieldSerializer(Class<ResultSimpleField> t) {
		super(t);
	}

	/** {@inheritDoc} */
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
