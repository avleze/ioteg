package com.ioteg.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class represents an attribute inside a complex field in the data model.
 *
 * @author Antonio Vélez Estévez
 * @version $Id: $Id
 */

@Entity
public class Attribute extends OwnedEntity{

	@NotEmpty
	@NotNull
	protected String type;
	protected String value;
	protected Double min;
	protected String step;
	protected String unit;
	protected Double max;
	@Column(name = "PRECISION_PROP")
	protected Integer precision;
	protected Integer length;
	protected String strCase;
	protected String begin;
	protected String end;
	protected String endcharacter;
	protected String format;
	protected Boolean isNumeric;
	protected String generationType;
	
	public Attribute() {

	}

	/**
	 * <p>
	 * Constructor for Attribute.
	 * </p>
	 * 
	 * @param id           a {@link java.lang.Long} object.
	 * @param type         a {@link java.lang.String} object.
	 * @param value        a {@link java.lang.String} object.
	 * @param min          a {@link java.lang.Double} object.
	 * @param step         a {@link java.lang.String} object.
	 * @param unit         a {@link java.lang.String} object.
	 * @param max          a {@link java.lang.Double} object.
	 * @param precision    a {@link java.lang.Integer} object.
	 * @param length       a {@link java.lang.Integer} object.
	 * @param strCase      a {@link java.lang.String} object.
	 * @param begin        a {@link java.lang.String} object.
	 * @param end          a {@link java.lang.String} object.
	 * @param endcharacter a {@link java.lang.String} object.
	 * @param format       a {@link java.lang.String} object.
	 * @param isNumeric    a {@link java.lang.Boolean} object.
	 */
	@JsonCreator
	public Attribute(@JsonProperty("id") Long id, @NotEmpty @NotNull @JsonProperty("type") String type,
			@JsonProperty("value") String value, @JsonProperty("min") Double min, @JsonProperty("step") String step,
			@JsonProperty("unit") String unit, @JsonProperty("max") Double max,
			@JsonProperty("precision") Integer precision, @JsonProperty("length") Integer length,
			@JsonProperty("case") String strCase, @JsonProperty("begin") String begin, @JsonProperty("end") String end,
			@JsonProperty("endcharacter") String endcharacter, @JsonProperty("format") String format,
			@JsonProperty("isnumeric") Boolean isNumeric) {
		if (isNumeric == null)
			isNumeric = false;			
		if (min == null)
			min = 0.0;
		if (max == null)
			max = 10.0;
		if (length == null)
			length = 10;
		
		this.id = id;
		this.type = type;
		this.value = value;
		this.min = min;
		this.step = step;
		this.unit = unit;
		this.max = max;
		this.precision = precision;
		this.length = length;
		this.strCase = strCase;
		this.begin = begin;
		this.end = end;
		this.endcharacter = endcharacter;
		this.format = format;
		this.isNumeric = isNumeric;
	}

	
	
	/**
	 * @param type
	 * @param value
	 * @param min
	 * @param step
	 * @param unit
	 * @param max
	 * @param precision
	 * @param length
	 * @param strCase
	 * @param begin
	 * @param end
	 * @param endcharacter
	 * @param format
	 * @param isNumeric
	 */
	public Attribute(@NotEmpty @NotNull String type, String value, Double min, String step, String unit, Double max,
			Integer precision, Integer length, String strCase, String begin, String end, String endcharacter,
			String format, Boolean isNumeric) {
		this(null, type, value, min, step, unit, max, precision, length, strCase, begin, end, endcharacter, format, isNumeric);
	}

	/**
	 * <p>
	 * Constructor for Attribute.
	 * </p>
	 *
	 * @param type a {@link java.lang.String} object.
	 */
	public Attribute(@NotEmpty @NotNull String type) {
		this.type = type;
	}

	/**
	 * <p>
	 * Getter for the field <code>type</code>.
	 * </p>
	 *
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * <p>
	 * Setter for the field <code>type</code>.
	 * </p>
	 *
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * <p>
	 * Getter for the field <code>value</code>.
	 * </p>
	 *
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * <p>
	 * Setter for the field <code>value</code>.
	 * </p>
	 *
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * <p>
	 * Getter for the field <code>min</code>.
	 * </p>
	 *
	 * @return the min
	 */
	public Double getMin() {
		return min;
	}

	/**
	 * <p>
	 * Setter for the field <code>min</code>.
	 * </p>
	 *
	 * @param min the min to set
	 */
	public void setMin(Double min) {
		this.min = min;
	}

