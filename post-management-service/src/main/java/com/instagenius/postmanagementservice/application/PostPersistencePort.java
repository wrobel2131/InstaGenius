package com.instagenius.postmanagementservice.application;

import com.instagenius.postmanagementservice.domain.Post;

public interface PostPersistencePort {
    Post save(Post post);
}
