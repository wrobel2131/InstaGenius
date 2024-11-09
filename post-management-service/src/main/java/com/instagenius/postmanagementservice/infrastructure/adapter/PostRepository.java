package com.instagenius.postmanagementservice.infrastructure.adapter;

import com.instagenius.postmanagementservice.application.PostPersistencePort;
import com.instagenius.postmanagementservice.domain.Post;
import com.instagenius.postmanagementservice.infrastructure.exception.PostNotFoundException;
import com.instagenius.postmanagementservice.infrastructure.mapper.PostMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
class PostRepository implements PostPersistencePort {
    private final JpaPostRepository jpaPostRepository;
    private static final PostMapper postMapper = PostMapper.INSTANCE;

    @Override
    public Post save(Post post) {
        return postMapper.toPost(jpaPostRepository.save(postMapper.toPostEntity(post)));
    }

    @Override
    public List<Post> getPostsByUserId(UUID userId) {
        return jpaPostRepository.findAllPostEntitiesByUserId(userId).stream().map(postMapper::toPost).toList();
    }

    @Override
    public Post getPostByUserIdAndPostId(UUID userId, UUID postId) {
        return postMapper.toPost(jpaPostRepository.findPostEntityByUserIdAndId(userId, postId).orElseThrow(
                () -> new PostNotFoundException("Post with id " + postId + " not found!")));
    }

    @Override
    public void deletePostByUserIdAndPostId(UUID userId, UUID postId) {
        jpaPostRepository.deletePostEntityByUserIdAndId(userId, postId);
    }
}

@Repository
interface JpaPostRepository extends JpaRepository<PostEntity, UUID> {

    @Query("SELECT p FROM PostEntity p WHERE p.userId = :userId AND p.id = :id")
    Optional<PostEntity> findPostEntityByUserIdAndId(@Param("userId") UUID userId, @Param("id") UUID id);

    @Query("SELECT p FROM PostEntity p WHERE p.userId = :userId")
    List<PostEntity> findAllPostEntitiesByUserId(@Param("userId") UUID userId);

    @Query("DELETE FROM PostEntity p WHERE p.userId = :userId AND p.id = :id")
    @Modifying
    void deletePostEntityByUserIdAndId(@Param("userId") UUID userId, @Param("id") UUID id);
}
