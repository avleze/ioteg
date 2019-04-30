package com.ioteg.serializers.csv;

import java.util.ArrayList;
import java.util.List;

import com.ioteg.model.Block;
import com.ioteg.model.Field;
import com.ioteg.model.OptionalFields;

/**
 * <p>CSVHeaderBuilder class.</p>
 *
 * @author antonio
 * @version $Id: $Id
 */
public class CSVHeaderBuilder {

	private CSVHeaderBuilder() {
	}

	/**
	 * <p>getBlockCSVHeader.</p>
	 *
	 * @param block a {@link com.ioteg.model.Block} object.
	 * @return a {@link java.util.List} object.
	 */
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

	/**
	 * <p>getComplexFieldCSVHeader.</p>
	 *
	 * @param complexField a {@link com.ioteg.model.Field} object.
	 * @return a {@link java.util.List} object.
	 */
	public static List<String> getComplexFieldCSVHeader(Field complexField) {
		List<String> result = new ArrayList<>();
		for (Field field : complexField.getFields())
			//if (field.getFields() == null || field.getFields().isEmpty())
				result.add(complexField.getName() + "." + field.getName());
			/*else
				result.addAll(getComplexFieldCSVHeader(field));*/
		return result;
	}
}
