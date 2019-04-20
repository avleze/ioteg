package com.ioteg.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ioteg.model.ChannelType;
import com.ioteg.repositories.ChannelTypeRepository;
import com.ioteg.users.User;

@Service
public class ChannelTypeService {

	@Autowired
	private UserService userService;
	
	@Autowired
	private ChannelTypeRepository channelTypeRepository;
	
	
	public ChannelType createChannel(Long userId, ChannelType channel) {
		ChannelType storedChannel = channelTypeRepository.save(channel);
		
		User user = userService.loadUserByIdWithChannels(userId);
		user.getChannels().add(channel);
		userService.save(user);
		
		return storedChannel;
	}
	
	public ChannelType modifyChannel(Long channelId, ChannelType channel) throws ResourceNotFoundException {
		ChannelType storedChannel = channelTypeRepository.findById(channelId).orElseThrow(() -> new ResourceNotFoundException("channel " + channelId, "Channel not found"));
		storedChannel.setChannelName(channel.getChannelName());
		return storedChannel;
	}
	
	public void removeChannel(Long channelId) {
		channelTypeRepository.deleteById(channelId);
	}
	
	public ChannelType loadById(Long channelId) throws ResourceNotFoundException {
		return channelTypeRepository.findById(channelId).orElseThrow(() -> new ResourceNotFoundException("channel " + channelId, "Channel not found"));
	}
	
	public ChannelType save(ChannelType channel) {
		return channelTypeRepository.save(channel);
	}
}
