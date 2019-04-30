package com.ioteg.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ioteg.model.ConfigurableEventType;
import com.ioteg.model.EventType;
import com.ioteg.repositories.BlockRepository;
import com.ioteg.repositories.ConfigurableEventTypeRepository;
import com.ioteg.repositories.CustomBehaviourRepository;
import com.ioteg.repositories.EventTypeRepository;
import com.ioteg.repositories.FieldRepository;

@Service
public class GenerationService {
	private ConfigurableEventTypeRepository configurableEventTypeRepository;
	private EventTypeRepository eventTypeRepository;
	private BlockRepository blockRepository;
	private FieldRepository fieldRepository;
	private CustomBehaviourRepository customBehaviourRepository;
	
	/**
	 * @param configurableEventTypeRepository
	 * @param eventTypeRepository
	 * @param blockRepository
	 * @param fieldRepository
	 * @param customBehaviourRepository
	 */
	@Autowired
	public GenerationService(ConfigurableEventTypeRepository configurableEventTypeRepository,
			EventTypeRepository eventTypeRepository, BlockRepository blockRepository, FieldRepository fieldRepository,
			CustomBehaviourRepository customBehaviourRepository) {
		super();
		this.configurableEventTypeRepository = configurableEventTypeRepository;
		this.eventTypeRepository = eventTypeRepository;
		this.blockRepository = blockRepository;
		this.fieldRepository = fieldRepository;
		this.customBehaviourRepository = customBehaviourRepository;
	}



	public ConfigurableEventType loadConfigurableEventTypeDeeply(Long id) {
		ConfigurableEventType configurableEventType = configurableEventTypeRepository.findById(id).get();
		EventType eventType = configurableEventType.getEventType();
		eventType.setBlocks(eventTypeRepository.findAllBlocksOf(eventType.getId()));

		eventType.getBlocks().stream().forEach(block -> {

			Long blockId = block.getId();
			block.setFields(blockRepository.findByIdWithFields(blockId).get().getFields());
			block.setOptionalFields(blockRepository.findByIdWithOptionalFields(blockId).get().getOptionalFields());
			block.setInjectedFields(blockRepository.findByIdWithInjectedFields(blockId).get().getInjectedFields());

			block.getFields().stream().forEach(field -> {
				Long fieldId = field.getId();

				field.setAttributes(fieldRepository.findAllAttributesOf(fieldId));
				field.setFields(fieldRepository.findAllFieldsOf(fieldId));
				
				if(field.getCustomBehaviour() != null) {
					Long customBehaviourId = field.getCustomBehaviour().getId();
					field.getCustomBehaviour().setRules(customBehaviourRepository.findAllRulesOf(customBehaviourId));
					field.getCustomBehaviour().setVariables(customBehaviourRepository.findAllVariablesOf(customBehaviourId));
				}
			});

			block.getOptionalFields().stream().forEach(optionalFields -> {
				optionalFields.getId();

				optionalFields.getFields().stream().forEach(field -> {
					Long fieldId = field.getId();

					field.setAttributes(fieldRepository.findAllAttributesOf(fieldId));
					field.setFields(fieldRepository.findAllFieldsOf(fieldId));
					
					if(field.getCustomBehaviour() != null) {
						Long customBehaviourId = field.getCustomBehaviour().getId();
						field.getCustomBehaviour().setRules(customBehaviourRepository.findAllRulesOf(customBehaviourId));
						field.getCustomBehaviour().setVariables(customBehaviourRepository.findAllVariablesOf(customBehaviourId));
					}
				});
			});
		});
		return configurableEventType;

	}
}
