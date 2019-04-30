package com.ioteg.controllers.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class CustomBehaviourRequest {
	@NotNull(message = "The simulations can not be null.")
	@Positive(message = "The simulations has to be positive.")
	private Integer simulations;

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
