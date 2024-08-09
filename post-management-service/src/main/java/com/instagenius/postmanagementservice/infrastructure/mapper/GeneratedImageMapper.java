package com.instagenius.postmanagementservice.infrastructure.mapper;

import com.instagenius.postmanagementservice.domain.GeneratedImage;
import com.instagenius.postmanagementservice.infrastructure.dto.GeneratedImageDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface GeneratedImageMapper {
    GeneratedImageMapper INSTANCE = Mappers.getMapper(GeneratedImageMapper.class);

    @Mapping(source = "b64Image", target = "b64Image")
    GeneratedImage toGeneratedImage(GeneratedImageDto generatedImageDto);
}
