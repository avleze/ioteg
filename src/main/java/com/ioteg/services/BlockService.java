package com.ioteg.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ioteg.model.Block;
import com.ioteg.model.EventType;
import com.ioteg.repositories.BlockRepository;
import com.ioteg.repositories.EventTypeRepository;

@Service
public class BlockService {

	@Autowired
	private EventTypeRepository eventTypeRepository;
	
	@Autowired
	private BlockRepository blockRepository;

	public Block createBlock(Long eventTypeId,
			Block block) throws ResourceNotFoundException {
	
		Block storedBlock = blockRepository.save(block);
		EventType eventType = eventTypeRepository.findById(eventTypeId).orElseThrow(() -> new ResourceNotFoundException("EventType " + eventTypeId, "EventType not found."));
		eventType.getBlocks().add(storedBlock);
		eventTypeRepository.save(eventType);

		return storedBlock;
	}

	public Block modifyBlock(Long blockId, Block block) throws ResourceNotFoundException {
		Block storedBlock = blockRepository.findById(blockId).orElseThrow(() -> new ResourceNotFoundException("Block " + blockId, "Block not found."));
		
		storedBlock.setName(block.getName());
		storedBlock.setRepetition(block.getRepetition());
		storedBlock.setValue(block.getValue());
		
		return blockRepository.save(storedBlock);
	}

	public void removeBlock(Long blockId) {
		blockRepository.deleteById(blockId);
	}
	
	public Block loadById(Long blockId) throws ResourceNotFoundException {
		return blockRepository.findById(blockId).orElseThrow(() -> new ResourceNotFoundException("Block  " + blockId, "Block not found"));
	}
	
	public Block save(Block block) {
		return blockRepository.save(block);
	}
}
