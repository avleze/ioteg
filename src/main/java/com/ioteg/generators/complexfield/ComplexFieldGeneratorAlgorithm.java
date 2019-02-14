package com.ioteg.generators.complexfield;

import java.util.ArrayList;
import java.util.List;

import com.ioteg.generators.Generable;
import com.ioteg.generators.GenerationAlgorithm;
import com.ioteg.generators.GeneratorsFactory;
import com.ioteg.model.Attribute;
import com.ioteg.model.Field;
import com.ioteg.resultmodel.ResultComplexField;
import com.ioteg.resultmodel.ResultField;

public class ComplexFieldGeneratorAlgorithm extends GenerationAlgorithm<ResultField> {

	protected List<Generable> fieldGenerators;
	protected Boolean isFormedWithAttributes;

	public ComplexFieldGeneratorAlgorithm(Field field) {
		super(field);
		if (!field.getChooseone())
			makeGenerators(field);
	}

	private void makeGenerators(Field field) {
		this.fieldGenerators = new ArrayList<>();
		this.isFormedWithAttributes = false;
		if (!field.getFields().isEmpty())
			for (Field fld : field.getFields())
				this.fieldGenerators.add(GeneratorsFactory.makeGenerator(fld, null));
		else {
			this.isFormedWithAttributes = true;
			for (Attribute attr : field.getAttributes())
				this.fieldGenerators.add(GeneratorsFactory.makeGenerator(new Field(attr), null));
		}
	}

	private void makeGeneratorsChooseone() {
		this.fieldGenerators = new ArrayList<>();
		this.isFormedWithAttributes = false;

		if (!field.getFields().isEmpty()) {
			Integer selected = r.ints(0, field.getFields().size()).findFirst().getAsInt();
			this.fieldGenerators.add(GeneratorsFactory.makeGenerator(field.getFields().get(selected), null));
		}

		else {
			this.isFormedWithAttributes = true;
			Integer selected = r.ints(0, field.getAttributes().size()).findFirst().getAsInt();
			this.fieldGenerators
					.add(GeneratorsFactory.makeGenerator(new Field(field.getAttributes().get(selected)), null));
		}
	}

	@Override
	public ResultField generate() {

		List<ResultField> resultFieldsOfComplexField = new ArrayList<>();
		if (field.getChooseone())
			makeGeneratorsChooseone();
		for (Generable generator : fieldGenerators)
			resultFieldsOfComplexField.add(generator.generate(1).get(0));

		return new ResultComplexField(field.getName(), field.getType(), field.getQuotes(), resultFieldsOfComplexField,
				isFormedWithAttributes);
	}
}
