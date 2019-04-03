package com.ioteg.builders;

import org.jdom2.Element;

import com.ioteg.model.Attribute;

/**
 * This class is a builder which allows to build an Attribute from its
 * definition in a XML Element.
 *
 * @author Antonio Vélez Estévez
 * @version $Id: $Id
 */
public class AttributeBuilder {

	/**
	 * <p>build.</p>
	 *
	 * @param attributeElement a {@link org.jdom2.Element} object.
	 * @return a {@link com.ioteg.model.Attribute} object.
	 */
	public Attribute build(Element attributeElement) {

		String type = attributeElement.getAttributeValue("type");
		String value = attributeElement.getAttributeValue("value");
		String min = attributeElement.getAttributeValue("min");
		String step = attributeElement.getAttributeValue("step");
		String unit = attributeElement.getAttributeValue("unit");
		String max = attributeElement.getAttributeValue("max");
		String precision = attributeElement.getAttributeValue("precision");
		String length = attributeElement.getAttributeValue("length");
		String strCase = attributeElement.getAttributeValue("case");
		String begin = attributeElement.getAttributeValue("begin");
		String end = attributeElement.getAttributeValue("end");
		String endcharacter = attributeElement.getAttributeValue("endcharacter");
		String format = attributeElement.getAttributeValue("format");
		String isNumeric = attributeElement.getAttributeValue("isnumeric");
		

		Double minValue = null;
		if (min != null)
			minValue = Double.valueOf(min);
		
		Double maxValue = null;
		if (max != null)
			maxValue = Double.valueOf(max);

		Integer precisionValue = null;
		if (precision != null)
			precisionValue = Integer.valueOf(precision);

		Integer lengthValue = null;
		if (length != null)
			lengthValue = Integer.valueOf(length);
		
		return new Attribute(null, type, value, minValue, step, unit, maxValue, precisionValue, lengthValue, strCase, begin, end, endcharacter, format, Boolean.valueOf(isNumeric));
	}

}
