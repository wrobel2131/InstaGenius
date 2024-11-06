package com.instagenius.postgenerationservice.infrastructure.adapter;

import com.instagenius.postgenerationservice.application.PostGenerationOutputPort;
import com.instagenius.postgenerationservice.domain.DescriptionGenerationOptions;
import com.instagenius.postgenerationservice.domain.GeneratedDescription;
import com.instagenius.postgenerationservice.domain.GeneratedImage;
import com.instagenius.postgenerationservice.domain.ImageGenerationOptions;
import com.instagenius.postgenerationservice.domain.ImageSize;
import com.instagenius.postgenerationservice.infrastructure.config.openai.*;
import com.instagenius.postgenerationservice.infrastructure.exception.InvalidGenerationOptionsException;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.image.ImageModel;
import org.springframework.ai.image.ImagePrompt;
import org.springframework.ai.openai.OpenAiImageOptions;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
class OpenAIPostGenerationAdapter implements PostGenerationOutputPort {
    private final ChatClient chatClient;
    private final ImageModel imageModel;
    private static final Integer NUMBER_OF_GENERATED_IMAGES = 1;
    private final GenerationConfig generationConfig;

    @Override
    public GeneratedDescription generateDescription(DescriptionGenerationOptions descriptionGenerationOptions) {
        if (!validateDescriptionOptions(descriptionGenerationOptions)) {
            return null;
        }
        System.out.println("Description options are valid!");

        try {
            //TODO mocked response
            return new GeneratedDescription("Mocked description");
//            return new GeneratedDescription(chatClient
//                    .prompt()
//                    .user(descriptionGenerationOptions.userPrompt())
//                    .call()
//                    .content()
//            );
        } catch(Exception e) {
            return null;
        }

    }

    @Override
    public GeneratedImage generateImage(ImageGenerationOptions imageGenerationOptions) {
        if (!validateImageOptions(imageGenerationOptions)) {
            return null;
        }
        System.out.println("Image options are valid!");

        OpenAiImageOptions.Builder imageOptionsBuilder = OpenAiImageOptions
                .builder()
                .withModel(imageGenerationOptions.model().model())
                .withHeight(imageGenerationOptions.size().height())
                .withWidth(imageGenerationOptions.size().width())
                .withResponseFormat("b64_json")
                .withN(NUMBER_OF_GENERATED_IMAGES);
        // for model DALL-E-2 generate b64Image without style and quality params
        if(imageGenerationOptions.model().model().equals("dall-e-2")) {

            try {
                //TODO mocked response
                return new GeneratedImage("some-b54-image");
//                return new GeneratedImage(
//                        imageModel.call(
//                                        new ImagePrompt(imageGenerationOptions.userPrompt(), imageOptionsBuilder.build()
//                                        )
//                                )
//                                .getResult()
//                                .getOutput()
//                                .getB64Json()
//                );
            } catch(Exception e) {
                return null;
            }
        }


        try {
            //TODO mocked response
            return new GeneratedImage("some-b54-image");
//            return new GeneratedImage(imageModel
//                    .call(
//                            new ImagePrompt(imageGenerationOptions.userPrompt(), imageOptionsBuilder
//                                    .withStyle(imageGenerationOptions.style().style())
//                                    .withQuality(imageGenerationOptions.quality().quality())
//                                    .build()
//                            )
//                    )
//                    .getResult()
//                    .getOutput()
//                    .getB64Json());
        } catch(Exception e) {
            return null;
        }
    }

    @Override
    public int calculateGenerationCost(DescriptionGenerationOptions descriptionGenerationOptions, ImageGenerationOptions imageGenerationOptions) {
        if(!validateImageOptions(imageGenerationOptions) || !validateDescriptionOptions(descriptionGenerationOptions)) {
            throw new InvalidGenerationOptionsException("Invalid image or description generation options!");
        }
        Optional<ImageModelConfig> optionalImageModelConfig = generationConfig
                .getImage()
                .getModels()
                .stream()
                .filter(m -> m.getName().equals(imageGenerationOptions.model().model()))
                .findFirst();

        return calculateDescriptionGenerationCost(descriptionGenerationOptions)
                + calculateImageGenerationSizeCost(imageGenerationOptions, optionalImageModelConfig)
                + calculateImageGenerationQualityCost(imageGenerationOptions, optionalImageModelConfig);
    }

