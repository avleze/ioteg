package com.ioteg.resultmodel.csvserializers;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import com.ioteg.model.Block;
import com.ioteg.model.EventType;
import com.ioteg.resultmodel.ResultEvent;

public class CSVUtil {

	private static CSVSerializer<ResultEvent> csvResultEventSerializer;
	
	static {
		csvResultEventSerializer = new CSVResultEventSerializer();
	}
	
	public static String serializeResultEvent(EventType model, ResultEvent resultEvent) throws IOException {
		OutputStream out = new ByteArrayOutputStream();
		
		Block blockWithRepTag = null;
		for(Block block : model.getBlocks()) {
			if(block.getRepetition() != null)
			{
				blockWithRepTag = block;
				break;
			}
		}
		
		CSVGenerator csvGenerator = new CSVGenerator(out, CSVHeaderBuilder.getBlockCSVHeader(blockWithRepTag));
		csvResultEventSerializer.serialize(resultEvent, csvGenerator);
		csvGenerator.close();
		return out.toString();
	}
}
