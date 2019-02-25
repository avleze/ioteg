package com.ioteg.resultmodel.csvserializers;

import java.io.IOException;

import com.ioteg.resultmodel.ResultComplexField;
import com.ioteg.resultmodel.ResultField;
import com.ioteg.resultmodel.ResultSimpleField;

public class CSVResultComplexFieldSerializer implements CSVSerializer<ResultComplexField> {

	@Override
	public void serialize(ResultComplexField value, CSVGenerator csvGen) throws IOException {

		if (!value.getIsAComplexFieldFormedWithAttributes()) {
			for (ResultField r : value.getValue())
				if (r instanceof ResultSimpleField) {
					ResultSimpleField rF = (ResultSimpleField) r;
					String val = rF.getValue();
					if (rF.getQuotes())
						val = "\"" + val + "\"";
					csvGen.writeField(value.getName() + "." + rF.getName(), val);
				}
		} else {
			StringBuilder str = new StringBuilder();
			for (ResultField r : value.getValue())
				str.append(((ResultSimpleField) r).getValue());

			String val = str.toString();
			if (value.getQuotes())
				val = "\"" + val + "\"";
			csvGen.writeField(value.getName(), val);
		}

	}

}
