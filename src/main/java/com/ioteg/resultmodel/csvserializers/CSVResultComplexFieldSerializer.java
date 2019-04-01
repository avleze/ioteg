package com.ioteg.resultmodel.csvserializers;

import java.io.IOException;

import com.ioteg.resultmodel.ResultComplexField;
import com.ioteg.resultmodel.ResultField;
import com.ioteg.resultmodel.ResultSimpleField;

/**
 * <p>CSVResultComplexFieldSerializer class.</p>
 *
 * @author antonio
 * @version $Id: $Id
 */
public class CSVResultComplexFieldSerializer implements CSVSerializer<ResultComplexField> {

	/** {@inheritDoc} */
	@Override
	public void serialize(ResultComplexField value, CSVGenerator csvGen) throws IOException {

		if (!value.getIsAComplexFieldFormedWithAttributes()) {
			for (ResultField r : value.getValue())
				if (r instanceof ResultSimpleField) {
					ResultSimpleField rF = (ResultSimpleField) r;
					StringBuilder val = new StringBuilder(rF.getValue());
					if (rF.getQuotes()) {
						val.insert(0, "\"");
						val.append("\"");
					}
					csvGen.writeField(value.getName() + "." + rF.getName(), val.toString());
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
