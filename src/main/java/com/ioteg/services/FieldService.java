package com.ioteg.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.ioteg.model.Block;
import com.ioteg.model.Field;
import com.ioteg.model.OptionalFields;
import com.ioteg.repositories.FieldRepository;

@Service
public class FieldService {

	private BlockService blockService;
	private OptionalFieldsService optionalFieldsService;
	private FieldRepository fieldRepository;
	private UserService userService;
	
	/**
	 * @param blockService
	 * @param optionalFieldsService
	 * @param fieldRepository
	 * @param userService
	 */
	@Autowired
	public FieldService(BlockService blockService, OptionalFieldsService optionalFieldsService,
			FieldRepository fieldRepository, UserService userService) {
		super();
		this.blockService = blockService;
		this.optionalFieldsService = optionalFieldsService;
		this.fieldRepository = fieldRepository;
		this.userService = userService;
	}

	@PreAuthorize("hasPermission(#blockId, 'Block', 'OWNER') or hasRole('ADMIN')")
	public Field createField(Long blockId, Field field) throws EntityNotFoundException {

		field.setOwner(userService.loadLoggedUser());
		Field storedField = fieldRepository.save(field);
		Block block = blockService.loadById(blockId);
		block.getFields().add(storedField);
		blockService.save(block);

		return storedField;
	}

	@PreAuthorize("hasPermission(#fieldId, 'Field', 'OWNER') or hasRole('ADMIN')")
	public Field createSubField(Long fieldId, Field field) throws EntityNotFoundException {

		field.setOwner(userService.loadLoggedUser());
		Field storedField = fieldRepository.save(field);

		Field parentField = this.loadById(fieldId);
		parentField.getFields().add(storedField);
		this.save(parentField);

		return storedField;
	}
	
	@PreAuthorize("hasPermission(#optionalFieldsId, 'OptionalFields', 'OWNER') or hasRole('ADMIN')")
	public Field createFieldInOptionalFields(Long optionalFieldsId, Field field) throws EntityNotFoundException {

		field.setOwner(userService.loadLoggedUser());
		Field storedField = fieldRepository.save(field);

		OptionalFields parentOptionalFields = optionalFieldsService.loadById(optionalFieldsId);
		parentOptionalFields.getFields().add(storedField);
		optionalFieldsService.save(parentOptionalFields);

		return storedField;
	}

	@PreAuthorize("hasPermission(#fieldId, 'Field', 'OWNER') or hasRole('ADMIN')")
	public Field modifyField(Long fieldId, Field field) throws EntityNotFoundException {
		Field storedField = this.loadById(fieldId);

		storedField.setBegin(field.getBegin());
		storedField.setCase(field.getCase());
		storedField.setChooseone(field.getChooseone());
		storedField.setDependence(field.getDependence());
		storedField.setEnd(field.getEnd());
		storedField.setEndcharacter(field.getEndcharacter());
		storedField.setFormat(field.getFormat());
		storedField.setInjectable(field.getInjectable());
		storedField.setIsNumeric(field.getIsNumeric());
		storedField.setLength(field.getLength());
		storedField.setMax(field.getMax());
		storedField.setMin(field.getMin());
		storedField.setName(field.getName());
		storedField.setPrecision(field.getPrecision());
		storedField.setQuotes(field.getQuotes());
		storedField.setStep(field.getStep());
		storedField.setType(field.getType());
		storedField.setUnit(field.getUnit());
		storedField.setValue(field.getValue());

		return fieldRepository.save(storedField);
	}

	@PreAuthorize("hasPermission(#fieldId, 'Field', 'OWNER') or hasRole('ADMIN')")
	public void removeField(Long fieldId) {
		fieldRepository.deleteById(fieldId);
	}

	@PreAuthorize("hasPermission(#fieldId, 'Field', 'OWNER') or hasRole('ADMIN')")
	public Field loadById(Long fieldId) throws EntityNotFoundException {
		return fieldRepository.findById(fieldId)
				.orElseThrow(() -> new EntityNotFoundException(Field.class, "id", fieldId.toString()));
	}

	public Field save(Field block) {
		return fieldRepository.save(block);
	}
}
