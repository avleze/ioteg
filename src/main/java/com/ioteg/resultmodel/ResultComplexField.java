package com.ioteg.resultmodel;

import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ioteg.resultmodel.jsonserializers.ResultComplexFieldSerializer;

/**
 * <p>ResultComplexField class.</p>
 *
 * @author antonio
 * @version $Id: $Id
 */
@JsonSerialize(using = ResultComplexFieldSerializer.class)
public class ResultComplexField extends ResultField {
	private List<ResultField> value;
	private Boolean isAComplexFieldFormedWithAttributes;

	/**
	 * <p>Constructor for ResultComplexField.</p>
	 *
	 * @param name a {@link java.lang.String} object.
	 * @param type a {@link java.lang.String} object.
	 * @param quotes a {@link java.lang.Boolean} object.
	 * @param value a {@link java.util.List} object.
	 * @param isAComplexFieldFormedWithAttributes a {@link java.lang.Boolean} object.
	 */
	public ResultComplexField(String name, String type, Boolean quotes, List<ResultField> value,
			Boolean isAComplexFieldFormedWithAttributes) {
		super(name, type, quotes);
		this.value = value;
		this.isAComplexFieldFormedWithAttributes = isAComplexFieldFormedWithAttributes;
	}

	/**
	 * <p>Getter for the field <code>value</code>.</p>
	 *
	 * @return a {@link java.util.List} object.
	 */
	public List<ResultField> getValue() {
		return value;
	}

	/**
	 * <p>Setter for the field <code>value</code>.</p>
	 *
	 * @param value a {@link java.util.List} object.
	 */
	public void setValue(List<ResultField> value) {
		this.value = value;
	}

	/**
	 * <p>Getter for the field <code>isAComplexFieldFormedWithAttributes</code>.</p>
	 *
	 * @return a {@link java.lang.Boolean} object.
	 */
	public Boolean getIsAComplexFieldFormedWithAttributes() {
		return isAComplexFieldFormedWithAttributes;
	}

	/**
	 * <p>Setter for the field <code>isAComplexFieldFormedWithAttributes</code>.</p>
	 *
	 * @param isAComplexFieldFormedWithAttributes a {@link java.lang.Boolean} object.
	 */
	public void setIsAComplexFieldFormedWithAttributes(Boolean isAComplexFieldFormedWithAttributes) {
		this.isAComplexFieldFormedWithAttributes = isAComplexFieldFormedWithAttributes;
	}
}
