package com.ioteg.controllers.dto.mappers;

import org.mapstruct.Mapper;

import com.ioteg.controllers.dto.ChannelTypeRequest;
import com.ioteg.controllers.dto.ChannelTypeResponse;
import com.ioteg.model.ChannelType;

@Mapper(componentModel="spring")
public interface ChannelTypeMapper {
    ChannelTypeResponse channelTypeToChannelTypeResponse(ChannelType channelType);
    ChannelType channelTypeRequestToChannelType(ChannelTypeRequest channelType);
}
