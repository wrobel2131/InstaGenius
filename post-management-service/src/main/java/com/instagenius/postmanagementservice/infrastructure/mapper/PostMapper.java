package com.instagenius.postmanagementservice.infrastructure.mapper;

import com.instagenius.postmanagementservice.domain.Post;
import com.instagenius.postmanagementservice.infrastructure.dto.CreatePostDto;
import com.instagenius.postmanagementservice.infrastructure.dto.CreatePostResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PostMapper {
    PostMapper INSTANCE = Mappers.getMapper(PostMapper.class);

    @Mapping(source = "title", target = "title")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "b64Image", target = "b64Image")
    CreatePostResponseDto toCreatePostDto(Post post);
}
