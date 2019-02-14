package com.ioteg.generators.block;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.ioteg.generators.AbstractGenerationAlgorithm;
import com.ioteg.generators.Generable;
import com.ioteg.generators.GeneratorsFactory;
import com.ioteg.model.Block;
import com.ioteg.model.Field;
import com.ioteg.model.OptionalFields;
import com.ioteg.resultmodel.ResultBlock;
import com.ioteg.resultmodel.ResultField;

public class BlockGenerationAlgorithm extends AbstractGenerationAlgorithm<ResultBlock> {

	protected List<Generable> generators;
	protected List<Generable> optionalGenerators;
	protected Block block;
	
	
	public BlockGenerationAlgorithm(Block block) {
		super();
		this.block = block;
		makeGenerators(block);
	}

	private void makeGenerators(Block block) {
		this.generators = new ArrayList<>();
		
		for (Field field : block.getFields()) 
			generators.add(GeneratorsFactory.makeGenerator(field, block.getRepetition()));
	}
	
	private void makeOptionalGenerators() {
		this.optionalGenerators = new ArrayList<>();
		for (OptionalFields optionalFields : block.getOptionalFields())
			for(Field field : fieldsSelected(optionalFields))
				optionalGenerators.add(GeneratorsFactory.makeGenerator(field, block.getRepetition()));
	}
	
	private List<Field> fieldsSelected(OptionalFields optionalFields) {
		List<Field> fields = new ArrayList<>();
		
		Integer numOfFields = optionalFields.getFields().size();
		Boolean atLeastOne = optionalFields.getMandatory().equalsIgnoreCase("true") || optionalFields.getMandatory().equalsIgnoreCase("1");
		
		Integer howManyFields = r.ints(0, numOfFields).findFirst().getAsInt();
		
		if(atLeastOne && howManyFields == 0)
			howManyFields = 1;
		
		List<Integer> selectedIndexes = new ArrayList<>();
	
		if(numOfFields != 1)
			selectedIndexes = r.ints(0, numOfFields - 1).limit(howManyFields).boxed().collect(Collectors.toList());
		else if(atLeastOne)
			selectedIndexes.add(0);
		else if(r.nextBoolean())
			selectedIndexes.add(0);

		for(Integer index : selectedIndexes)
			fields.add(optionalFields.getFields().get(index));
		
		return fields;
	}
		

	@Override
	public ResultBlock generate() {
		ResultBlock resultBlock = new ResultBlock(block.getName(), new ArrayList<ResultField>());
		
		makeOptionalGenerators();
		
		for (Generable generator : this.generators) 
			resultBlock.getResultFields().add(generator.generate(1).get(0));

		for (Generable generator : this.optionalGenerators) 
			resultBlock.getResultFields().add(generator.generate(1).get(0));
		
		return resultBlock;
	}
	
	
}
