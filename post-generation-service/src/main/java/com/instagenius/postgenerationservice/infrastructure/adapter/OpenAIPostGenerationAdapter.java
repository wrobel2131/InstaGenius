package com.instagenius.postgenerationservice.infrastructure.adapter;

import com.instagenius.postgenerationservice.application.PostGenerationOutputPort;
import com.instagenius.postgenerationservice.domain.DescriptionGenerationOptions;
import com.instagenius.postgenerationservice.domain.GeneratedDescription;
import com.instagenius.postgenerationservice.domain.GeneratedImage;
import com.instagenius.postgenerationservice.domain.ImageGenerationOptions;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.image.ImageModel;
import org.springframework.ai.image.ImagePrompt;
import org.springframework.ai.image.ImageResponse;
import org.springframework.ai.openai.OpenAiImageOptions;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Component
@RequiredArgsConstructor
class OpenAIPostGenerationAdapter implements PostGenerationOutputPort {
    private final ChatClient chatClient;
    private final ImageModel imageModel;
    private static final Integer NUMBER_OF_GENERATED_IMAGES = 1;

    @Override
    public GeneratedDescription generateDescription(DescriptionGenerationOptions descriptionGenerationOptions) {
        return chatClient
                .prompt()
                .user(descriptionGenerationOptions.userPrompt())
                .call()
                .entity(GeneratedDescription.class);
    }

    @Override
    public GeneratedImage generateImage(ImageGenerationOptions imageGenerationOptions) {
        OpenAiImageOptions.Builder imageOptionsBuilder = OpenAiImageOptions
                .builder()
                .withModel(imageGenerationOptions.model().model())
                .withHeight(imageGenerationOptions.size().height())
                .withWidth(imageGenerationOptions.size().width())
                .withResponseFormat("b64_json")
                .withN(NUMBER_OF_GENERATED_IMAGES);
        // for model DALL-E-2 generate image without style and quality params
        if(imageGenerationOptions.model().model().equals("dall-e-2")) {

            return new GeneratedImage(decodeBase64(imageModel.call(new ImagePrompt(imageGenerationOptions.userPrompt(), imageOptionsBuilder.build()
                    ))
                    .getResult()
                    .getOutput()
                    .getB64Json())
            );
        }

        return new GeneratedImage(decodeBase64(imageModel
                .call(
                        new ImagePrompt(imageGenerationOptions.userPrompt(), imageOptionsBuilder
                                .withStyle(imageGenerationOptions.style().style())
                                .withQuality(imageGenerationOptions.quality().quality())
                                .build()
                        )
                )
                .getResult()
                .getOutput()
                .getB64Json()));
    }

    private byte[] decodeBase64(String encoded) {
        return Base64.getDecoder().decode(encoded);
    }
}
