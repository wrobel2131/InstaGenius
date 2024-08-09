package com.instagenius.postmanagementservice.application;

import com.instagenius.postmanagementservice.domain.DescriptionGenerationOptions;
import com.instagenius.postmanagementservice.domain.ImageGenerationOptions;
import com.instagenius.postmanagementservice.domain.Post;

import java.util.List;
import java.util.UUID;

public interface PostManagementUseCase {
    Post createPost(UUID userId, DescriptionGenerationOptions descriptionGenerationOptions, ImageGenerationOptions imageGenerationOptions, String title);
    List<Post> getPosts();
    Post getPostById();
    Post updatePost();
    void deletePost();
    //TODO add pagination for get posts, add parameters to methods
}