    private int calculateDescriptionGenerationCost(DescriptionGenerationOptions descriptionGenerationOptions) {
        Optional<DescriptionModelConfig> optionalDescriptionModelConfig = generationConfig
                .getDescription()
                .getModels()
                .stream()
                .filter(m -> m.getName().equals(descriptionGenerationOptions.model().model()))
                .findFirst();

        if(optionalDescriptionModelConfig.isEmpty()) {
            throw new InvalidGenerationOptionsException("Invalid image or description generation options!");
        }

        return optionalDescriptionModelConfig
                .get()
                .getCost();
    }

    private int calculateImageGenerationSizeCost(ImageGenerationOptions imageGenerationOptions, Optional<ImageModelConfig> optionalImageModelConfig) {
        if (optionalImageModelConfig.isEmpty()) {
            throw new InvalidGenerationOptionsException("Invalid image or description generation options!");
        }
        Optional<SizeConfig> optionalSizeConfig = optionalImageModelConfig
                .get()
                .getSizes()
                .stream()
                .filter(s -> s.equals(new SizeConfig(imageGenerationOptions.size().width(), imageGenerationOptions.size().height())))
                .findFirst();
        if (optionalSizeConfig.isEmpty()) {
            throw new InvalidGenerationOptionsException("Invalid image or description generation options!");
        }

        return optionalSizeConfig
                .get()
                .getCost();
    }

    private int calculateImageGenerationQualityCost(ImageGenerationOptions imageGenerationOptions, Optional<ImageModelConfig> optionalImageModelConfig) {
        if (optionalImageModelConfig.isEmpty()) {
            throw new InvalidGenerationOptionsException("Invalid image or description generation options!");
        }

        if(imageGenerationOptions.quality().quality() == null && optionalImageModelConfig.get().getQualities() == null) {
            System.out.println("Quality is required!");
            return 0;
        }

        Optional<QualityConfig> optionalQualityConfig =  optionalImageModelConfig
                .get()
                .getQualities()
                .stream()
                .filter(q -> q.equals(new QualityConfig(imageGenerationOptions.quality().quality())))
                .findFirst();

        return optionalQualityConfig
                .map(QualityConfig::getCost)
                .orElse(0);
    }

    private boolean validateDescriptionOptions(DescriptionGenerationOptions descriptionGenerationOptions) {
        return generationConfig
                .getDescription()
                .getModels()
                .stream()
                .map(DescriptionModelConfig::getName)
                .toList()
                .contains(descriptionGenerationOptions.model().model());
    }

    private boolean validateImageOptions(ImageGenerationOptions imageGenerationOptions) {
        ImageConfig imageConfig = generationConfig.getImage();
        String generationOptionsModel = imageGenerationOptions.model().model();
        String generationOptionsImageQuality = imageGenerationOptions.quality().quality();
        String generationOptionsImageStyle = imageGenerationOptions.style().style();
        ImageSize generationOptionsImageSize = imageGenerationOptions.size();
        SizeConfig sizeConfig = new SizeConfig(generationOptionsImageSize.width(), generationOptionsImageSize.height());

        boolean isModelValid = imageConfig
                .getModels()
                .stream()
                .anyMatch(m -> m.getName().equals(generationOptionsModel));
        System.out.println("isModelValid: " + isModelValid);
        if(!isModelValid) {
            return false;
        }
        Optional<ImageModelConfig> imageModelConfig = imageConfig
                .getModels()
                .stream()
                .filter(m -> m.getName().equals(generationOptionsModel))
                .findFirst();

        if(imageModelConfig.isEmpty()) {
            return false;
        }

        List<QualityConfig> qualities = imageModelConfig
                .get()
                .getQualities();

        List<String> styles = imageModelConfig
                .get()
                .getStyles();

        boolean isSizeValid = imageModelConfig
                .get()
                .getSizes().contains(sizeConfig);
        System.out.println("isSizeValid: " + isSizeValid);

        boolean isQualityValid = qualities == null && generationOptionsImageQuality == null || qualities != null &&
                qualities
                        .stream()
                        .map(QualityConfig::getName)
                        .toList()
                        .contains(generationOptionsImageQuality);
        System.out.println("isQualityValid: " + isQualityValid);

        boolean isStyleValid = styles == null && generationOptionsImageStyle == null ||
                styles != null && styles.contains(generationOptionsImageStyle);

        System.out.println("isStyleValid: " + isStyleValid);

        return isSizeValid && isQualityValid && isStyleValid;
    }
}
