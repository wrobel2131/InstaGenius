package com.instagenius.postmanagementservice.infrastructure.mapper;

import com.instagenius.postmanagementservice.domain.DescriptionGenerationOptions;
import com.instagenius.postmanagementservice.infrastructure.dto.CreateDescriptionDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface DescriptionGenerationOptionsMapper {
    DescriptionGenerationOptionsMapper INSTANCE = Mappers.getMapper(DescriptionGenerationOptionsMapper.class);

    @Mapping(source = "userPrompt", target = "userPrompt")
    @Mapping(source = "model", target = "model")
    CreateDescriptionDto toCreateDescriptionDto(DescriptionGenerationOptions descriptionGenerationOptions);

    @Mapping(source = "userPrompt", target = "userPrompt")
    @Mapping(source = "model", target = "model")
    DescriptionGenerationOptions toDescriptionGenerationOptions(CreateDescriptionDto createDescriptionDto);
}
