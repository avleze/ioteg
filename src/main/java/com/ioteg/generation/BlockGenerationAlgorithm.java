package com.ioteg.generation;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.ioteg.exprlang.ExprParser.ExprLangParsingException;
import com.ioteg.model.Block;
import com.ioteg.model.Field;
import com.ioteg.model.InjectedField;
import com.ioteg.model.OptionalFields;
import com.ioteg.resultmodel.ResultBlock;
import com.ioteg.resultmodel.ResultField;

/**
 * <p>BlockGenerationAlgorithm class.</p>
 *
 * @author antonio
 * @version $Id: $Id
 */
public class BlockGenerationAlgorithm extends AbstractGenerationAlgorithm<ResultBlock> {

	protected List<Generable> generators;
	protected List<List<Generable>> optionalGenerators;
	protected Block block;
	protected GenerationContext generationContext;
	
	
	/**
	 * <p>Constructor for BlockGenerationAlgorithm.</p>
	 *
	 * @param block a {@link com.ioteg.model.Block} object.
	 * @param generationContext a {@link com.ioteg.generation.GenerationContext} object.
	 * @throws com.ioteg.generation.NotExistingGeneratorException if any.
	 * @throws com.ioteg.exprlang.ExprParser.ExprLangParsingException if any.
	 * @throws java.text.ParseException if any.
	 */
	public BlockGenerationAlgorithm(Block block, GenerationContext generationContext) throws NotExistingGeneratorException, ExprLangParsingException, ParseException {
		super();
		this.block = block;
		this.generationContext = generationContext;
		makeGenerators(block);
		makeOptionalGenerators();
	}

	private void makeGenerators(Block block) throws NotExistingGeneratorException, ExprLangParsingException, ParseException {
		this.generators = new ArrayList<>();
		
		for (Field field : block.getFields()) 
			generators.add(GeneratorsFactory.makeGenerator(field, block.getRepetition(), generationContext));
	}
	
	private void makeOptionalGenerators() throws NotExistingGeneratorException, ExprLangParsingException, ParseException {
		this.optionalGenerators = new ArrayList<>();
		int index = 0;
		for (OptionalFields optionalFields : block.getOptionalFields())
		{
			optionalGenerators.add(new ArrayList<>());
			for(Field field : optionalFields.getFields())
				optionalGenerators.get(index).add(GeneratorsFactory.makeGenerator(field, block.getRepetition(), generationContext));
			++index;
		}
			
	}
	
	private List<Integer> selectGenerators(OptionalFields optionalFields) {
		
		Integer numOfFields = optionalFields.getFields().size();
		Boolean atLeastOne = false;
		if(optionalFields.getMandatory() != null)
			atLeastOne = optionalFields.getMandatory();
		Integer lowerBound = 0;
		if(atLeastOne)
			lowerBound = 1;
		
		Integer howManyFields = r.ints(lowerBound, numOfFields + 1).findFirst().getAsInt();
		
		List<Integer> selectedIndexes = new ArrayList<>();
	
		if(numOfFields != 1)
			selectedIndexes = r.ints(0, numOfFields).limit(howManyFields).boxed().collect(Collectors.toList());
		else if(atLeastOne)
			selectedIndexes.add(0);
		else if(r.nextBoolean())
			selectedIndexes.add(0);
		
		return selectedIndexes;
	}
		

	/** {@inheritDoc} */
	@Override
	public ResultBlock generate() throws NotExistingGeneratorException, ExprLangParsingException, ParseException {
		ResultBlock resultBlock = new ResultBlock(block.getName(), new ArrayList<ResultField>());
		List<Generable> generatorsSelected = new ArrayList<>();
		int optionalFieldIndex = 0;
		for(OptionalFields optionalFields: this.block.getOptionalFields())
		{
			for(Integer index : selectGenerators(optionalFields))
				generatorsSelected.add(optionalGenerators.get(optionalFieldIndex).get(index));
			++optionalFieldIndex;
		}
			
		
		for (Generable generator : this.generators) 
			resultBlock.getResultFields().add(generator.generate(1).get(0));

		for (Generable generator : generatorsSelected) 
			resultBlock.getResultFields().add(generator.generate(1).get(0));
		
		for (InjectedField injectedField : block.getInjectedFields())
		{
			ResultField resultField = generationContext.getInjectableResultField(injectedField.getName());
			if(resultField != null)
				resultBlock.getResultFields().add(resultField);
		}

		return resultBlock;
	}
	
	
}
