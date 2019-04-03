package com.ioteg.serializers.xml;

import java.io.IOException;

import com.ioteg.resultmodel.ArrayResultBlock;
import com.ioteg.resultmodel.ResultBlock;

/**
 * <p>XMLArrayResultBlockSerializer class.</p>
 *
 * @author antonio
 * @version $Id: $Id
 */
public class XMLArrayResultBlockSerializer implements XMLSerializer<ArrayResultBlock>{	
	
	private static XMLResultBlockSerializer xmlResultBlockSerializer;

	static {
		xmlResultBlockSerializer = new XMLResultBlockSerializer();
	}
	
	/** {@inheritDoc} */
	@Override
	public void serialize(ArrayResultBlock value, XMLGenerator xmlGen) throws IOException {
		xmlGen.writeStartField(value.getResultBlocks().get(0).getName(), true);
		
		if(value.getHasRepetitionTag())
			for(ResultBlock block : value.getResultBlocks())
			{
				xmlGen.writeStartField("feed", true);
				xmlResultBlockSerializer.serialize(block, xmlGen);
				xmlGen.writeEndField("feed", true);
			}
		else 
			xmlResultBlockSerializer.serialize(value.getResultBlocks().get(0), xmlGen);
		
		xmlGen.writeEndField(value.getResultBlocks().get(0).getName(), true);
	}
	
	
}
