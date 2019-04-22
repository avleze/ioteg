package com.ioteg.services;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.ioteg.model.ChannelType;
import com.ioteg.model.ConfigurableEventType;
import com.ioteg.repositories.ConfigurableEventTypeRepository;

@Service
public class ConfigurableEventTypeService {

	private ChannelTypeService channelTypeService;
	private ConfigurableEventTypeRepository configurableEventTypeRepository;
	private UserService userService;
	
	/**
	 * @param channelTypeService
	 * @param configurableEventTypeRepository
	 * @param userService
	 */
	public ConfigurableEventTypeService(ChannelTypeService channelTypeService,
			ConfigurableEventTypeRepository configurableEventTypeRepository, UserService userService) {
		super();
		this.channelTypeService = channelTypeService;
		this.configurableEventTypeRepository = configurableEventTypeRepository;
		this.userService = userService;
	}

	@PreAuthorize("hasPermission(#channelId, 'ChannelType', 'OWNER')")
	public ConfigurableEventType createConfigurableEventType(Long channelId,
			ConfigurableEventType configurableEventType) throws EntityNotFoundException {
		
		configurableEventType.setOwner(userService.loadLoggedUser());
		ConfigurableEventType storedConfigurableEventType = configurableEventTypeRepository.save(configurableEventType);

		ChannelType channelType = channelTypeService.loadById(channelId);
		channelType.getConfigurableEventTypes().add(storedConfigurableEventType);
		channelTypeService.save(channelType);

		return storedConfigurableEventType;
	}

	@PreAuthorize("hasPermission(#configurableEventTypeId, 'ConfigurableEventType', 'OWNER')")
	public ConfigurableEventType modifyConfigurableEventType(Long configurableEventTypeId, ConfigurableEventType configurableEventType) throws ResourceNotFoundException {
		ConfigurableEventType storedConfigurableEventType = configurableEventTypeRepository.findById(configurableEventTypeId).orElseThrow(() -> new ResourceNotFoundException("configurableEventType " + configurableEventTypeId, "ConfigurableEventType not found."));
		
		storedConfigurableEventType.getEventType().setName(configurableEventType.getEventType().getName());
		storedConfigurableEventType.setDelay(configurableEventType.getDelay());
		storedConfigurableEventType.setPeriod(configurableEventType.getPeriod());
		storedConfigurableEventType.setUnit(configurableEventType.getUnit());
		
		return configurableEventTypeRepository.save(storedConfigurableEventType);
	}

	@PreAuthorize("hasPermission(#configurableEventTypeId, 'ConfigurableEventType', 'OWNER')")
	public void removeConfigurableEventType(Long configurableEventTypeId) {
		configurableEventTypeRepository.deleteById(configurableEventTypeId);
	}
	
	@PreAuthorize("hasPermission(#configurableEventTypeId, 'ConfigurableEventType', 'OWNER')")
	public ConfigurableEventType loadById(Long configurableEventTypeId) throws EntityNotFoundException {
		return configurableEventTypeRepository.findById(configurableEventTypeId).orElseThrow(() -> new EntityNotFoundException(ConfigurableEventType.class, "id", configurableEventTypeId.toString()));
	}
	
	public ConfigurableEventType save(ConfigurableEventType channel) {
		return configurableEventTypeRepository.save(channel);
	}
}
