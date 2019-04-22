package com.ioteg.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.ioteg.model.Block;
import com.ioteg.model.OptionalFields;
import com.ioteg.repositories.OptionalFieldsRepository;

@Service
public class OptionalFieldsService {

	private BlockService blockService;
	private OptionalFieldsRepository optionalFieldsRepository;
	private UserService userService;
	
	/**
	 * @param blockService
	 * @param optionalFieldsRepository
	 * @param userService
	 */
	@Autowired
	public OptionalFieldsService(BlockService blockService, OptionalFieldsRepository optionalFieldsRepository,
			UserService userService) {
		super();
		this.blockService = blockService;
		this.optionalFieldsRepository = optionalFieldsRepository;
		this.userService = userService;
	}

	@PreAuthorize("hasPermission(#blockId, 'Block', 'OWNER')")
	public OptionalFields createOptionalFields(Long blockId, OptionalFields optionalFields) throws ResourceNotFoundException {
		
		optionalFields.setOwner(userService.loadLoggedUser());
		OptionalFields storedOptionalFields = optionalFieldsRepository.save(optionalFields);
		
		Block block = blockService.loadById(blockId);
		block.getOptionalFields().add(storedOptionalFields);
		blockService.save(block);

		return storedOptionalFields;
	}

	@PreAuthorize("hasPermission(#optionalFieldsId, 'OptionalFields', 'OWNER')")
	public OptionalFields modifyOptionalFields(Long optionalFieldsId, OptionalFields optionalFields) throws ResourceNotFoundException {
		OptionalFields storedOptionalFields = optionalFieldsRepository.findById(optionalFieldsId)
				.orElseThrow(() -> new ResourceNotFoundException("OptionalFields " + optionalFieldsId, "OptionalFields not found."));

		storedOptionalFields.setMandatory(optionalFields.getMandatory());
		
		return optionalFieldsRepository.save(storedOptionalFields);
	}

	@PreAuthorize("hasPermission(#optionalFieldsId, 'OptionalFields', 'OWNER')")
	public void removeOptionalFields(Long optionalFieldsId) {
		optionalFieldsRepository.deleteById(optionalFieldsId);
	}

	@PreAuthorize("hasPermission(#optionalFieldsId, 'OptionalFields', 'OWNER')")
	public OptionalFields loadById(Long optionalFieldsId) throws ResourceNotFoundException {
		return optionalFieldsRepository.findById(optionalFieldsId)
				.orElseThrow(() -> new ResourceNotFoundException("OptionalFields " + optionalFieldsId, "OptionalFields not found."));
	}

	public OptionalFields save(OptionalFields optionalFields) {
		return optionalFieldsRepository.save(optionalFields);
	}
}
