package com.ioteg.resultmodel.csvserializers;

import java.io.IOException;

import com.ioteg.resultmodel.ResultBlock;
import com.ioteg.resultmodel.ResultComplexField;
import com.ioteg.resultmodel.ResultField;
import com.ioteg.resultmodel.ResultSimpleField;

public class CSVResultBlockSerializer implements CSVSerializer<ResultBlock> {

	private static CSVResultComplexFieldSerializer csvResultComplexFieldSerializer;

	static {
		csvResultComplexFieldSerializer = new CSVResultComplexFieldSerializer();
	}
	
	@Override
	public void serialize(ResultBlock value, CSVGenerator csvGen) throws IOException {
		csvGen.writeStartObject();
		
		for (ResultField resultField : value.getResultFields()) {
			if (resultField instanceof ResultSimpleField) {
				ResultSimpleField resultSimpleField = (ResultSimpleField) resultField;
				
				String val = resultSimpleField.getValue();
				if(resultSimpleField.getQuotes())
					val = "\"" + val + "\"";
				csvGen.writeField(resultSimpleField.getName(), val);
				
			} else {
				ResultComplexField rCF = (ResultComplexField) resultField;
				csvResultComplexFieldSerializer.serialize(rCF, csvGen);
			}
		}
		
		csvGen.writeEndObject();
	}

}
