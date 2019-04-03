package com.ioteg.serializers.csv;

import java.io.IOException;

import com.ioteg.resultmodel.ArrayResultBlock;
import com.ioteg.resultmodel.ResultBlock;

/**
 * <p>CSVArrayResultBlockSerializer class.</p>
 *
 * @author antonio
 * @version $Id: $Id
 */
public class CSVArrayResultBlockSerializer implements CSVSerializer<ArrayResultBlock> {
	 
	private static CSVResultBlockSerializer csvResultBlockSerializer;
	
	static {
		csvResultBlockSerializer = new CSVResultBlockSerializer();
	}
	
	
	/** {@inheritDoc} */
	@Override
	public void serialize(ArrayResultBlock value, CSVGenerator csvGen) throws IOException {
		for(ResultBlock resultBlock : value.getResultBlocks())
			csvResultBlockSerializer.serialize(resultBlock, csvGen);
	}

}
