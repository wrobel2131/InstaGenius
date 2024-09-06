package com.instagenius.postmanagementservice.infrastructure.mapper;

import com.instagenius.postmanagementservice.domain.GeneratedDescription;
import com.instagenius.postmanagementservice.infrastructure.dto.GeneratedDescriptionDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface GeneratedDescriptionMapper {
    GeneratedDescriptionMapper INSTANCE = Mappers.getMapper(GeneratedDescriptionMapper.class);

    @Mapping(source = "description", target = "description")
    GeneratedDescription toGeneratedDescription(GeneratedDescriptionDto generatedDescriptionDto);
}
