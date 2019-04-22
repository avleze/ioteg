package com.ioteg.controllers.dto.mappers;

import org.mapstruct.Mapper;
import com.ioteg.controllers.dto.UserResponse;
import com.ioteg.model.User;

@Mapper(componentModel="spring")
public interface UserMapper {
    UserResponse userToUserResponse(User user);
}
