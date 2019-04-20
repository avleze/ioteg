package com.ioteg.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ioteg.model.CustomBehaviour;
import com.ioteg.model.Field;
import com.ioteg.repositories.CustomBehaviourRepository;

@Service
public class CustomBehaviourService {

	@Autowired
	private FieldService fieldService;
	
	@Autowired
	private CustomBehaviourRepository customBehaviourRepository;

	public CustomBehaviour createCustomBehaviour(Long fieldId,
			CustomBehaviour customBehaviour) throws ResourceNotFoundException {
	
		CustomBehaviour storedCustomBehaviour = customBehaviourRepository.save(customBehaviour);
		
		Field field = fieldService.loadById(fieldId);
		field.setCustomBehaviour(storedCustomBehaviour);
		fieldService.save(field);

		return storedCustomBehaviour;
	}

	public CustomBehaviour modifyCustomBehaviour(Long customBehaviourId, CustomBehaviour customBehaviour) throws ResourceNotFoundException {
		CustomBehaviour storedCustomBehaviour = customBehaviourRepository.findById(customBehaviourId).orElseThrow(() -> new ResourceNotFoundException("CustomBehaviour " + customBehaviourId, "CustomBehaviour not found."));
		
		storedCustomBehaviour.setExternalFilePath(customBehaviour.getExternalFilePath());
		storedCustomBehaviour.setSimulations(customBehaviour.getSimulations());
		
		return customBehaviourRepository.save(storedCustomBehaviour);
	}

	public void removeCustomBehaviour(Long customBehaviourId) {
		customBehaviourRepository.deleteById(customBehaviourId);
	}
	
	public CustomBehaviour loadById(Long customBehaviourId) throws ResourceNotFoundException {
		return customBehaviourRepository.findById(customBehaviourId).orElseThrow(() -> new ResourceNotFoundException("CustomBehaviour " + customBehaviourId, "CustomBehaviour not found."));
	}
	
	public CustomBehaviour save(CustomBehaviour customBehaviour) {
		return customBehaviourRepository.save(customBehaviour);
	}
}
