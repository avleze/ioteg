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
	
		Field field = fieldService.loadByIdWithAttributes(fieldId);
		field.getAttributes().add(storedAttribute);
		fieldService.save(field);

		return storedAttribute;
	}


	@PreAuthorize("hasPermission(#attributeId, 'Attribute', 'OWNER') or hasRole('ADMIN')")
	public Attribute modifyAttribute(Long attributeId, Attribute attribute) throws EntityNotFoundException {
		Attribute storedAttribute = this.loadById(attributeId);

		storedAttribute.setBegin(attribute.getBegin());
		storedAttribute.setCase(attribute.getCase());
		storedAttribute.setEnd(attribute.getEnd());
		storedAttribute.setEndcharacter(attribute.getEndcharacter());
		storedAttribute.setFormat(attribute.getFormat());
		storedAttribute.setIsNumeric(attribute.getIsNumeric());
		storedAttribute.setLength(attribute.getLength());
		storedAttribute.setMax(attribute.getMax());
		storedAttribute.setMin(attribute.getMin());
		storedAttribute.setPrecision(attribute.getPrecision());
		storedAttribute.setStep(attribute.getStep());
		storedAttribute.setType(attribute.getType());
		storedAttribute.setUnit(attribute.getUnit());
		storedAttribute.setValue(attribute.getValue());
		storedAttribute.setGenerationType(attribute.getGenerationType());
		
		return attributeRepository.save(storedAttribute);
	}

	@PreAuthorize("hasPermission(#attributeId, 'Attribute', 'OWNER') or hasRole('ADMIN')")
	public void removeAttribute(Long attributeId) {
		attributeRepository.deleteById(attributeId);
	}
	
	@PreAuthorize("hasPermission(#fieldId, 'Field', 'OWNER') or hasRole('ADMIN')")
	public void removeAttributeFromField(Long fieldId, Long attributeId) throws EntityNotFoundException {
		Field field = fieldService.loadByIdWithAttributes(fieldId);
		field.getAttributes().remove(this.loadById(attributeId));
		fieldService.save(field);
		this.removeAttribute(attributeId);
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
