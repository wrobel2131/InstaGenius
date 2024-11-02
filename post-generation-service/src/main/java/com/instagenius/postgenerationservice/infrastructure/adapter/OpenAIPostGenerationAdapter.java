package com.instagenius.postgenerationservice.infrastructure.adapter;

import com.instagenius.postgenerationservice.application.PostGenerationOutputPort;
import com.instagenius.postgenerationservice.domain.DescriptionGenerationOptions;
import com.instagenius.postgenerationservice.domain.GeneratedDescription;
import com.instagenius.postgenerationservice.domain.GeneratedImage;
import com.instagenius.postgenerationservice.domain.ImageGenerationOptions;
import com.instagenius.postgenerationservice.domain.ImageSize;
import com.instagenius.postgenerationservice.infrastructure.config.openai.GenerationConfig;
import com.instagenius.postgenerationservice.infrastructure.config.openai.ImageConfig;
import com.instagenius.postgenerationservice.infrastructure.config.openai.Size;
import com.instagenius.postgenerationservice.infrastructure.exception.InvalidGenerationOptionsException;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.image.ImageModel;
import org.springframework.ai.image.ImagePrompt;
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
        return new GeneratedDescription(chatClient
                .prompt()
                .user(descriptionGenerationOptions.userPrompt())
                .call()
                .content()
        );
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
            return new GeneratedImage(
                    imageModel.call(
                            new ImagePrompt(imageGenerationOptions.userPrompt(), imageOptionsBuilder.build()
                            )
                    )
                            .getResult()
                            .getOutput()
                            .getB64Json()
            );
        }

        return new GeneratedImage(imageModel
                .call(
                        new ImagePrompt(imageGenerationOptions.userPrompt(), imageOptionsBuilder
                                .withStyle(imageGenerationOptions.style().style())
                                .withQuality(imageGenerationOptions.quality().quality())
                                .build()
                        )
                )
                .getResult()
                .getOutput()
                .getB64Json());
    }

    private boolean validateDescriptionOptions(DescriptionGenerationOptions descriptionGenerationOptions) {
        return generationConfig.getDescription().getModels().contains(descriptionGenerationOptions.model().model());
    }

    private boolean validateImageOptions(ImageGenerationOptions imageGenerationOptions) {
        ImageConfig imageConfig = generationConfig.getImage();
        String generationOptionsModel = imageGenerationOptions.model().model();
        String generationOptionsImageQuality = imageGenerationOptions.quality().quality();
        String generationOptionsImageStyle = imageGenerationOptions.style().style();
        ImageSize generationOptionsImageSize = imageGenerationOptions.size();
        Size size = new Size(generationOptionsImageSize.width(), generationOptionsImageSize.height());

        boolean isModelValid = imageConfig
                .getModels()
                .stream()
                .anyMatch(m -> m.getName().equals(generationOptionsModel));
        System.out.println("Is model valid? " + isModelValid);
        if(!isModelValid) {
            return false;
        }

        List<String> qualities = imageConfig
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
                .getSizes().contains(size);
        System.out.println("Is size valid? " + isSizeValid);
        boolean isQualityValid = qualities != null && qualities.contains(generationOptionsImageQuality);

        System.out.println("Is quality valid? " + isQualityValid);
        boolean isStyleValid = styles != null && styles.contains(generationOptionsImageStyle);

        System.out.println("Is style valid? " + isStyleValid);
        return isSizeValid && isQualityValid && isStyleValid;
    }
}
