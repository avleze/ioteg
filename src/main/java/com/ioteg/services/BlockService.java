package com.ioteg.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.ioteg.model.Block;
import com.ioteg.model.EventType;
import com.ioteg.repositories.BlockRepository;
import com.ioteg.repositories.EventTypeRepository;

@Service
public class BlockService {

	private EventTypeRepository eventTypeRepository;
	private BlockRepository blockRepository;
	private UserService userService;

	/**
	 * @param eventTypeRepository
	 * @param blockRepository
	 * @param userService
	 */
	@Autowired
	public BlockService(EventTypeRepository eventTypeRepository, BlockRepository blockRepository,
			UserService userService) {
		super();
		this.eventTypeRepository = eventTypeRepository;
		this.blockRepository = blockRepository;
		this.userService = userService;
	}

	@PreAuthorize("hasPermission(#eventTypeId, 'EventType', 'OWNER') or hasRole('ADMIN')")
	public Block createBlock(Long eventTypeId, Block block) throws EntityNotFoundException {

		block.setOwner(userService.loadLoggedUser());
		Block storedBlock = blockRepository.save(block);
		EventType eventType = eventTypeRepository.findById(eventTypeId)
				.orElseThrow(() -> new EntityNotFoundException(EventType.class, "id", eventTypeId.toString()));
		eventType.getBlocks().add(storedBlock);
		eventTypeRepository.save(eventType);

		return storedBlock;
	}

	@PreAuthorize("hasPermission(#blockId, 'Block', 'OWNER') or hasRole('ADMIN')")
	public Block modifyBlock(Long blockId, Block block) throws EntityNotFoundException {
		Block storedBlock = this.loadById(blockId);

		storedBlock.setName(block.getName());
		storedBlock.setRepetition(block.getRepetition());
		storedBlock.setValue(block.getValue());

		return blockRepository.save(storedBlock);
	}

	@PreAuthorize("hasPermission(#blockId, 'Block', 'OWNER') or hasRole('ADMIN')")
	public void removeBlock(Long blockId) {
		blockRepository.deleteById(blockId);
	}

	@PreAuthorize("hasPermission(#blockId, 'Block', 'OWNER') or hasRole('ADMIN')")
	public Block loadById(Long blockId) throws EntityNotFoundException {
		return blockRepository.findById(blockId)
				.orElseThrow(() -> new EntityNotFoundException(Block.class, "id", blockId.toString()));
	}

	public Block save(Block block) {
		return blockRepository.save(block);
	}
}