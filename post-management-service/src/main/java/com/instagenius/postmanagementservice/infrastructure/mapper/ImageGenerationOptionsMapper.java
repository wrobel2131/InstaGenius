package com.instagenius.postmanagementservice.infrastructure.mapper;

import com.instagenius.postmanagementservice.domain.ImageGenerationOptions;
import com.instagenius.postmanagementservice.infrastructure.dto.CreateImageDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ImageGenerationOptionsMapper {
    ImageGenerationOptionsMapper INSTANCE = Mappers.getMapper(ImageGenerationOptionsMapper.class);

    @Mapping(source = "userPrompt", target = "userPrompt")
    @Mapping(source = "model", target = "model")
    @Mapping(source = "quality", target = "quality")
    @Mapping(source = "width", target = "width")
    @Mapping(source = "height", target = "height")
    @Mapping(source = "style", target = "style")
    CreateImageDto toCreateImageDto(ImageGenerationOptions imageGenerationOptions);

    @Mapping(source = "userPrompt", target = "userPrompt")
    @Mapping(source = "model", target = "model")
    @Mapping(source = "quality", target = "quality")
    @Mapping(source = "width", target = "width")
    @Mapping(source = "height", target = "height")
    @Mapping(source = "style", target = "style")
    ImageGenerationOptions toImageGenerationOptions(CreateImageDto createImageDto);
}
