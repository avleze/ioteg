package com.ioteg.controllers.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class BlockResponse {
	private Long id;
	@NotEmpty(message = "The name can not be empty.")
	@NotNull(message = "The name can not be null.")
	private String name;
	private String value;
	private Integer repetition;
	
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
	
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
	 * @return the value
	 */
	public String getValue() {
		return value;
	}
	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}
	/**
	 * @return the repetition
	 */
	public Integer getRepetition() {
		return repetition;
	}
	/**
	 * @param repetition the repetition to set
	 */
	public void setRepetition(Integer repetition) {
		this.repetition = repetition;
	}
	
}
