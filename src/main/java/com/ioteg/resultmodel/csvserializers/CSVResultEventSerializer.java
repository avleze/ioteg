package com.ioteg.resultmodel.csvserializers;

import java.io.IOException;

import com.ioteg.resultmodel.ArrayResultBlock;
import com.ioteg.resultmodel.ResultEvent;

/**
 * <p>CSVResultEventSerializer class.</p>
 *
 * @author antonio
 * @version $Id: $Id
 */
public class CSVResultEventSerializer implements CSVSerializer<ResultEvent> {
	 
	private static CSVArrayResultBlockSerializer csvArrayResultBlockSerializer;
	
	static {
		csvArrayResultBlockSerializer = new CSVArrayResultBlockSerializer();
	}
	
	
	/** {@inheritDoc} */
	@Override
	public void serialize(ResultEvent value, CSVGenerator csvGen) throws IOException {
		ArrayResultBlock blockWithRepTag = null;
		for(ArrayResultBlock arrayResultBlock : value.getArrayResultBlocks()) {
			if(arrayResultBlock.getHasRepetitionTag())
			{
				blockWithRepTag = arrayResultBlock;
				break;
			}
		}
		
		csvArrayResultBlockSerializer.serialize(blockWithRepTag, csvGen);
	}

}
