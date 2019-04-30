package com.ioteg.controllers.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class CustomBehaviourResponse {
	private Long id;
	
	@NotNull(message = "The simulations can not be null.")
	@Positive(message = "The simulations has to be positive.")
	private Integer simulations;
	
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
	 * @return the simulations
	 */
	public Integer getSimulations() {
		return simulations;
	}

	/**
	 * @param simulations the simulations to set
	 */
	public void setSimulations(Integer simulations) {
		this.simulations = simulations;
	}
	
}
