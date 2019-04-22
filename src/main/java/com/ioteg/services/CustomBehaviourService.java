package com.ioteg.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.ioteg.model.CustomBehaviour;
import com.ioteg.model.Field;
import com.ioteg.repositories.CustomBehaviourRepository;

@Service
public class CustomBehaviourService {

	private FieldService fieldService;
	private CustomBehaviourRepository customBehaviourRepository;
	private UserService userService;
	
	/**
	 * @param fieldService
	 * @param customBehaviourRepository
	 * @param userService
	 */
	@Autowired
	public CustomBehaviourService(FieldService fieldService, CustomBehaviourRepository customBehaviourRepository,
			UserService userService) {
		super();
		this.fieldService = fieldService;
		this.customBehaviourRepository = customBehaviourRepository;
		this.userService = userService;
	}

	@PreAuthorize("hasPermission(#fieldId, 'Field', 'OWNER')")
	public CustomBehaviour createCustomBehaviour(Long fieldId,
			CustomBehaviour customBehaviour) throws EntityNotFoundException {
	
		customBehaviour.setOwner(userService.loadLoggedUser());
		CustomBehaviour storedCustomBehaviour = customBehaviourRepository.save(customBehaviour);
		
		Field field = fieldService.loadById(fieldId);
		field.setCustomBehaviour(storedCustomBehaviour);
		fieldService.save(field);

		return storedCustomBehaviour;
	}

	@PreAuthorize("hasPermission(#customBehaviourId, 'CustomBehaviour', 'OWNER') or hasRole('ADMIN')")
	public CustomBehaviour modifyCustomBehaviour(Long customBehaviourId, CustomBehaviour customBehaviour) throws EntityNotFoundException {
		CustomBehaviour storedCustomBehaviour = this.loadById(customBehaviourId);
		
		storedCustomBehaviour.setExternalFilePath(customBehaviour.getExternalFilePath());
		storedCustomBehaviour.setSimulations(customBehaviour.getSimulations());
		
		return customBehaviourRepository.save(storedCustomBehaviour);
	}

	@PreAuthorize("hasPermission(#customBehaviourId, 'CustomBehaviour', 'OWNER') or hasRole('ADMIN')")
	public void removeCustomBehaviour(Long customBehaviourId) {
		customBehaviourRepository.deleteById(customBehaviourId);
	}
	
	@PreAuthorize("hasPermission(#customBehaviourId, 'CustomBehaviour', 'OWNER') or hasRole('ADMIN')")
	public CustomBehaviour loadById(Long customBehaviourId) throws EntityNotFoundException {
		return customBehaviourRepository.findById(customBehaviourId).orElseThrow(() -> new EntityNotFoundException(CustomBehaviour.class, "id", customBehaviourId.toString()));
	}
	
	public CustomBehaviour save(CustomBehaviour customBehaviour) {
		return customBehaviourRepository.save(customBehaviour);
	}
}
