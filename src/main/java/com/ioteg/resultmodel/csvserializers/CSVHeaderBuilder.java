package com.ioteg.resultmodel.csvserializers;

import java.util.ArrayList;
import java.util.List;

import com.ioteg.model.Block;
import com.ioteg.model.Field;
import com.ioteg.model.OptionalFields;

public class CSVHeaderBuilder {

	private CSVHeaderBuilder() {
	}

	public static List<String> getBlockCSVHeader(Block block) {
		List<String> result = new ArrayList<>();
		for (Field field : block.getFields())
			if (field.getFields() == null || field.getFields().isEmpty())
				result.add(field.getName());
			else
				result.addAll(getComplexFieldCSVHeader(field));

		for (OptionalFields optionalFields : block.getOptionalFields())
			for (Field field : optionalFields.getFields())
				if (field.getFields() == null || field.getFields().isEmpty())
					result.add(field.getName());
				else
					result.addAll(getComplexFieldCSVHeader(field));

		return result;
	}

	public static List<String> getComplexFieldCSVHeader(Field complexField) {
		List<String> result = new ArrayList<>();
		for (Field field : complexField.getFields())
			if (field.getFields() == null || field.getFields().isEmpty())
				result.add(complexField.getName() + "." + field.getName());
			else
				result.addAll(getComplexFieldCSVHeader(field));
		return result;
	}
}
