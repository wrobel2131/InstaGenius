package com.instagenius.postgenerationservice.infrastructure.mapper;

import com.instagenius.postgenerationservice.infrastructure.dto.DescriptionGenerationOptionsDto;
import com.instagenius.postgenerationservice.domain.DescriptionGenerationOptions;
import com.instagenius.postgenerationservice.domain.vo.GPTModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper
public interface DescriptionGenerationOptionsMapper {
    DescriptionGenerationOptionsMapper INSTANCE = Mappers.getMapper(DescriptionGenerationOptionsMapper.class);

    @Mapping(source = "userPrompt", target = "userPrompt")
    @Mapping(source = "model", target = "model", qualifiedByName = "stringToGPTModel")
    DescriptionGenerationOptions toDescriptionGenerationOptions(
            DescriptionGenerationOptionsDto descriptionGenerationOptionsDto);


    @Named("stringToGPTModel")
    default GPTModel mapToGPTModel(String model) {
        return new GPTModel(model);
    }
}
