package com.ioteg.generation;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import com.ioteg.exprlang.ExprParser.ExprLangParsingException;
import com.ioteg.model.Attribute;
import com.ioteg.model.Field;
import com.ioteg.resultmodel.ResultComplexField;
import com.ioteg.resultmodel.ResultField;

/**
 * <p>ComplexFieldGeneratorAlgorithm class.</p>
 *
 * @author antonio
 * @version $Id: $Id
 */
public class ComplexFieldGenerationAlgorithm extends GenerationAlgorithm<ResultField> {

	protected List<Generable> fieldGenerators;
	protected Boolean isFormedWithAttributes;
	protected String type;

	/**
	 * <p>Constructor for ComplexFieldGeneratorAlgorithm.</p>
	 *
	 * @param field a {@link com.ioteg.model.Field} object.
	 * @param generationContext a {@link com.ioteg.generation.GenerationContext} object.
	 * @throws com.ioteg.generation.NotExistingGeneratorException if any.
	 * @throws com.ioteg.exprlang.ExprParser.ExprLangParsingException if any.
	 * @throws java.text.ParseException if any.
	 */
	public ComplexFieldGenerationAlgorithm(Field field, GenerationContext generationContext)
			throws NotExistingGeneratorException, ExprLangParsingException, ParseException {
		super(field, generationContext);
		makeGenerators(field);
	}

	private void makeGenerators(Field field)
			throws NotExistingGeneratorException, ExprLangParsingException, ParseException {
		this.fieldGenerators = new ArrayList<>();
		this.isFormedWithAttributes = false;
		this.type = field.getType();
		if (!field.getFields().isEmpty())
			for (Field fld : field.getFields())
				this.fieldGenerators.add(GeneratorsFactory.makeGenerator(fld, null, generationContext));
		else {
			this.isFormedWithAttributes = true;
			for (Attribute attr : field.getAttributes())
				this.fieldGenerators
						.add(GeneratorsFactory.makeGenerator(new Field(null, false, attr), null, generationContext));
		}
	}

	private Integer selectGeneratorsChooseone()
			throws NotExistingGeneratorException, ExprLangParsingException, ParseException {

		Integer selected = null;
		String dependence = field.getDependence();
		if (!isFormedWithAttributes) {
			if (needsToGenerateIndex(dependence))
				selected = r.ints(0, field.getFields().size()).findFirst().getAsInt();
			else
				selected = generationContext.getDependenceIndex(field.getDependence());

			this.type = field.getFields().get(selected).getType();
			this.fieldGenerators
					.add(GeneratorsFactory.makeGenerator(field.getFields().get(selected), null, generationContext));
		}

		else {
			if (needsToGenerateIndex(dependence))
				selected = r.ints(0, field.getAttributes().size()).findFirst().getAsInt();
			else
				selected = generationContext.getDependenceIndex(field.getDependence());
			this.type = field.getAttributes().get(selected).getType();
			this.fieldGenerators.add(GeneratorsFactory.makeGenerator(
					new Field(null, false, field.getAttributes().get(selected)), null, generationContext));
		}

		if (needsToGenerateIndex(dependence))
			generationContext.putDependenceIndex(field.getName(), selected);

		return selected;
	}

	private boolean needsToGenerateIndex(String dependence) {
		return dependence == null || dependence.equals("true") || dependence.equals("false");
	}

	private boolean isDependent(String dependence) {
		return dependence != null && !dependence.equals("false");
	}

	/** {@inheritDoc} */
	@Override
	public ResultField generate() throws NotExistingGeneratorException, ExprLangParsingException, ParseException {

		List<ResultField> resultFieldsOfComplexField = new ArrayList<>();

		if (field.getChooseone() || isDependent(field.getDependence())) {
			Integer selected = selectGeneratorsChooseone();
			resultFieldsOfComplexField.add(fieldGenerators.get(selected).generate(1).get(0));
		} else {
			for (Generable generator : fieldGenerators)
				resultFieldsOfComplexField.add(generator.generate(1).get(0));
		}

		return new ResultComplexField(field.getName(), this.type, field.getQuotes(), resultFieldsOfComplexField,
				isFormedWithAttributes);
	}
}
