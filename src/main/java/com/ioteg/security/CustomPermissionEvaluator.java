package com.ioteg.security;

import java.io.Serializable;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.ioteg.model.OwnedResource;
import com.ioteg.model.User;
import com.ioteg.repositories.AttributeRepository;
import com.ioteg.repositories.BlockRepository;
import com.ioteg.repositories.ConfigurableEventTypeRepository;
import com.ioteg.repositories.CustomBehaviourRepository;
import com.ioteg.repositories.EventTypeRepository;
import com.ioteg.repositories.FieldRepository;
import com.ioteg.repositories.OptionalFieldsRepository;
import com.ioteg.repositories.RuleCustomBehaviourRepository;
import com.ioteg.repositories.UserRepository;
import com.ioteg.repositories.VariableCustomBehaviourRepository;

@Component
public class CustomPermissionEvaluator implements PermissionEvaluator {

	Logger logger = LoggerFactory.getLogger(CustomPermissionEvaluator.class);

	private ConfigurableEventTypeRepository configurableEventTypeRepository;
	private EventTypeRepository eventTypeRepository;
	private BlockRepository blockRepository;
	private FieldRepository fieldRepository;
	private AttributeRepository attributeRepository;
	private OptionalFieldsRepository optionalFieldsRepository;
	private CustomBehaviourRepository customBehaviourRepository;
	private RuleCustomBehaviourRepository ruleCustomBehaviourRepository;
	private VariableCustomBehaviourRepository variableCustomBehaviourRepository;
	private UserRepository userRepository;

	
	
	/**
	 * @param configurableEventTypeRepository
	 * @param eventTypeRepository
	 * @param blockRepository
	 * @param fieldRepository
	 * @param attributeRepository
	 * @param optionalFieldsRepository
	 * @param customBehaviourRepository
	 * @param ruleCustomBehaviourRepository
	 * @param variableCustomBehaviourRepository
	 * @param userRepository
	 */
	@Autowired
	public CustomPermissionEvaluator(ConfigurableEventTypeRepository configurableEventTypeRepository,
			EventTypeRepository eventTypeRepository, BlockRepository blockRepository, FieldRepository fieldRepository,
			AttributeRepository attributeRepository, OptionalFieldsRepository optionalFieldsRepository,
			CustomBehaviourRepository customBehaviourRepository,
			RuleCustomBehaviourRepository ruleCustomBehaviourRepository,
			VariableCustomBehaviourRepository variableCustomBehaviourRepository, UserRepository userRepository) {
		super();
		this.configurableEventTypeRepository = configurableEventTypeRepository;
		this.eventTypeRepository = eventTypeRepository;
		this.blockRepository = blockRepository;
		this.fieldRepository = fieldRepository;
		this.attributeRepository = attributeRepository;
		this.optionalFieldsRepository = optionalFieldsRepository;
		this.customBehaviourRepository = customBehaviourRepository;
		this.ruleCustomBehaviourRepository = ruleCustomBehaviourRepository;
		this.variableCustomBehaviourRepository = variableCustomBehaviourRepository;
		this.userRepository = userRepository;
	}

	@Override
	public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {

		if (permission.equals("OWNER") && targetDomainObject instanceof OwnedResource) {
			OwnedResource ownedResource = (OwnedResource) targetDomainObject;
			return ownedResource.getOwner().getUsername().equals(authentication.getPrincipal());
		}

		return false;
	}

	@Override
	public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType,
			Object permission) {

		if (permission.equals("OWNER"))
			return hasOwnerPermission(authentication, targetId, targetType);

		return false;
	}

	private boolean hasOwnerPermission(Authentication authentication, Serializable targetId, String targetType) {
		logger.info("targetId: {}, targetType: {}", targetId, targetType);

		Long id = (Long) targetId;
		Optional<User> owner = Optional.empty();
		switch (targetType) {
		case "ConfigurableEventType":
			owner = configurableEventTypeRepository.findOwner(id);
			break;
		case "EventType":
			owner = eventTypeRepository.findOwner(id);
			break;
		case "Block":
			owner = blockRepository.findOwner(id);
			break;
		case "Field":
			owner = fieldRepository.findOwner(id);
			break;
		case "Attribute":
			owner = attributeRepository.findOwner(id);
			break;
		case "OptionalFields":
			owner = optionalFieldsRepository.findOwner(id);
			break;
		case "CustomBehaviour":
			owner = customBehaviourRepository.findOwner(id);
			break;
		case "RuleCustomBehaviour":
			owner = ruleCustomBehaviourRepository.findOwner(id);
			break;
		case "VariableCustomBehaviour":
			owner = variableCustomBehaviourRepository.findOwner(id);
			break;
		case "User":
			owner = userRepository.findById(id);
			break;
		}

		if (owner.isPresent())
			return owner.get().getUsername().equals(authentication.getPrincipal());
		else
			return false;
	}

}
