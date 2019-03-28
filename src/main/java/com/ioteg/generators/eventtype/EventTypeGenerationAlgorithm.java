package com.ioteg.generators.eventtype;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import com.ioteg.exprlang.ExprParser.ExprLangParsingException;
import com.ioteg.generators.AbstractGenerationAlgorithm;
import com.ioteg.generators.GeneratorsFactory;
import com.ioteg.generators.block.BlockGenerator;
import com.ioteg.generators.context.GenerationContext;
import com.ioteg.generators.exceptions.NotExistingGeneratorException;
import com.ioteg.model.Block;
import com.ioteg.model.EventType;
import com.ioteg.resultmodel.ArrayResultBlock;
import com.ioteg.resultmodel.ResultEvent;

public class EventTypeGenerationAlgorithm extends AbstractGenerationAlgorithm<ResultEvent> {

	protected EventType eventType;
	protected List<BlockGenerator> blockGenerators;
	protected BlockGenerator blockGeneratorWithRepetition;
	protected GenerationContext generationContext;
	protected Integer repetitions;
	
	/**
	 * @param eventType
	 * @throws ParseException 
	 * @throws ExprLangParsingException 
	 * @throws NotExistingGeneratorException 
	 */
	public EventTypeGenerationAlgorithm(EventType eventType, GenerationContext generationContext) throws NotExistingGeneratorException, ExprLangParsingException, ParseException {
		this.generationContext = generationContext;
		this.eventType = eventType;
		this.blockGenerators = new ArrayList<>();
		this.blockGeneratorWithRepetition = null;
		for (Block block : eventType.getBlocks()) 
		{
			BlockGenerator blockGenerator = GeneratorsFactory.makeBlockGenerator(block, generationContext);
			blockGenerators.add(blockGenerator);
			if(block.getRepetition() != null)
			{
				blockGeneratorWithRepetition = blockGenerator;
				repetitions = block.getRepetition();
			}
		}
	}

	@Override
	public ResultEvent generate() throws NotExistingGeneratorException, ExprLangParsingException, ParseException {

		ResultEvent resultEvent = new ResultEvent(eventType.getName(), new ArrayList<>());
		resultEvent.setModel(eventType);
		for (BlockGenerator blockGenerator : this.blockGenerators) {

			ArrayResultBlock resultBlocks = new ArrayResultBlock(new ArrayList<>(), blockGenerator == blockGeneratorWithRepetition);
			
			resultBlocks.getResultBlocks()
					.addAll(blockGenerator.generate(blockGeneratorWithRepetition == blockGenerator ? repetitions : 1));
			resultEvent.getArrayResultBlocks().add(resultBlocks);
		}
		
		return resultEvent;
	}

}
