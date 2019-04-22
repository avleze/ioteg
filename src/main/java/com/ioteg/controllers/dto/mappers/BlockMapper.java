package com.ioteg.controllers.dto.mappers;

import org.mapstruct.Mapper;

import com.ioteg.controllers.dto.BlockRequest;
import com.ioteg.controllers.dto.BlockResponse;
import com.ioteg.model.Block;

@Mapper(componentModel = "spring")
public interface BlockMapper {
	
	BlockResponse blockToBlockResponse(Block block);
	Block blockRequestToBlock(BlockRequest block);
}
