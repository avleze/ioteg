package com.ioteg.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.ioteg.model.Attribute;
import com.ioteg.model.Block;
import com.ioteg.model.CustomBehaviour;
import com.ioteg.model.Field;
import com.ioteg.model.OptionalFields;
import com.ioteg.repositories.CustomBehaviourRepository;
import com.ioteg.repositories.FieldRepository;

@Service
public class FieldService {

	private BlockService blockService;
	private OptionalFieldsService optionalFieldsService;
	private FieldRepository fieldRepository;
	private UserService userService;
	private CustomBehaviourRepository customBehaviourRepository;

	/**
	 * @param blockService
	 * @param optionalFieldsService
	 * @param fieldRepository
	 * @param userService
	 */
	@Autowired
	public FieldService(BlockService blockService, OptionalFieldsService optionalFieldsService,
			FieldRepository fieldRepository, UserService userService,
			CustomBehaviourRepository customBehaviourRepository) {
		super();
		this.blockService = blockService;
		this.optionalFieldsService = optionalFieldsService;
		this.fieldRepository = fieldRepository;
		this.userService = userService;
		this.customBehaviourRepository = customBehaviourRepository;
	}

	@PreAuthorize("hasPermission(#blockId, 'Block', 'OWNER') or hasRole('ADMIN')")
	public Field createField(Long blockId, Field field) throws EntityNotFoundException {

		field.setOwner(userService.loadLoggedUser());
		if (field.getCustomBehaviour() != null)
			field.getCustomBehaviour().setOwner(userService.loadLoggedUser());
		Field storedField = fieldRepository.save(field);
		Block block = blockService.loadByIdWithFields(blockId);
		block.getFields().add(storedField);
		blockService.save(block);

		return storedField;
	}

	@PreAuthorize("hasPermission(#fieldId, 'Field', 'OWNER') or hasRole('ADMIN')")
	public List<Field> getAllFields(Long fieldId) {
		return fieldRepository.findAllFieldsOf(fieldId);
	}

	@PreAuthorize("hasPermission(#fieldId, 'Field', 'OWNER') or hasRole('ADMIN')")
	public List<Attribute> getAllAttributes(Long fieldId) {
		return fieldRepository.findAllAttributesOf(fieldId);
	}

	@PreAuthorize("hasPermission(#fieldId, 'Field', 'OWNER') or hasRole('ADMIN')")
	public Field createSubField(Long fieldId, Field field) throws EntityNotFoundException {

		field.setOwner(userService.loadLoggedUser());
		if (field.getCustomBehaviour() != null)
			field.getCustomBehaviour().setOwner(userService.loadLoggedUser());
		Field storedField = fieldRepository.save(field);

		Field parentField = this.loadByIdWithFields(fieldId);
		parentField.getFields().add(storedField);
		this.save(parentField);

		return storedField;
	}

	@PreAuthorize("hasPermission(#optionalFieldsId, 'OptionalFields', 'OWNER') or hasRole('ADMIN')")
	public Field createFieldInOptionalFields(Long optionalFieldsId, Field field) throws EntityNotFoundException {

		field.setOwner(userService.loadLoggedUser());
		if (field.getCustomBehaviour() != null)
			field.getCustomBehaviour().setOwner(userService.loadLoggedUser());
		Field storedField = fieldRepository.save(field);

		OptionalFields parentOptionalFields = optionalFieldsService.loadByIdWithFields(optionalFieldsId);
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
		storedField.setGenerationType(field.getGenerationType());
		
		if (field.getCustomBehaviour() != null) {
			field.getCustomBehaviour().setOwner(userService.loadLoggedUser());
			if (storedField.getCustomBehaviour() != null)
				storedField.getCustomBehaviour().setSimulations(field.getCustomBehaviour().getSimulations());
			else
				storedField.setCustomBehaviour(field.getCustomBehaviour());
		} else if (storedField.getCustomBehaviour() != null) {
			CustomBehaviour customBehaviour = storedField.getCustomBehaviour();
			storedField.setCustomBehaviour(null);
			this.save(storedField);
			customBehaviourRepository.delete(customBehaviour);
		}

		return fieldRepository.save(storedField);
	}

	@PreAuthorize("hasPermission(#fieldId, 'Field', 'OWNER') or hasRole('ADMIN')")
	public void removeField(Long fieldId) {
		fieldRepository.deleteById(fieldId);
	}

	@PreAuthorize("hasPermission(#optionalFieldsId, 'OptionalFields', 'OWNER') or hasRole('ADMIN')")
	public void removeFieldFromOptionalFields(Long optionalFieldsId, Long fieldId) throws EntityNotFoundException {
		OptionalFields optionalFields = optionalFieldsService.loadByIdWithFields(optionalFieldsId);
		optionalFields.getFields().remove(this.loadById(fieldId));
		optionalFieldsService.save(optionalFields);

		this.removeField(fieldId);
	}

	@PreAuthorize("hasPermission(#fieldId1, 'Field', 'OWNER') or hasRole('ADMIN')")
	public void removeFieldFromField(Long fieldId1, Long fieldId2) throws EntityNotFoundException {
		Field field = this.loadByIdWithFields(fieldId1);
		field.getFields().remove(this.loadById(fieldId2));
		fieldRepository.save(field);

		this.removeField(fieldId2);
	}

	@PreAuthorize("hasPermission(#blockId, 'Block', 'OWNER') or hasRole('ADMIN')")
	public void removeFieldFromBlock(Long blockId, Long fieldId) throws EntityNotFoundException {
		Block block = blockService.loadByIdWithFields(blockId);
		block.getFields().remove(this.loadById(fieldId));
		blockService.save(block);
		this.removeField(fieldId);
	}

	@PreAuthorize("hasPermission(#fieldId, 'Field', 'OWNER') or hasRole('ADMIN')")
	public Field loadById(Long fieldId) throws EntityNotFoundException {
		return fieldRepository.findById(fieldId)
				.orElseThrow(() -> new EntityNotFoundException(Field.class, "id", fieldId.toString()));
	}

	@PreAuthorize("hasPermission(#fieldId, 'Field', 'OWNER') or hasRole('ADMIN')")
	public Field loadByIdWithAttributes(Long fieldId) throws EntityNotFoundException {
		return fieldRepository.findByIdWithAttributes(fieldId)
				.orElseThrow(() -> new EntityNotFoundException(Field.class, "id", fieldId.toString()));
	}

	@PreAuthorize("hasPermission(#fieldId, 'Field', 'OWNER') or hasRole('ADMIN')")
	public Field loadByIdWithFields(Long fieldId) throws EntityNotFoundException {
		return fieldRepository.findByIdWithFields(fieldId)
				.orElseThrow(() -> new EntityNotFoundException(Field.class, "id", fieldId.toString()));
	}

	public Field save(Field field) {
		return fieldRepository.save(field);
	}
}
