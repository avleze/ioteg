package com.ioteg.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.ioteg.model.Block;
import com.ioteg.model.EventType;
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

	@PreAuthorize("hasPermission(#eventTypeId, 'EventType', 'OWNER') or hasRole('ADMIN')")
	public EventType loadByIdWithBlocks(Long eventTypeId) throws EntityNotFoundException {
		return eventTypeRepository.findByIdWithBlocks(eventTypeId)
				.orElseThrow(() -> new EntityNotFoundException(EventType.class, "id", eventTypeId.toString()));

	}

	public void save(EventType eventType) {
		eventTypeRepository.save(eventType);
	}

	public EventType loadById(Long eventTypeId) throws EntityNotFoundException {
		return eventTypeRepository.findById(eventTypeId)
				.orElseThrow(() -> new EntityNotFoundException(EventType.class, "id", eventTypeId.toString()));
	}
}
