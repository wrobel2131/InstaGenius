package com.instagenius.userservice.infrastructure.mapper;

import com.instagenius.userservice.domain.CreateUser;
import com.instagenius.userservice.domain.UpdateUser;
import com.instagenius.userservice.domain.User;
import com.instagenius.userservice.infrastructure.dto.CreateUserRequestDto;
import com.instagenius.userservice.infrastructure.dto.UpdateUserRequestDto;
import com.instagenius.userservice.infrastructure.dto.UserResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "realmId", target = "realmId")
    @Mapping(source = "username", target = "username")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "firstName", target = "firstName")
    @Mapping(source = "lastName", target = "lastName")
    @Mapping(source = "emailVerified", target = "emailVerified")
    @Mapping(source = "enabled", target = "enabled")
    @Mapping(source = "createdAt", target = "createdAt")
    CreateUser toCreateUser(CreateUserRequestDto createUserRequestDto);

    @Mapping(source = "email", target = "email")
    @Mapping(source = "firstName", target = "firstName")
    @Mapping(source = "lastName", target = "lastName")
    @Mapping(source = "emailVerified", target = "emailVerified")
    @Mapping(source = "enabled", target = "enabled")
    UpdateUser toUpdateUser(UpdateUserRequestDto updateUserRequestDto);

    //TODO: Add mapping for UserResponseDto
    UserResponseDto toUserResponseDto(User user);
}
