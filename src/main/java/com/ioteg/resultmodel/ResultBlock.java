package com.ioteg.resultmodel;

import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ioteg.serializers.json.ResultBlockSerializer;

/**
 * <p>ResultBlock class.</p>
 *
 * @author antonio
 * @version $Id: $Id
 */
@JsonSerialize(using = ResultBlockSerializer.class)
public class ResultBlock {
	private String name;
	private List<ResultField> resultFields;

	/**
	 * <p>Constructor for ResultBlock.</p>
	 *
	 * @param name a {@link java.lang.String} object.
	 * @param resultFields a {@link java.util.List} object.
	 */
	public ResultBlock(String name, List<ResultField> resultFields) {
		super();
		this.name = name;
		this.resultFields = resultFields;
	}

	/**
	 * <p>Getter for the field <code>name</code>.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getName() {
		return name;
	}

	/**
	 * <p>Setter for the field <code>name</code>.</p>
	 *
	 * @param name a {@link java.lang.String} object.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * <p>Getter for the field <code>resultFields</code>.</p>
	 *
	 * @return a {@link java.util.List} object.
	 */
	public List<ResultField> getResultFields() {
		return resultFields;
	}

	/**
	 * <p>Setter for the field <code>resultFields</code>.</p>
	 *
	 * @param resultFields a {@link java.util.List} object.
	 */
	public void setResultFields(List<ResultField> resultFields) {
		this.resultFields = resultFields;
	}

}
