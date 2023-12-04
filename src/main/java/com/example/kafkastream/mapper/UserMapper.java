package com.example.kafkastream.mapper;

import com.example.kafkastream.dto.UserDto;
import com.example.kafkastream.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper( UserMapper.class );

    @Mapping(target = "username", source = "name")
    UserDto userToUserDto(User user);

    @Mapping(target = "name", source = "username")
    User userDtoToUser(UserDto user);
}
