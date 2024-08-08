package com.instagenius.postgenerationservice.infrastructure.mapper;
import com.instagenius.postgenerationservice.domain.GeneratedImage;
import com.instagenius.postgenerationservice.infrastructure.dto.GeneratedImageResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface GeneratedImageMapper {
    GeneratedImageMapper INSTANCE = Mappers.getMapper(GeneratedImageMapper.class);

    @Mapping(source = "image", target = "image")
    GeneratedImageResponseDto toGeneratedImageResponseDto(GeneratedImage generatedImage);
}
