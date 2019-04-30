package com.ioteg.controllers.dto;

import java.util.List;

public class OptionalFieldsResponse {
	
	private Long id;
	private Boolean mandatory;
	private List<String> fields;
	
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
	 * @return the mandatory
	 */
	public Boolean getMandatory() {
		return mandatory;
	}
	/**
	 * @param mandatory the mandatory to set
	 */
	public void setMandatory(Boolean mandatory) {
		this.mandatory = mandatory;
	}
	/**
	 * @return the fields
	 */
	public List<String> getFields() {
		return fields;
	}
	/**
	 * @param fields the fields to set
	 */
	public void setFields(List<String> fields) {
		this.fields = fields;
	}
}
