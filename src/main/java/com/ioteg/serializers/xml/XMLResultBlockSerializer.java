package com.ioteg.serializers.xml;

import java.io.IOException;
import com.ioteg.resultmodel.ResultBlock;
import com.ioteg.resultmodel.ResultComplexField;
import com.ioteg.resultmodel.ResultField;
import com.ioteg.resultmodel.ResultSimpleField;

/**
 * <p>XMLResultBlockSerializer class.</p>
 *
 * @author antonio
 * @version $Id: $Id
 */
public class XMLResultBlockSerializer implements XMLSerializer<ResultBlock> {

	private static XMLResultComplexFieldSerializer xmlResultComplexFieldSerializer;
	private static XMLResultSimpleFieldSerializer xmlResultSimpleFieldSerializer;

	static {
		xmlResultComplexFieldSerializer = new XMLResultComplexFieldSerializer();
		xmlResultSimpleFieldSerializer = new XMLResultSimpleFieldSerializer();
	}

	/** {@inheritDoc} */
	@Override
	public void serialize(ResultBlock resultBlock, XMLGenerator xmlGen) throws IOException {

		for (ResultField resultField : resultBlock.getResultFields()) {
			if (resultField instanceof ResultSimpleField) {
				ResultSimpleField resultSimpleField = (ResultSimpleField) resultField;
				xmlResultSimpleFieldSerializer.serialize(resultSimpleField, xmlGen);
			} else {
				xmlResultComplexFieldSerializer.serialize((ResultComplexField) resultField, xmlGen);
			}
		}

	}

}
