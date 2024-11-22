package com.instagenius.userservice.infrastructure.mapper;

import com.instagenius.userservice.domain.*;
import com.instagenius.userservice.infrastructure.adapter.UserEntity;
import com.instagenius.userservice.infrastructure.dto.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    User toUser(UserEntity userEntity);

    UserEntity toUserEntity(User user);

    UserResponseDto toUserResponseDto(User user);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "kcUserId", target = "kcUserId")
    @Mapping(source = "kcRealmId", target = "kcRealmId")
    CreatedUserResponseDto toCreatedUserResponseDto(CreatedUser createdUser);

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
    @Mapping(source = "username", target = "username")
    @Mapping(source = "firstName", target = "firstName")
    @Mapping(source = "lastName", target = "lastName")
    @Mapping(source = "emailVerified", target = "emailVerified")
    @Mapping(source = "enabled", target = "enabled")
    SyncUpdateUser toSyncUpdateUser(SyncUpdateUserRequestDto syncUpdateUserRequestDto);

    @Mapping(source = "email", target = "email")
    @Mapping(source = "username", target = "username")
    @Mapping(source = "firstName", target = "firstName")
    @Mapping(source = "lastName", target = "lastName")
    UpdateUser toUpdateUser(UpdateUserRequestDto updateUserRequestDto);

}