	/**
	 * <p>
	 * Getter for the field <code>step</code>.
	 * </p>
	 *
	 * @return the step
	 */
	public String getStep() {
		return step;
	}

	/**
	 * <p>
	 * Setter for the field <code>step</code>.
	 * </p>
	 *
	 * @param step the step to set
	 */
	public void setStep(String step) {
		this.step = step;
	}

	/**
	 * <p>
	 * Getter for the field <code>unit</code>.
	 * </p>
	 *
	 * @return the unit
	 */
	public String getUnit() {
		return unit;
	}

	/**
	 * <p>
	 * Setter for the field <code>unit</code>.
	 * </p>
	 *
	 * @param unit the unit to set
	 */
	public void setUnit(String unit) {
		this.unit = unit;
	}

	/**
	 * <p>
	 * Getter for the field <code>max</code>.
	 * </p>
	 *
	 * @return the max
	 */
	public Double getMax() {
		return max;
	}

	/**
	 * <p>
	 * Setter for the field <code>max</code>.
	 * </p>
	 *
	 * @param max the max to set
	 */
	public void setMax(Double max) {
		this.max = max;
	}

	/**
	 * <p>
	 * Getter for the field <code>precision</code>.
	 * </p>
	 *
	 * @return the precision
	 */
	public Integer getPrecision() {
		return precision;
	}

	/**
	 * <p>
	 * Setter for the field <code>precision</code>.
	 * </p>
	 *
	 * @param precision the precision to set
	 */
	public void setPrecision(Integer precision) {
		this.precision = precision;
	}

	/**
	 * <p>
	 * Getter for the field <code>length</code>.
	 * </p>
	 *
	 * @return the length
	 */
	public Integer getLength() {
		return length;
	}

	/**
	 * <p>
	 * Setter for the field <code>length</code>.
	 * </p>
	 *
	 * @param length the length to set
	 */
	public void setLength(Integer length) {
		this.length = length;
	}

	/**
	 * <p>
	 * getCase.
	 * </p>
	 *
	 * @return the case
	 */
	public String getCase() {
		return strCase;
	}

	/**
	 * <p>
	 * setCase.
	 * </p>
	 *
	 * @param strCase a {@link java.lang.String} object.
	 */
	public void setCase(String strCase) {
		this.strCase = strCase;
	}

	/**
	 * <p>
	 * Getter for the field <code>begin</code>.
	 * </p>
	 *
	 * @return the begin
	 */
	public String getBegin() {
		return begin;
	}

	/**
	 * <p>
	 * Setter for the field <code>begin</code>.
	 * </p>
	 *
	 * @param begin the begin to set
	 */
	public void setBegin(String begin) {
		this.begin = begin;
	}

	/**
	 * <p>
	 * Getter for the field <code>end</code>.
	 * </p>
	 *
	 * @return the end
	 */
	public String getEnd() {
		return end;
	}

	/**
	 * <p>
	 * Setter for the field <code>end</code>.
	 * </p>
	 *
	 * @param end the end to set
	 */
	public void setEnd(String end) {
		this.end = end;
	}

	/**
	 * <p>
	 * Getter for the field <code>endcharacter</code>.
	 * </p>
	 *
	 * @return the endcharacter
	 */
	public String getEndcharacter() {
		return endcharacter;
	}

	/**
	 * <p>
	 * Setter for the field <code>endcharacter</code>.
	 * </p>
	 *
	 * @param endcharacter the endcharacter to set
	 */
	public void setEndcharacter(String endcharacter) {
		this.endcharacter = endcharacter;
	}

	/**
	 * <p>
	 * Getter for the field <code>format</code>.
	 * </p>
	 *
	 * @return the format
	 */
	public String getFormat() {
		return format;
	}

	/**
	 * <p>
	 * Setter for the field <code>format</code>.
	 * </p>
	 *
	 * @param format the format to set
	 */
	public void setFormat(String format) {
		this.format = format;
	}

	/**
	 * <p>
	 * Getter for the field <code>isNumeric</code>.
	 * </p>
	 *
	 * @return the isNumeric
	 */
	public Boolean getIsNumeric() {
		return isNumeric;
	}

	/**
	 * <p>
	 * Setter for the field <code>isNumeric</code>.
	 * </p>
	 *
	 * @param isNumeric the isNumeric to set
	 */
	public void setIsNumeric(Boolean isNumeric) {
		this.isNumeric = isNumeric;
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
	 * @return the generationType
	 */
	public String getGenerationType() {
		return generationType;
	}

	/**
	 * @param generationType the generationType to set
	 */
	public void setGenerationType(String generationType) {
		this.generationType = generationType;
	}
}
