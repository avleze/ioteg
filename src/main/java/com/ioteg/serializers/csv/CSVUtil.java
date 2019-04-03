package com.ioteg.serializers.csv;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import com.ioteg.model.Block;
import com.ioteg.model.EventType;
import com.ioteg.resultmodel.ResultEvent;

/**
 * <p>CSVUtil class.</p>
 *
 * @author antonio
 * @version $Id: $Id
 */
public class CSVUtil {

	private static CSVSerializer<ResultEvent> csvResultEventSerializer;
	
	static {
		csvResultEventSerializer = new CSVResultEventSerializer();
	}
	
	private CSVUtil() {
		
	}
	
	/**
	 * <p>serializeResultEvent.</p>
	 *
	 * @param model a {@link com.ioteg.model.EventType} object.
	 * @param resultEvent a {@link com.ioteg.resultmodel.ResultEvent} object.
	 * @return a {@link java.lang.String} object.
	 * @throws java.io.IOException if any.
	 */
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
