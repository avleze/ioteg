package com.ioteg.generators.complexfield;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import com.ioteg.exprlang.ExprParser.ExprLangParsingException;
import com.ioteg.generators.Generable;
import com.ioteg.generators.GenerationAlgorithm;
import com.ioteg.generators.GeneratorsFactory;
import com.ioteg.generators.context.GenerationContext;
import com.ioteg.generators.exceptions.NotExistingGeneratorException;
import com.ioteg.model.Attribute;
import com.ioteg.model.Field;
import com.ioteg.resultmodel.ResultComplexField;
import com.ioteg.resultmodel.ResultField;

public class ComplexFieldGeneratorAlgorithm extends GenerationAlgorithm<ResultField> {

	protected List<Generable> fieldGenerators;
	protected Boolean isFormedWithAttributes;
	protected String type;
	
	public ComplexFieldGeneratorAlgorithm(Field field, GenerationContext generationContext) throws NotExistingGeneratorException, ExprLangParsingException, ParseException {
		super(field, generationContext);
		if (!field.getChooseone())
			makeGenerators(field);
	}

	private void makeGenerators(Field field) throws NotExistingGeneratorException, ExprLangParsingException, ParseException {
		this.fieldGenerators = new ArrayList<>();
		this.isFormedWithAttributes = false;
		this.type = field.getType();
		if (!field.getFields().isEmpty())
			for (Field fld : field.getFields())
				this.fieldGenerators.add(GeneratorsFactory.makeGenerator(fld, null, generationContext));
		else {
			this.isFormedWithAttributes = true;
			for (Attribute attr : field.getAttributes())
				this.fieldGenerators.add(GeneratorsFactory.makeGenerator(new Field(null, false, attr), null, generationContext));
		}
	}

	private void makeGeneratorsChooseone() throws NotExistingGeneratorException, ExprLangParsingException, ParseException {
		this.fieldGenerators = new ArrayList<>();
		this.isFormedWithAttributes = false;
		Integer selected = null;
		String dependence = field.getDependence();
		if (!field.getFields().isEmpty()) {
			if(needsToGenerateIndex(dependence))
				selected = r.ints(0, field.getFields().size()).findFirst().getAsInt();
			else
				selected = generationContext.getDependenceIndex(field.getDependence());
			
			this.type = field.getFields().get(selected).getType();
			this.fieldGenerators.add(GeneratorsFactory.makeGenerator(field.getFields().get(selected), null, generationContext));
		}

		else {
			this.isFormedWithAttributes = true;
			if(needsToGenerateIndex(dependence))
				selected = r.ints(0, field.getAttributes().size()).findFirst().getAsInt();
			else
				selected = generationContext.getDependenceIndex(field.getDependence());
			this.type = field.getAttributes().get(selected).getType();
			this.fieldGenerators
					.add(GeneratorsFactory.makeGenerator(new Field(null, false,field.getAttributes().get(selected)), null, generationContext));
		}
		
		if (needsToGenerateIndex(dependence))
			generationContext.putDependenceIndex(field.getName(), selected);

	}
	
	

	
	private boolean needsToGenerateIndex(String dependence) {
		return dependence == null || dependence.equals("true") || dependence.equals("false");
	}
	
	private boolean isDependent(String dependence) {
		return dependence != null && !dependence.equals("false");
	}

	@Override
	public ResultField generate() throws NotExistingGeneratorException, ExprLangParsingException, ParseException {

		List<ResultField> resultFieldsOfComplexField = new ArrayList<>();
		if (field.getChooseone() || isDependent(field.getDependence()))
			makeGeneratorsChooseone();
		for (Generable generator : fieldGenerators)
			resultFieldsOfComplexField.add(generator.generate(1).get(0));
		
		return new ResultComplexField(field.getName(), this.type , field.getQuotes(), resultFieldsOfComplexField,
				isFormedWithAttributes);
	}
}
