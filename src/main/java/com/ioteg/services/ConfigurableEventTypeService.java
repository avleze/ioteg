package com.ioteg.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ioteg.model.ChannelType;
import com.ioteg.model.ConfigurableEventType;
import com.ioteg.repositories.ConfigurableEventTypeRepository;

@Service
public class ConfigurableEventTypeService {

	@Autowired
	private ChannelTypeService channelTypeService;

	@Autowired
	private ConfigurableEventTypeRepository configurableEventTypeRepository;

	public ConfigurableEventType createConfigurableEventType(Long channelId,
			ConfigurableEventType configurableEventType) throws ResourceNotFoundException {
		
		
		ConfigurableEventType storedConfigurableEventType = configurableEventTypeRepository.save(configurableEventType);

		ChannelType channelType = channelTypeService.loadById(channelId);
		channelType.getConfigurableEventTypes().add(storedConfigurableEventType);
		channelTypeService.save(channelType);

		return storedConfigurableEventType;
	}

	public ConfigurableEventType modifyConfigurableEventType(Long configurableEventTypeId, ConfigurableEventType configurableEventType) throws ResourceNotFoundException {
		ConfigurableEventType storedConfigurableEventType = configurableEventTypeRepository.findById(configurableEventTypeId).orElseThrow(() -> new ResourceNotFoundException("configurableEventType " + configurableEventTypeId, "ConfigurableEventType not found."));
		
		storedConfigurableEventType.getEventType().setName(configurableEventType.getEventType().getName());
		storedConfigurableEventType.setDelay(configurableEventType.getDelay());
		storedConfigurableEventType.setPeriod(configurableEventType.getPeriod());
		storedConfigurableEventType.setUnit(configurableEventType.getUnit());
		
		return configurableEventTypeRepository.save(storedConfigurableEventType);
	}

	public void removeConfigurableEventType(Long configurableEventTypeId) {
		configurableEventTypeRepository.deleteById(configurableEventTypeId);
	}
	
	public ConfigurableEventType loadById(Long configurableEventTypeId) throws ResourceNotFoundException {
		return configurableEventTypeRepository.findById(configurableEventTypeId).orElseThrow(() -> new ResourceNotFoundException("configurableEventType " + configurableEventTypeId, "ConfigurableEventType not found"));
	}
	
	public ConfigurableEventType save(ConfigurableEventType channel) {
		return configurableEventTypeRepository.save(channel);
	}
}
