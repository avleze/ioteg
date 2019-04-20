package com.ioteg.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ioteg.model.Block;
import com.ioteg.model.Field;
import com.ioteg.model.OptionalFields;
import com.ioteg.repositories.FieldRepository;

@Service
public class FieldService {

	@Autowired
	private BlockService blockService;

	@Autowired
	private OptionalFieldsService optionalFieldsService;
	
	@Autowired
	private FieldRepository fieldRepository;

	public Field createField(Long blockId, Field field) throws ResourceNotFoundException {

		Field storedField = fieldRepository.save(field);
		Block block = blockService.loadById(blockId);
		block.getFields().add(storedField);
		blockService.save(block);

		return storedField;
	}

	public Field createSubField(Long fieldId, Field field) throws ResourceNotFoundException {

		Field storedField = fieldRepository.save(field);

		Field parentField = this.loadById(fieldId);
		parentField.getFields().add(storedField);
		this.save(parentField);

		return storedField;
	}
	
	public Field createFieldInOptionalFields(Long optionalFieldsId, Field field) throws ResourceNotFoundException {

		Field storedField = fieldRepository.save(field);

		OptionalFields parentOptionalFields = optionalFieldsService.loadById(optionalFieldsId);
		parentOptionalFields.getFields().add(storedField);
		optionalFieldsService.save(parentOptionalFields);

		return storedField;
	}

	public Field modifyField(Long fieldId, Field field) throws ResourceNotFoundException {
		Field storedField = fieldRepository.findById(fieldId)
				.orElseThrow(() -> new ResourceNotFoundException("Field " + fieldId, "Field not found."));

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

	public void removeField(Long fieldId) {
		fieldRepository.deleteById(fieldId);
	}

	public Field loadById(Long fieldId) throws ResourceNotFoundException {
		return fieldRepository.findById(fieldId)
				.orElseThrow(() -> new ResourceNotFoundException("Field " + fieldId, "Field not found."));
	}

	public Field save(Field block) {
		return fieldRepository.save(block);
	}
}
