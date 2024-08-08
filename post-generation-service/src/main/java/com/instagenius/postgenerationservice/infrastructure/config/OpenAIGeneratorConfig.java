package com.instagenius.postgenerationservice.infrastructure.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.image.ImageModel;
import org.springframework.ai.openai.OpenAiImageModel;
import org.springframework.ai.openai.api.OpenAiImageApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class OpenAIGeneratorConfig {

    @Bean
    ChatClient chatClient(ChatClient.Builder chatClientBuilder) {
        return chatClientBuilder
                .defaultSystem("") //Instruction how chat should respond, structure of the response, https://platform.openai.com/docs/guides/prompt-engineering/six-strategies-for-getting-better-results
//                .defaultAdvisors() //advisor, which will search in vector database based on the user query
                .build();
    }

    @Bean
    ImageModel imageModel(@Value("$spring.ai.openai.api-key") String openAIApiKey) {
        return new OpenAiImageModel(new OpenAiImageApi(openAIApiKey));
    }
}
