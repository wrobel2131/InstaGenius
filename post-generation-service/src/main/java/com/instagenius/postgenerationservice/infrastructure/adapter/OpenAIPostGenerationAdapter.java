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
import org.springframework.ai.openai.OpenAiImageOptions;
import org.springframework.stereotype.Component;

import java.util.List;

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
            throw new InvalidGenerationOptionsException("Invalid description generation options!");
        }
        System.out.println("Description options are valid!");
        //TODO mocked response
        return new GeneratedDescription("Mocked description");
//        return new GeneratedDescription(chatClient
//                .prompt()
//                .user(descriptionGenerationOptions.userPrompt())
//                .call()
//                .content()
//        );
    }

    @Override
    public GeneratedImage generateImage(ImageGenerationOptions imageGenerationOptions) {
        if (!validateImageOptions(imageGenerationOptions)) {
            throw new InvalidGenerationOptionsException("Invalid image generation options!");
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
            //TODO mocked response
            return new GeneratedImage("some-b54-image");
//            return new GeneratedImage(
//                    imageModel.call(
//                            new ImagePrompt(imageGenerationOptions.userPrompt(), imageOptionsBuilder.build()
//                            )
//                    )
//                            .getResult()
//                            .getOutput()
//                            .getB64Json()
//            );
        }

        //TODO mocked response
        return new GeneratedImage("some-b54-image");
//        return new GeneratedImage(imageModel
//                .call(
//                        new ImagePrompt(imageGenerationOptions.userPrompt(), imageOptionsBuilder
//                                .withStyle(imageGenerationOptions.style().style())
//                                .withQuality(imageGenerationOptions.quality().quality())
//                                .build()
//                        )
//                )
//                .getResult()
//                .getOutput()
//                .getB64Json());
    }

    @Override
    public int calculateGenerationCost(DescriptionGenerationOptions descriptionGenerationOptions, ImageGenerationOptions imageGenerationOptions) {
        if(!validateImageOptions(imageGenerationOptions) || !validateDescriptionOptions(descriptionGenerationOptions)) {
            throw new InvalidGenerationOptionsException("Invalid image or description generation options!");
        }

        int descriptionGenerationCost = generationConfig
                .getDescription()
                .getModels()
                .stream()
                .filter(m -> m.getName().equals(descriptionGenerationOptions.model().model()))
                .findFirst()
                .get()
                .getCost();
        System.out.println("Description generation cost: " + descriptionGenerationCost);

        ImageModelConfig imageModelConfig = generationConfig
                .getImage()
                .getModels()
                .stream()
                .filter(m -> m.getName().equals(imageGenerationOptions.model().model()))
                .findFirst()
                .get();

        int imageGenerationSizeCost = imageModelConfig
                .getSizes()
                .stream()
                .filter(s -> s.equals(new SizeConfig(imageGenerationOptions.size().width(), imageGenerationOptions.size().height())))
                .findFirst()
                .get()
                .getCost();
        System.out.println("Image generation size cost: " + imageGenerationSizeCost);

        int imageGenerationQualityCost = imageModelConfig.getQualities() == null ? 0 :
                imageModelConfig
                        .getQualities()
                        .stream()
                        .filter(q -> q.equals(new QualityConfig(imageGenerationOptions.quality().quality())))
                        .findFirst()
                        .get()
                        .getCost();
        System.out.println("Image generation quality cost: " + imageGenerationQualityCost);

        return descriptionGenerationCost + imageGenerationSizeCost + imageGenerationQualityCost;
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
        System.out.println("Is model valid? " + isModelValid);
        if(!isModelValid) {
            return false;
        }

        List<QualityConfig> qualities = imageConfig
                .getModels()
                .stream()
                .filter(m -> m.getName().equals(generationOptionsModel))
                .findFirst()
                .get()
                .getQualities();

        List<String> styles = imageConfig
                .getModels()
                .stream()
                .filter(m -> m.getName().equals(generationOptionsModel))
                .findFirst()
                .get()
                .getStyles();

        boolean isSizeValid = imageConfig
                .getModels()
                .stream()
                .filter(m -> m.getName().equals(generationOptionsModel))
                .findFirst()
                .get()
                .getSizes().contains(sizeConfig);
        System.out.println("Is size valid? " + isSizeValid);
        boolean isQualityValid = qualities != null &&
                qualities
                .stream()
                        .map(QualityConfig::getName)
                        .toList()
                        .contains(generationOptionsImageQuality);

        System.out.println("Is quality valid? " + isQualityValid);
        boolean isStyleValid = styles != null && styles.contains(generationOptionsImageStyle);

        System.out.println("Is style valid? " + isStyleValid);
        return isSizeValid && isQualityValid && isStyleValid;
    }
}
