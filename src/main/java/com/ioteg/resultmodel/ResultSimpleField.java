package com.ioteg.resultmodel;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ioteg.resultmodel.jsonserializers.ResultSimpleFieldSerializer;

/**
 * <p>ResultSimpleField class.</p>
 *
 * @author antonio
 * @version $Id: $Id
 */
@JsonSerialize(using = ResultSimpleFieldSerializer.class)
public class ResultSimpleField extends ResultField {
	protected String value;

	/**
	 * <p>Constructor for ResultSimpleField.</p>
	 *
	 * @param name a {@link java.lang.String} object.
	 * @param type a {@link java.lang.String} object.
	 * @param quotes a {@link java.lang.Boolean} object.
	 * @param value a {@link java.lang.String} object.
	 */
	public ResultSimpleField(String name, String type, Boolean quotes, String value) {
		super(name, type, quotes);
		this.value = value;
	}

	/**
	 * <p>Getter for the field <code>value</code>.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getValue() {
		return value;
	}

	/**
	 * <p>Setter for the field <code>value</code>.</p>
	 *
	 * @param value a {@link java.lang.String} object.
	 */
	public void setValue(String value) {
		this.value = value;
	}

}
