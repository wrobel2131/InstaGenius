package com.instagenius.postgenerationservice.infrastructure.mapper;

import com.instagenius.postgenerationservice.infrastructure.dto.ImageGenerationOptionsDto;
import com.instagenius.postgenerationservice.domain.vo.DALLEModel;
import com.instagenius.postgenerationservice.domain.ImageGenerationOptions;
import com.instagenius.postgenerationservice.domain.vo.ImageQuality;
import com.instagenius.postgenerationservice.domain.vo.ImageStyle;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ImageGenerationOptionsMapper {
    ImageGenerationOptionsMapper INSTANCE = Mappers.getMapper(ImageGenerationOptionsMapper.class);

    @Mapping(source = "userPrompt", target = "userPrompt")
    @Mapping(source = "model", target = "model", qualifiedByName = "stringToDALLEModel")
    @Mapping(source = "quality", target = "quality", qualifiedByName = "stringToImageQuality")
    @Mapping(source = "width", target = "size.width")
    @Mapping(source = "height", target = "size.height")
    @Mapping(source = "style", target = "style", qualifiedByName = "stringToImageStyle")
    ImageGenerationOptions toImageGenerationOptions(ImageGenerationOptionsDto imageGenerationOptionsDto);

    @Named("stringToDALLEModel")
    default DALLEModel mapToDALLEModel(String model) {
        return new DALLEModel(model);
    }

    @Named("stringToImageQuality")
    default ImageQuality mapToImageQuality(String quality) {
        return new ImageQuality(quality);
    }

    @Named("stringToImageStyle")
    default ImageStyle mapToImageStyle(String style) {
        return new ImageStyle(style);
    }
}
