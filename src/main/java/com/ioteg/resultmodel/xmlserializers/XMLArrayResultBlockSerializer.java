package com.ioteg.resultmodel.xmlserializers;

import java.io.IOException;

import com.ioteg.resultmodel.ArrayResultBlock;
import com.ioteg.resultmodel.ResultBlock;

public class XMLArrayResultBlockSerializer implements XMLSerializer<ArrayResultBlock>{	
	
	private static XMLResultBlockSerializer xmlResultBlockSerializer;

	static {
		xmlResultBlockSerializer = new XMLResultBlockSerializer();
	}
	
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
