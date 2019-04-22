package com.ioteg.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.ioteg.model.CustomBehaviour;
import com.ioteg.model.VariableCustomBehaviour;
import com.ioteg.repositories.VariableCustomBehaviourRepository;

@Service
public class VariableCustomBehaviourService {

	private CustomBehaviourService customBehaviourService;
	private VariableCustomBehaviourRepository variableCustomBehaviourRepository;
	private UserService userService;
	
	/**
	 * @param customBehaviourService
	 * @param variableCustomBehaviourRepository
	 * @param userService
	 */
	@Autowired
	public VariableCustomBehaviourService(CustomBehaviourService customBehaviourService,
			VariableCustomBehaviourRepository variableCustomBehaviourRepository, UserService userService) {
		super();
		this.customBehaviourService = customBehaviourService;
		this.variableCustomBehaviourRepository = variableCustomBehaviourRepository;
		this.userService = userService;
	}

	@PreAuthorize("hasPermission(#customBehaviourId, 'CustomBehaviour', 'OWNER')")
	public VariableCustomBehaviour createVariableCustomBehaviourRepository(Long customBehaviourId,
			VariableCustomBehaviour variableCustomBehaviour) throws EntityNotFoundException {
	
		variableCustomBehaviour.setOwner(userService.loadLoggedUser());
		VariableCustomBehaviour storedVariableCustomBehaviour = variableCustomBehaviourRepository.save(variableCustomBehaviour);
		
		CustomBehaviour customBehaviour = customBehaviourService.loadById(customBehaviourId);
		customBehaviour.getVariables().add(storedVariableCustomBehaviour);
		customBehaviourService.save(customBehaviour);

		return storedVariableCustomBehaviour;
	}
	
	@PreAuthorize("hasPermission(#variableCustomBehaviourId, 'VariableCustomBehaviour', 'OWNER')")
	public VariableCustomBehaviour modifyVariableCustomBehaviour(Long variableCustomBehaviourId,
			VariableCustomBehaviour variableCustomBehaviour) throws EntityNotFoundException  {
	
		VariableCustomBehaviour storedVariableCustomBehaviour =  this.loadById(variableCustomBehaviourId);
		
		storedVariableCustomBehaviour.setMax(variableCustomBehaviour.getMax());
		storedVariableCustomBehaviour.setMin(variableCustomBehaviour.getMin());
		storedVariableCustomBehaviour.setName(variableCustomBehaviour.getName());
		storedVariableCustomBehaviour.setValue(variableCustomBehaviour.getValue());

		return variableCustomBehaviourRepository.save(storedVariableCustomBehaviour);
	}
	
	@PreAuthorize("hasPermission(#variableCustomBehaviourId, 'VariableCustomBehaviour', 'OWNER')")
	public void removeVariableCustomBehaviour(Long variableCustomBehaviourId) {
		variableCustomBehaviourRepository.deleteById(variableCustomBehaviourId);
	}

	@PreAuthorize("hasPermission(#variableCustomBehaviourId, 'VariableCustomBehaviour', 'OWNER')")
	public VariableCustomBehaviour loadById(Long variableCustomBehaviourId) throws EntityNotFoundException {
		return variableCustomBehaviourRepository.findById(variableCustomBehaviourId)
				.orElseThrow(() -> new EntityNotFoundException(VariableCustomBehaviour.class, "id", variableCustomBehaviourId.toString()));
	}

	public VariableCustomBehaviour save(VariableCustomBehaviour variableCustomBehaviour) {
		return variableCustomBehaviourRepository.save(variableCustomBehaviour);
	}
}
