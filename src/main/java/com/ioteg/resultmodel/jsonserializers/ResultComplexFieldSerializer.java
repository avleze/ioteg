package com.ioteg.resultmodel.jsonserializers;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.ioteg.resultmodel.ResultComplexField;
import com.ioteg.resultmodel.ResultField;
import com.ioteg.resultmodel.ResultSimpleField;

public class ResultComplexFieldSerializer extends StdSerializer<ResultComplexField> {
    
	private static final long serialVersionUID = 1L;

	public ResultComplexFieldSerializer(Class<ResultComplexField> t) {
		super(t);
	}

	@Override
    public void serialize(
    		ResultComplexField value, JsonGenerator jgen, SerializerProvider provider) 
      throws IOException {
		
		if(!value.getIsAComplexFieldFormedWithAttributes())
		{			
			jgen.writeStartObject();
				for(ResultField r : value.getValue())
					if(r instanceof ResultSimpleField)
						jgen.writeObject(r);
					else
					{
						ResultComplexField rCF = (ResultComplexField) r;
						if(rCF.getIsAComplexFieldFormedWithAttributes())
							jgen.writeObject(rCF);
						else
							jgen.writeObjectField(rCF.getName(), rCF);
					}
				
			jgen.writeEndObject();
		}
		else
		{
			StringBuilder str = new StringBuilder();
				for(ResultField r : value.getValue())
					str.append(((ResultSimpleField)r).getValue());
				
			jgen.writeFieldName(value.getName());
			if(value.getQuotes())
				jgen.writeString(str.toString());
			else
				jgen.writeRawValue(str.toString());
		}
    }
}