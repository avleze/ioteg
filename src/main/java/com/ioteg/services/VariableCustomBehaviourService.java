package com.ioteg.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ioteg.model.CustomBehaviour;
import com.ioteg.model.VariableCustomBehaviour;
import com.ioteg.repositories.VariableCustomBehaviourRepository;

@Service
public class VariableCustomBehaviourService {

	@Autowired
	private CustomBehaviourService customBehaviourService;
	
	@Autowired
	private VariableCustomBehaviourRepository variableCustomBehaviourRepository;
	
	
	public VariableCustomBehaviour createVariableCustomBehaviourRepository(Long customBehaviourId,
			VariableCustomBehaviour variableCustomBehaviour) throws ResourceNotFoundException {
	
		VariableCustomBehaviour storedVariableCustomBehaviour = variableCustomBehaviourRepository.save(variableCustomBehaviour);
		
		CustomBehaviour customBehaviour = customBehaviourService.loadById(customBehaviourId);
		customBehaviour.getVariables().add(storedVariableCustomBehaviour);
		customBehaviourService.save(customBehaviour);

		return storedVariableCustomBehaviour;
	}
	
	public VariableCustomBehaviour modifyRuleCustomBehaviour(Long variableCustomBehaviourId,
			VariableCustomBehaviour variableCustomBehaviour) throws ResourceNotFoundException {
	
		VariableCustomBehaviour storedVariableCustomBehaviour =  this.loadById(variableCustomBehaviourId);
		
		storedVariableCustomBehaviour.setMax(variableCustomBehaviour.getMax());
		storedVariableCustomBehaviour.setMin(variableCustomBehaviour.getMin());
		storedVariableCustomBehaviour.setName(variableCustomBehaviour.getName());
		storedVariableCustomBehaviour.setValue(variableCustomBehaviour.getValue());

		return variableCustomBehaviourRepository.save(storedVariableCustomBehaviour);
	}
	
	public void removeRuleCustomBehaviour(Long variableCustomBehaviourId) {
		variableCustomBehaviourRepository.deleteById(variableCustomBehaviourId);
	}

	public VariableCustomBehaviour loadById(Long variableCustomBehaviourId) throws ResourceNotFoundException {
		return variableCustomBehaviourRepository.findById(variableCustomBehaviourId)
				.orElseThrow(() -> new ResourceNotFoundException("VariableCustomBehaviour " + variableCustomBehaviourId, "VariableCustomBehaviour not found."));
	}

	public VariableCustomBehaviour save(VariableCustomBehaviour variableCustomBehaviour) {
		return variableCustomBehaviourRepository.save(variableCustomBehaviour);
	}
}
