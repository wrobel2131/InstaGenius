package com.instagenius.postmanagementservice.infrastructure.mapper;

import com.instagenius.postmanagementservice.domain.GenerationCost;
import com.instagenius.postmanagementservice.infrastructure.dto.GenerationCostDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface GenerationCostMapper {
    GenerationCostMapper INSTANCE = Mappers.getMapper(GenerationCostMapper.class);

    @Mapping(source = "coins", target = "coins")
    GenerationCostDto toGenerationCostDto(GenerationCost generationCost);

    @Mapping(source = "coins", target = "coins")
    GenerationCost toGenerationCost(GenerationCostDto generationCostDto);
}
