package com.ioteg.resultmodel.xmlserializers;

import java.io.IOException;

import com.ioteg.resultmodel.ArrayResultBlock;
import com.ioteg.resultmodel.ResultEvent;

/**
 * <p>XMLResultEventSerializer class.</p>
 *
 * @author antonio
 * @version $Id: $Id
 */
public class XMLResultEventSerializer implements XMLSerializer<ResultEvent>{
	
	private static XMLArrayResultBlockSerializer xmlArrayResultBlockSerializer;

	static {
		xmlArrayResultBlockSerializer = new XMLArrayResultBlockSerializer();
	}
	
	/** {@inheritDoc} */
	public void serialize(ResultEvent value, XMLGenerator xmlGen) throws IOException {
		xmlGen.writeStartField("xml", true);

		for (ArrayResultBlock arrayResultBlock : value.getArrayResultBlocks())
			xmlArrayResultBlockSerializer.serialize(arrayResultBlock, xmlGen);

		xmlGen.writeEndField("xml", true);
	}

}
