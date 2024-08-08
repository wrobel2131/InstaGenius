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

    @Value("${spring.ai.openai.api-key}")
    private String openAIApiKey;

    @Bean
    public ChatClient chatClient(ChatClient.Builder chatClientBuilder) {
        return chatClientBuilder
                .defaultSystem(
                        """
                                You are a specialist in creating high-engagement Instagram posts. Your role is to generate professional captions that maximize reach, likes, and comments based on the user's input. Structure your responses as follows:
                        
                                1. Start with an attention-grabbing statement or question.
                                2. Provide context, share the main message, or tell a brief story related to the user’s input.
                                3. to Action: Encourage interaction, such as liking, commenting, or sharing.
                                4. Suggest relevant hashtags that boost discoverability.
                        
                                Ensure each section is crafted based on the specific input provided by the user. Match the tone to the brand, and vary the style to suit different campaign goals. Keep your suggestions clear, concise, and aimed at evoking strong engagement from followers.
                        """)
//                .defaultAdvisors() //advisor, which will search in vector database based on the user query
                .build();
    }

    @Bean
    public ImageModel imageModel() {
        return new OpenAiImageModel(new OpenAiImageApi(openAIApiKey));
    }
}
