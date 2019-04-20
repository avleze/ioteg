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
	private OptionalFieldsRepository optionalFieldsService;
	private UserService userService;
	
	/**
	 * @param blockService
	 * @param optionalFieldsService
	 * @param userService
	 */
	@Autowired
	public OptionalFieldsService(BlockService blockService, OptionalFieldsRepository optionalFieldsService,
			UserService userService) {
		super();
		this.blockService = blockService;
		this.optionalFieldsService = optionalFieldsService;
		this.userService = userService;
	}

	@PreAuthorize("hasPermission(#blockId, 'Block', 'OWNER')")
	public OptionalFields createOptionalFields(Long blockId, OptionalFields optionalFields) throws ResourceNotFoundException {
		
		optionalFields.setOwner(userService.loadLoggedUser());
		OptionalFields storedOptionalFields = optionalFieldsService.save(optionalFields);
		
		Block block = blockService.loadById(blockId);
		block.getOptionalFields().add(storedOptionalFields);
		blockService.save(block);

		return storedOptionalFields;
	}

	@PreAuthorize("hasPermission(#optionalFieldsId, 'OptionalFields', 'OWNER')")
	public OptionalFields modifyOptionalFields(Long optionalFieldsId, OptionalFields optionalFields) throws ResourceNotFoundException {
		OptionalFields storedOptionalFields = optionalFieldsService.findById(optionalFieldsId)
				.orElseThrow(() -> new ResourceNotFoundException("OptionalFields " + optionalFieldsId, "OptionalFields not found."));

		storedOptionalFields.setMandatory(optionalFields.getMandatory());
		
		return optionalFieldsService.save(storedOptionalFields);
	}

	@PreAuthorize("hasPermission(#optionalFieldsId, 'OptionalFields', 'OWNER')")
	public void removeOptionalFields(Long optionalFieldsId) {
		optionalFieldsService.deleteById(optionalFieldsId);
	}

	@PreAuthorize("hasPermission(#optionalFieldsId, 'OptionalFields', 'OWNER')")
	public OptionalFields loadById(Long optionalFieldsId) throws ResourceNotFoundException {
		return optionalFieldsService.findById(optionalFieldsId)
				.orElseThrow(() -> new ResourceNotFoundException("OptionalFields " + optionalFieldsId, "OptionalFields not found."));
	}

	public OptionalFields save(OptionalFields optionalFields) {
		return optionalFieldsService.save(optionalFields);
	}
}
