package com.ioteg.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ioteg.model.Block;
import com.ioteg.model.OptionalFields;
import com.ioteg.repositories.OptionalFieldsRepository;

@Service
public class OptionalFieldsService {

	@Autowired
	private BlockService blockService;

	@Autowired
	private OptionalFieldsRepository optionalFieldsService;
	
	

	public OptionalFields createOptionalFields(Long blockId, OptionalFields optionalFields) throws ResourceNotFoundException {

		OptionalFields storedOptionalFields = optionalFieldsService.save(optionalFields);
		
		Block block = blockService.loadById(blockId);
		block.getOptionalFields().add(storedOptionalFields);
		blockService.save(block);

		return storedOptionalFields;
	}


	public OptionalFields modifyOptionalFields(Long optionalFieldsId, OptionalFields optionalFields) throws ResourceNotFoundException {
		OptionalFields storedOptionalFields = optionalFieldsService.findById(optionalFieldsId)
				.orElseThrow(() -> new ResourceNotFoundException("OptionalFields " + optionalFieldsId, "OptionalFields not found."));

		storedOptionalFields.setMandatory(optionalFields.getMandatory());
		
		return optionalFieldsService.save(storedOptionalFields);
	}

	public void removeOptionalFields(Long optionalFieldsId) {
		optionalFieldsService.deleteById(optionalFieldsId);
	}

	public OptionalFields loadById(Long optionalFieldsId) throws ResourceNotFoundException {
		return optionalFieldsService.findById(optionalFieldsId)
				.orElseThrow(() -> new ResourceNotFoundException("OptionalFields " + optionalFieldsId, "OptionalFields not found."));
	}

	public OptionalFields save(OptionalFields optionalFields) {
		return optionalFieldsService.save(optionalFields);
	}
}
