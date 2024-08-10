package com.instagenius.postmanagementservice.infrastructure.mapper;

import com.instagenius.postmanagementservice.domain.FileKeyName;
import com.instagenius.postmanagementservice.domain.GeneratedDescription;
import com.instagenius.postmanagementservice.domain.Post;
import com.instagenius.postmanagementservice.infrastructure.adapters.PostEntity;
import com.instagenius.postmanagementservice.infrastructure.dto.PostResponseDto;
import jdk.jfr.Name;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PostMapper {
    PostMapper INSTANCE = Mappers.getMapper(PostMapper.class);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "generatedDescription.description", target = "description")
    @Mapping(source = "generatedImage.b64Image", target = "b64Image")
    @Mapping(source = "title", target = "title")
    PostResponseDto toCreatePostDto(Post post);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "userId", target = "userId")
    @Mapping(source = "imageKeyName.keyName", target = "imageKeyName")
    @Mapping(source = "title", target = "title")
    @Mapping(source = "generatedDescription.description", target = "description")
    PostEntity toPostEntity(Post post);


    @Mapping(source = "id", target = "id")
    @Mapping(source = "userId", target = "userId")
    @Mapping(source = "title", target = "title")
    @Mapping(source = "imageKeyName", target = "imageKeyName", qualifiedByName = "stringToFileKeyName")
    @Mapping(source = "description", target = "generatedDescription", qualifiedByName = "stringToGeneratedDescription")
    @Mapping(source = "createdAt", target = "createdAt")
    @Mapping(source = "lastModified", target = "lastModified")
    Post toPost(PostEntity postEntity);


    @Named("stringToFileKeyName")
    default FileKeyName mapToFileKeyName(String imageKeyName) {
        return new FileKeyName(imageKeyName);
    }

    @Named("stringToGeneratedDescription")
    default GeneratedDescription mapToGeneratedDescription(String description) {
        return new GeneratedDescription(description);
    }

}
