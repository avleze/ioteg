package com.ioteg.generators.booleanfield;

import java.util.List;

import com.ioteg.eplutils.Trio;
import com.ioteg.generators.QueryRestrictionGenerationAlgorithm;
import com.ioteg.generators.context.GenerationContext;
import com.ioteg.model.Field;

/**
 * <p>BooleanQueryRestrictionGenerationAlgorithm class.</p>
 *
 * @author antonio
 * @version $Id: $Id
 */
public class BooleanQueryRestrictionGenerationAlgorithm extends QueryRestrictionGenerationAlgorithm<Boolean>{

	/**
	 * <p>Constructor for BooleanQueryRestrictionGenerationAlgorithm.</p>
	 *
	 * @param field a {@link com.ioteg.model.Field} object.
	 * @param generationContext a {@link com.ioteg.generators.context.GenerationContext} object.
	 * @param restrictions a {@link java.util.List} object.
	 */
	public BooleanQueryRestrictionGenerationAlgorithm(Field field, GenerationContext generationContext, List<Trio<String, String, String>> restrictions) {
		super(field, generationContext, restrictions);
	}

	/** {@inheritDoc} */
	@Override
	public Boolean generate() {
		Trio<String, String, String> fieldRestrictionInformation = restrictions.get(0);
		
		String operator = fieldRestrictionInformation.getSecond();
		Boolean value = getBooleanValue(fieldRestrictionInformation.getThird());
	
		Boolean result = null;
		
		if (operator.equals("=")) 
			result = value;
		else if (operator.equals("!="))
			result = !value;
		else
			result = null;

		return result;
	}
	
	private Boolean getBooleanValue(String value) {
		return value.equalsIgnoreCase("1") || value.equalsIgnoreCase("true");
	}
}
