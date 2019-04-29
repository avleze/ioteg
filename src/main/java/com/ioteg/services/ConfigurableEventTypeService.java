package com.ioteg.services;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.ioteg.model.ChannelType;
import com.ioteg.model.ConfigurableEventType;
import com.ioteg.model.User;
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

	@PreAuthorize("hasPermission(#channelId, 'ChannelType', 'OWNER') or hasRole('ADMIN')")
	public ConfigurableEventType createConfigurableEventType(Long channelId,
			ConfigurableEventType configurableEventType) throws EntityNotFoundException {
		User owner = userService.loadLoggedUser();
		configurableEventType.setOwner(owner);
		configurableEventType.getEventType().setOwner(owner);
		ConfigurableEventType storedConfigurableEventType = configurableEventTypeRepository.save(configurableEventType);

		ChannelType channelType = channelTypeService.loadByIdWithConfigurableEvents(channelId);
		channelType.getConfigurableEventTypes().add(storedConfigurableEventType);
		channelTypeService.save(channelType);

		return storedConfigurableEventType;
	}

	@PreAuthorize("hasPermission(#configurableEventTypeId, 'ConfigurableEventType', 'OWNER') or hasRole('ADMIN')")
	public ConfigurableEventType modifyConfigurableEventType(Long configurableEventTypeId,
			ConfigurableEventType configurableEventType) throws EntityNotFoundException {
		ConfigurableEventType storedConfigurableEventType = this.loadById(configurableEventTypeId);

		storedConfigurableEventType.getEventType().setName(configurableEventType.getEventType().getName());
		storedConfigurableEventType.setDelay(configurableEventType.getDelay());
		storedConfigurableEventType.setPeriod(configurableEventType.getPeriod());
		storedConfigurableEventType.setUnit(configurableEventType.getUnit());

		return configurableEventTypeRepository.save(storedConfigurableEventType);
	}

	@PreAuthorize("hasPermission(#configurableEventTypeId, 'ConfigurableEventType', 'OWNER') or hasRole('ADMIN')")
	public void removeConfigurableEventType(Long configurableEventTypeId) {
		configurableEventTypeRepository.deleteById(configurableEventTypeId);
	}

	@PreAuthorize("hasPermission(#channelId, 'ChannelType', 'OWNER') or hasRole('ADMIN')")
	public void removeConfigurableEventTypeFromChannel(Long channelId, Long configurableEventTypeId) throws EntityNotFoundException {
		ChannelType channelType = channelTypeService.loadByIdWithConfigurableEvents(channelId);
		
		channelType.getConfigurableEventTypes()
				.remove(this.loadById(configurableEventTypeId));
		
		channelTypeService.save(channelType);
		
		this.removeConfigurableEventType(configurableEventTypeId);
	}

	@PreAuthorize("hasPermission(#configurableEventTypeId, 'ConfigurableEventType', 'OWNER') or hasRole('ADMIN')")
	public ConfigurableEventType loadById(Long configurableEventTypeId) throws EntityNotFoundException {
		return configurableEventTypeRepository.findById(configurableEventTypeId)
				.orElseThrow(() -> new EntityNotFoundException(ConfigurableEventType.class, "id",
						configurableEventTypeId.toString()));
	}

	public ConfigurableEventType save(ConfigurableEventType channel) {
		return configurableEventTypeRepository.save(channel);
	}
}
