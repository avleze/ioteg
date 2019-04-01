package com.ioteg.resultmodel;

/**
 * <p>Abstract ResultField class.</p>
 *
 * @author antonio
 * @version $Id: $Id
 */
public abstract class ResultField {
	protected String name;
	protected String type;
	protected Boolean quotes;
	
	/**
	 * <p>Constructor for ResultField.</p>
	 *
	 * @param name a {@link java.lang.String} object.
	 * @param type a {@link java.lang.String} object.
	 * @param quotes a {@link java.lang.Boolean} object.
	 */
	public ResultField(String name, String type, Boolean quotes) {
		super();
		this.name = name;
		this.type = type;
		this.quotes = quotes;
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
	 * <p>Getter for the field <code>type</code>.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getType() {
		return type;
	}

	/**
	 * <p>Setter for the field <code>type</code>.</p>
	 *
	 * @param type a {@link java.lang.String} object.
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * <p>Getter for the field <code>quotes</code>.</p>
	 *
	 * @return a {@link java.lang.Boolean} object.
	 */
	public Boolean getQuotes() {
		return quotes;
	}

	/**
	 * <p>Setter for the field <code>quotes</code>.</p>
	 *
	 * @param quotes a {@link java.lang.Boolean} object.
	 */
	public void setQuotes(Boolean quotes) {
		this.quotes = quotes;
	}

}
