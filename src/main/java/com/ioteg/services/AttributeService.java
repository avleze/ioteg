package com.ioteg.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.ioteg.model.Attribute;
import com.ioteg.model.Field;
import com.ioteg.repositories.AttributeRepository;

@Service
public class AttributeService {

	private FieldService fieldService;
	private AttributeRepository attributeRepository;
	private UserService userService;

	/**
	 * @param fieldService
	 * @param attributeRepository
	 * @param userService
	 */
	@Autowired
	public AttributeService(FieldService fieldService, AttributeRepository attributeRepository,
			UserService userService) {
		super();
		this.fieldService = fieldService;
		this.attributeRepository = attributeRepository;
		this.userService = userService;
	}


	@PreAuthorize("hasPermission(#fieldId, 'Field', 'OWNER') or hasRole('ADMIN')")
	public Attribute createAttribute(Long fieldId, Attribute attribute) throws EntityNotFoundException {
		attribute.setOwner(userService.loadLoggedUser());
		Attribute storedAttribute = attributeRepository.save(attribute);
	
		Field field = fieldService.loadById(fieldId);
		field.getAttributes().add(storedAttribute);
		fieldService.save(field);

		return storedAttribute;
	}


	@PreAuthorize("hasPermission(#attributeId, 'Attribute', 'OWNER') or hasRole('ADMIN')")
	public Attribute modifyAttribute(Long attributeId, Field field) throws EntityNotFoundException {
		Attribute storedAttribute = this.loadById(attributeId);

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

	@PreAuthorize("hasPermission(#attributeId, 'Attribute', 'OWNER') or hasRole('ADMIN')")
	public void removeAttribute(Long attributeId) {
		attributeRepository.deleteById(attributeId);
	}
	
	@PreAuthorize("hasPermission(#fieldId, 'Field', 'OWNER') or hasRole('ADMIN')")
	public void removeAttributeFromField(Long fieldId, Long attributeId) throws EntityNotFoundException {
		fieldService.loadByIdWithAttributes(fieldId).getAttributes().remove(this.loadById(attributeId));
	}

	@PreAuthorize("hasPermission(#attributeId, 'Attribute', 'OWNER') or hasRole('ADMIN')")
	public Attribute loadById(Long attributeId) throws EntityNotFoundException {
		return attributeRepository.findById(attributeId)
				.orElseThrow(() -> new EntityNotFoundException(Attribute.class, "id", attributeId.toString()));
	}

	public Attribute save(Attribute attribute) {
		return attributeRepository.save(attribute);
	}
}
