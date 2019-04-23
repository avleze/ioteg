package com.ioteg.controllers.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class FieldRequest {
	@NotEmpty(message = "The field name can't be empty.")
	@NotNull(message = "The field name can't be null.")
	private String name;
	private Boolean quotes = false;
	private Boolean chooseone = false;
	private String dependence = "false";
	private Boolean injectable = false;
	@NotEmpty
	@NotNull
	protected String type;	
	protected String step;
	protected String unit;
	protected Integer precision;
	protected Integer length = 10;
	protected String strCase;
	protected String begin;
	protected String end;
	protected String endcharacter;
	protected String format;
	protected Boolean isNumeric = false;
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the quotes
	 */
	public Boolean getQuotes() {
		return quotes;
	}
	/**
	 * @param quotes the quotes to set
	 */
	public void setQuotes(Boolean quotes) {
		this.quotes = quotes;
	}
	/**
	 * @return the chooseone
	 */
	public Boolean getChooseone() {
		return chooseone;
	}
	/**
	 * @param chooseone the chooseone to set
	 */
	public void setChooseone(Boolean chooseone) {
		this.chooseone = chooseone;
	}
	/**
	 * @return the dependence
	 */
	public String getDependence() {
		return dependence;
	}
	/**
	 * @param dependence the dependence to set
	 */
	public void setDependence(String dependence) {
		this.dependence = dependence;
	}
	/**
	 * @return the injectable
	 */
	public Boolean getInjectable() {
		return injectable;
	}
	/**
	 * @param injectable the injectable to set
	 */
	public void setInjectable(Boolean injectable) {
		this.injectable = injectable;
	}
	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * @return the step
	 */
	public String getStep() {
		return step;
	}
	/**
	 * @param step the step to set
	 */
	public void setStep(String step) {
		this.step = step;
	}
	/**
	 * @return the unit
	 */
	public String getUnit() {
		return unit;
	}
	/**
	 * @param unit the unit to set
	 */
	public void setUnit(String unit) {
		this.unit = unit;
	}
	/**
	 * @return the precision
	 */
	public Integer getPrecision() {
		return precision;
	}
	/**
	 * @param precision the precision to set
	 */
	public void setPrecision(Integer precision) {
		this.precision = precision;
	}
	/**
	 * @return the length
	 */
	public Integer getLength() {
		return length;
	}
	/**
	 * @param length the length to set
	 */
	public void setLength(Integer length) {
		this.length = length;
	}
	/**
	 * @return the strCase
	 */
	public String getStrCase() {
		return strCase;
	}
	/**
	 * @param strCase the strCase to set
	 */
	public void setStrCase(String strCase) {
		this.strCase = strCase;
	}
	/**
	 * @return the begin
	 */
	public String getBegin() {
		return begin;
	}
	/**
	 * @param begin the begin to set
	 */
	public void setBegin(String begin) {
		this.begin = begin;
	}
	/**
	 * @return the end
	 */
	public String getEnd() {
		return end;
	}
	/**
	 * @param end the end to set
	 */
	public void setEnd(String end) {
		this.end = end;
	}
	/**
	 * @return the endcharacter
	 */
	public String getEndcharacter() {
		return endcharacter;
	}
	/**
	 * @param endcharacter the endcharacter to set
	 */
	public void setEndcharacter(String endcharacter) {
		this.endcharacter = endcharacter;
	}
	/**
	 * @return the format
	 */
	public String getFormat() {
		return format;
	}
	/**
	 * @param format the format to set
	 */
	public void setFormat(String format) {
		this.format = format;
	}
	/**
	 * @return the isNumeric
	 */
	public Boolean getIsNumeric() {
		return isNumeric;
	}
	/**
	 * @param isNumeric the isNumeric to set
	 */
	public void setIsNumeric(Boolean isNumeric) {
		this.isNumeric = isNumeric;
	}
}
