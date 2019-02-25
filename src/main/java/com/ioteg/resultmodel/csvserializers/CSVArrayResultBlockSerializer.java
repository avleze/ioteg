package com.ioteg.resultmodel.csvserializers;

import java.io.IOException;

import com.ioteg.resultmodel.ArrayResultBlock;
import com.ioteg.resultmodel.ResultBlock;

public class CSVArrayResultBlockSerializer implements CSVSerializer<ArrayResultBlock> {
	 
	private static CSVResultBlockSerializer csvResultBlockSerializer;
	
	static {
		csvResultBlockSerializer = new CSVResultBlockSerializer();
	}
	
	
	@Override
	public void serialize(ArrayResultBlock value, CSVGenerator csvGen) throws IOException {
		for(ResultBlock resultBlock : value.getResultBlocks())
			csvResultBlockSerializer.serialize(resultBlock, csvGen);
	}

}
