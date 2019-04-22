package com.ioteg.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.ioteg.model.Block;
import com.ioteg.repositories.EventTypeRepository;

@Service
public class EventTypeService {
	private EventTypeRepository eventTypeRepository;

	/**
	 * @param eventTypeRepository
	 */
	@Autowired
	public EventTypeService(EventTypeRepository eventTypeRepository) {
		super();
		this.eventTypeRepository = eventTypeRepository;
	}
	
	@PreAuthorize("hasPermission(#eventTypeId, 'EventType', 'OWNER') or hasRole('ADMIN')")
	public List<Block> getAllBlocks(Long eventTypeId) {
		return eventTypeRepository.findAllBlocksOf(eventTypeId);
	}
	
}
