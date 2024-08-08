package com.instagenius.postgenerationservice.infrastructure.mapper;

import com.instagenius.postgenerationservice.domain.GeneratedDescription;
import com.instagenius.postgenerationservice.infrastructure.dto.GeneratedDescriptionResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface GeneratedDescriptionMapper {
    GeneratedDescriptionMapper INSTANCE = Mappers.getMapper(GeneratedDescriptionMapper.class);

    @Mapping(source = "description", target = "description")
    GeneratedDescriptionResponseDto toGeneratedDescriptionResponseDto(GeneratedDescription generatedDescription);
}
