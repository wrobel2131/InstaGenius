package com.instagenius.orderservice.infrastructure.mapper;

import com.instagenius.orderservice.domain.UserProfile;
import com.instagenius.orderservice.infrastructure.dto.UserResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserRelatedMapper {
    UserRelatedMapper INSTANCE = Mappers.getMapper(UserRelatedMapper.class);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "username", target = "username")
    @Mapping(source = "firstName", target = "firstName")
    @Mapping(source = "lastName", target = "lastName")
    UserProfile toUserProfile(UserResponseDto userResponseDto);
}
