package com.ioteg.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.ioteg.model.ChannelType;
import com.ioteg.repositories.ChannelTypeRepository;
import com.ioteg.users.User;

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

	@PreAuthorize("hasPermission(#userId, 'User', 'OWNER')")
	public ChannelType createChannel(Long userId, ChannelType channel) {
		ChannelType storedChannel = channelTypeRepository.save(channel);

		User user = userService.loadUserByIdWithChannels(userId);
		user.getChannels().add(channel);
		userService.save(user);

		return storedChannel;
	}

	@PreAuthorize("hasPermission(#channelId, 'ChannelType', 'OWNER')")
	public ChannelType modifyChannel(Long channelId, ChannelType channel) throws ResourceNotFoundException {
		ChannelType storedChannel = channelTypeRepository.findById(channelId)
				.orElseThrow(() -> new ResourceNotFoundException("channel " + channelId, "Channel not found"));
		storedChannel.setChannelName(channel.getChannelName());
		return storedChannel;
	}

	@PreAuthorize("hasPermission(#channelId, 'ChannelType', 'OWNER')")
	public void removeChannel(Long channelId) {
		channelTypeRepository.deleteById(channelId);
	}

	@PreAuthorize("hasPermission(#channelId, 'ChannelType', 'OWNER')")
	public ChannelType loadById(Long channelId) throws ResourceNotFoundException {
		return channelTypeRepository.findById(channelId)
				.orElseThrow(() -> new ResourceNotFoundException("channel " + channelId, "Channel not found"));
	}

	public ChannelType save(ChannelType channel) {
		return channelTypeRepository.save(channel);
	}
}
