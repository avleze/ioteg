package com.ioteg.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.ioteg.model.Block;
import com.ioteg.model.EventType;
import com.ioteg.repositories.BlockRepository;

@Service
public class BlockService {

	private EventTypeService eventTypeService;
	private BlockRepository blockRepository;
	private UserService userService;

	/**
	 * @param eventTypeRepository
	 * @param blockRepository
	 * @param userService
	 */
	@Autowired
	public BlockService(EventTypeService eventTypeRepository, BlockRepository blockRepository,
			UserService userService) {
		super();
		this.eventTypeService = eventTypeRepository;
		this.blockRepository = blockRepository;
		this.userService = userService;
	}

	@PreAuthorize("hasPermission(#eventTypeId, 'EventType', 'OWNER') or hasRole('ADMIN')")
	public Block createBlock(Long eventTypeId, Block block) throws EntityNotFoundException {

		block.setOwner(userService.loadLoggedUser());
		Block storedBlock = blockRepository.save(block);
		EventType eventType = eventTypeService.loadById(eventTypeId);
		eventType.getBlocks().add(storedBlock);
		eventTypeService.save(eventType);

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

	@PreAuthorize("hasPermission(#eventTypeId, 'EventType', 'OWNER') or hasRole('ADMIN')")
	public void removeBlockFromEventType(Long eventTypeId, Long blockId) throws EntityNotFoundException {
		eventTypeService.loadByIdWithBlocks(eventTypeId)
				.getBlocks().remove(this.loadById(blockId));
	}

	@PreAuthorize("hasPermission(#blockId, 'Block', 'OWNER') or hasRole('ADMIN')")
	public Block loadById(Long blockId) throws EntityNotFoundException {
		return blockRepository.findById(blockId)
				.orElseThrow(() -> new EntityNotFoundException(Block.class, "id", blockId.toString()));
	}
	
	@PreAuthorize("hasPermission(#blockId, 'Block', 'OWNER') or hasRole('ADMIN')")
	public Block loadByIdWithOptionalFields(Long blockId) throws EntityNotFoundException {
		return blockRepository.findByIdWithOptionalFields(blockId)
				.orElseThrow(() -> new EntityNotFoundException(Block.class, "id", blockId.toString()));
	}
	
	@PreAuthorize("hasPermission(#blockId, 'Block', 'OWNER') or hasRole('ADMIN')")
	public Block loadByIdWithFields(Long blockId) throws EntityNotFoundException {
		return blockRepository.findByIdWithFields(blockId)
				.orElseThrow(() -> new EntityNotFoundException(Block.class, "id", blockId.toString()));
	}
	
	@PreAuthorize("hasPermission(#blockId, 'Block', 'OWNER') or hasRole('ADMIN')")
	public Block loadByIdWithInjectedFields(Long blockId) throws EntityNotFoundException {
		return blockRepository.findByIdWithInjectedFields(blockId)
				.orElseThrow(() -> new EntityNotFoundException(Block.class, "id", blockId.toString()));
	}

	public Block save(Block block) {
		return blockRepository.save(block);
	}
}
