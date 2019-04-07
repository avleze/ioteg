package com.ioteg.generation;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import com.ioteg.exprlang.ExprParser.ExprLangParsingException;
import com.ioteg.model.Block;
import com.ioteg.model.EventType;
import com.ioteg.resultmodel.ArrayResultBlock;
import com.ioteg.resultmodel.ResultEvent;

/**
 * <p>EventTypeGenerationAlgorithm class.</p>
 *
 * @author antonio
 * @version $Id: $Id
 */
public class EventTypeGenerationAlgorithm extends AbstractGenerationAlgorithm<ResultEvent> {

	protected EventType eventType;
	protected List<BlockGenerator> blockGenerators;
	protected BlockGenerator blockGeneratorWithRepetition;
	protected Block blockWithRepetition;
	protected GenerationContext generationContext;
	protected Integer repetitions;
	
	/**
	 * <p>Constructor for EventTypeGenerationAlgorithm.</p>
	 *
	 * @param eventType a {@link com.ioteg.model.EventType} object.
	 * @throws java.text.ParseException if any.
	 * @throws com.ioteg.exprlang.ExprParser.ExprLangParsingException if any.
	 * @throws com.ioteg.generation.NotExistingGeneratorException if any.
	 * @param generationContext a {@link com.ioteg.generation.GenerationContext} object.
	 */
	public EventTypeGenerationAlgorithm(EventType eventType, GenerationContext generationContext) throws NotExistingGeneratorException, ExprLangParsingException, ParseException {
		this.generationContext = generationContext;
		this.eventType = eventType;
		this.blockGenerators = new ArrayList<>();
		this.blockGeneratorWithRepetition = null;
		this.blockWithRepetition = null;
		for (Block block : eventType.getBlocks()) 
		{
			BlockGenerator blockGenerator = GeneratorsFactory.makeBlockGenerator(block, generationContext);
			blockGenerators.add(blockGenerator);
			if(block.getRepetition() != null)
			{
				blockWithRepetition = block;
				blockGeneratorWithRepetition = blockGenerator;
				repetitions = block.getRepetition();
			}
		}
	}

	/** {@inheritDoc} */
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
