package com.ioteg.services;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.ioteg.model.ChannelType;
import com.ioteg.model.ConfigurableEventType;
import com.ioteg.model.User;
import com.ioteg.repositories.ChannelTypeRepository;

@Service
public class ChannelTypeService {

	private UserService userService;
	private ChannelTypeRepository channelTypeRepository;

	/**
	 * @param userService
	 * @param channelTypeRepository
	 */
	@Autowired
	public ChannelTypeService(UserService userService, ChannelTypeRepository channelTypeRepository) {
		super();
		this.userService = userService;
		this.channelTypeRepository = channelTypeRepository;
	}

	@PreAuthorize("hasPermission(#userId, 'User', 'OWNER') or hasRole('ADMIN') or hasRole('ADMIN')")
	public ChannelType createChannel(Long userId, ChannelType channel) throws EntityNotFoundException {
		ChannelType storedChannel = channelTypeRepository.save(channel);

		User user = userService.loadUserByIdWithChannels(userId);
		user.getChannels().add(channel);
		userService.save(user);

		return storedChannel;
	}
	

	@PreAuthorize("hasPermission(#channelId, 'ChannelType', 'OWNER') or hasRole('ADMIN')")
	public ChannelType modifyChannel(Long channelId, ChannelType channel) throws EntityNotFoundException {
		ChannelType storedChannel = this.loadById(channelId);
		return storedChannel;
	}
	
	@PreAuthorize("hasPermission(#channelId, 'ChannelType', 'OWNER') or hasRole('ADMIN')")
	public List<ConfigurableEventType> getAllConfigurableEventTypes(Long channelId) throws EntityNotFoundException {
		return channelTypeRepository.findAllConfigurableEventsOf(channelId);
	}

	@PreAuthorize("hasPermission(#channelId, 'ChannelType', 'OWNER') or hasRole('ADMIN')")
	public void removeChannel(Long channelId) {
		channelTypeRepository.deleteById(channelId);
	}
	
	@PreAuthorize("hasPermission(#userId, 'User', 'OWNER') or hasRole('ADMIN')")
	public void removeChannelFromUser(Long userId, Long channelId) throws EntityNotFoundException {
		User user = userService.loadUserByIdWithChannels(userId);
		user.getChannels().remove(this.loadById(channelId))
		userService.save(user);
	}

	@PreAuthorize("hasPermission(#channelId, 'ChannelType', 'OWNER') or hasRole('ADMIN')")
	public ChannelType loadById(Long channelId) throws EntityNotFoundException {
		return channelTypeRepository.findById(channelId)
				.orElseThrow(() -> new EntityNotFoundException(ChannelType.class, "id", channelId.toString()));
	}
	
	@PreAuthorize("hasPermission(#channelId, 'ChannelType', 'OWNER') or hasRole('ADMIN')")
	public ChannelType loadByIdWithConfigurableEvents(Long channelId) throws EntityNotFoundException {
		return channelTypeRepository.findByIdWithConfigurableEvents(channelId)
				.orElseThrow(() -> new EntityNotFoundException(ChannelType.class, "id", channelId.toString()));
	}

	public ChannelType save(ChannelType channel) {
		return channelTypeRepository.save(channel);
	}
}
