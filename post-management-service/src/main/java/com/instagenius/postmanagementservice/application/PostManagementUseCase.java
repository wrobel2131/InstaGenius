package com.instagenius.postmanagementservice.application;

import com.instagenius.postmanagementservice.domain.Post;

import java.util.List;

public interface PostManagementUseCase {
    Post createPost();
    List<Post> getPostsByUserId();
    Post getPostByIdAndUserId();
    Post updatePost();
    void deletePost();
    //TODO add pagination for get posts, add parameters to methods
}
