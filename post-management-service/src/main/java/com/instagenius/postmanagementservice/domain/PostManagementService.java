package com.instagenius.postmanagementservice.domain;

import com.instagenius.postmanagementservice.application.FileStoragePort;
import com.instagenius.postmanagementservice.application.PostGenerationPort;
import com.instagenius.postmanagementservice.application.PostManagementUseCase;
import com.instagenius.postmanagementservice.application.PostPersistencePort;
import lombok.RequiredArgsConstructor;

import java.util.Base64;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
public class PostManagementService implements PostManagementUseCase {
    private final PostPersistencePort postPersistencePort;
    private final PostGenerationPort postGenerationPort;
    private final FileStoragePort fileStoragePort;

    @Override
    public Post createPost(UUID userId, DescriptionGenerationOptions descriptionGenerationOptions, ImageGenerationOptions imageGenerationOptions, String title) {
        //Call to generate description
        GeneratedDescription generatedDescription = postGenerationPort.generateDescription(descriptionGenerationOptions);
        //Call to generate image
        GeneratedImage generatedImage = postGenerationPort.generateImage(imageGenerationOptions);

        //Upload image to file  store
        FileKeyName imageKeyName = new FileKeyName(userId);
        fileStoragePort.uploadFile(imageKeyName, generatedImage);

        //Store generated post with keyname coresponding to file storage
        Post post = postPersistencePort.save(new Post(null, userId, title, imageKeyName, generatedImage, generatedDescription, null, null));
        post.setGeneratedImage(generatedImage);
        return post;
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
