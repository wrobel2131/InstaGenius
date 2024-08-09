package com.instagenius.postmanagementservice.domain;

import com.instagenius.postmanagementservice.application.PostGenerationPort;
import com.instagenius.postmanagementservice.application.PostManagementUseCase;
import com.instagenius.postmanagementservice.application.PostPersistencePort;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
public class PostManagementService implements PostManagementUseCase {
    private final PostPersistencePort postPersistencePort;
    private final PostGenerationPort postGenerationPort;

    @Override
    public Post createPost(UUID userId, DescriptionGenerationOptions descriptionGenerationOptions, ImageGenerationOptions imageGenerationOptions, String title) {
        //Call post generation service for generated post (its in base64 string)
        GeneratedDescription generatedDescription = postGenerationPort.generateDescription(descriptionGenerationOptions);
        GeneratedImage generatedImage = postGenerationPort.generateImage(imageGenerationOptions);

        //Convert base64 string to byte[]

        //Save byte[] to s3 bucket and return url

        //Save post with url to image
        //TODO
        //
    }

    @Override
    public List<Post> getPosts() {
        return List.of();
    }

    @Override
    public Post getPostById() {
        return null;
    }

    @Override
    public Post updatePost() {
        return null;
    }

    @Override
    public void deletePost() {

    }
}
