package com.ioteg.generators.booleanfield;

import java.util.List;

import com.ioteg.Trio;
import com.ioteg.generators.QueryRestrictionGenerationAlgorithm;
import com.ioteg.model.Field;

public class BooleanQueryRestrictionGenerationAlgorithm extends QueryRestrictionGenerationAlgorithm<Boolean>{

	public BooleanQueryRestrictionGenerationAlgorithm(List<Trio<String, String, String>> restrictions) {
		super(restrictions);
	}

	@Override
	public Boolean generate(Field booleanField) {
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
