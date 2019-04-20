package com.ioteg.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ioteg.model.Attribute;
import com.ioteg.model.Field;
import com.ioteg.repositories.AttributeRepository;

@Service
public class AttributeService {

	@Autowired
	private FieldService fieldService;

	@Autowired
	private AttributeRepository attributeRepository;

	public Attribute createAttribute(Long fieldId, Attribute attribute) throws ResourceNotFoundException {

		Attribute storedAttribute = attributeRepository.save(attribute);
		
		Field field = fieldService.loadById(fieldId);
		field.getAttributes().add(storedAttribute);
		fieldService.save(field);

		return storedAttribute;
	}


	public Attribute modifyAttribute(Long attributeId, Field field) throws ResourceNotFoundException {
		Attribute storedAttribute = attributeRepository.findById(attributeId)
				.orElseThrow(() -> new ResourceNotFoundException("Attribute " + attributeId, "Attribute not found."));

		storedAttribute.setBegin(field.getBegin());
		storedAttribute.setCase(field.getCase());
		storedAttribute.setEnd(field.getEnd());
		storedAttribute.setEndcharacter(field.getEndcharacter());
		storedAttribute.setFormat(field.getFormat());
		storedAttribute.setIsNumeric(field.getIsNumeric());
		storedAttribute.setLength(field.getLength());
		storedAttribute.setMax(field.getMax());
		storedAttribute.setMin(field.getMin());
		storedAttribute.setPrecision(field.getPrecision());
		storedAttribute.setStep(field.getStep());
		storedAttribute.setType(field.getType());
		storedAttribute.setUnit(field.getUnit());
		storedAttribute.setValue(field.getValue());

		return attributeRepository.save(storedAttribute);
	}

	public void removeAttribute(Long attributeId) {
		attributeRepository.deleteById(attributeId);
	}

	public Attribute loadById(Long attributeId) throws ResourceNotFoundException {
		return attributeRepository.findById(attributeId)
				.orElseThrow(() -> new ResourceNotFoundException("Attribute " + attributeId, "Attribute not found."));
	}

	public Attribute save(Attribute attribute) {
		return attributeRepository.save(attribute);
	}
}
