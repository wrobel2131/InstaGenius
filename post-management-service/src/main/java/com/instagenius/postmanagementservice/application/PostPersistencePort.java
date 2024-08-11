package com.instagenius.postmanagementservice.application;

import com.instagenius.postmanagementservice.domain.Post;

import java.util.List;
import java.util.UUID;

public interface PostPersistencePort {
    Post save(Post post);
    List<Post> getPostsByUserId(UUID userId);
    Post getPostByUserIdAndPostId(UUID userId, Long postId);
    void deletePostByUserIdAndPostId(UUID userId, Long postId);
}
