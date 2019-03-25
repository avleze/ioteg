package com.ioteg;

import java.util.ArrayList;
import com.ioteg.exprlang.ExprParser.ExprLangParsingException;
import com.ioteg.generators.GeneratorsFactory;
import com.ioteg.generators.block.BlockGenerator;
import com.ioteg.generators.exceptions.NotExistingGeneratorException;
import com.ioteg.model.Block;
import com.ioteg.model.EventType;
import com.ioteg.resultmodel.ArrayResultBlock;
import com.ioteg.resultmodel.ResultEvent;

public class EventGenerator {
	
	private EventGenerator() {
		
	}

	public static ResultEvent generateEvent(EventType event)
			throws NotExistingGeneratorException, ExprLangParsingException {

		ResultEvent resultEvent = new ResultEvent(event.getName(), new ArrayList<>());
		resultEvent.setModel(event);
		for (Block block : event.getBlocks()) {

			ArrayResultBlock resultBlocks = new ArrayResultBlock(new ArrayList<>(), block.getRepetition() != null);
			BlockGenerator bGenerator = GeneratorsFactory.makeBlockGenerator(block);
			resultBlocks.getResultBlocks()
					.addAll(bGenerator.generate(block.getRepetition() == null ? 1 : block.getRepetition()));
			resultEvent.getArrayResultBlocks().add(resultBlocks);
		}

		return resultEvent;
	}

}
