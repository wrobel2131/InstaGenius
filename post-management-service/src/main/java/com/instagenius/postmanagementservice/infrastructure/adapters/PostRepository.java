package com.instagenius.postmanagementservice.infrastructure.adapters;

import com.instagenius.postmanagementservice.application.PostPersistencePort;
import com.instagenius.postmanagementservice.domain.Post;
import com.instagenius.postmanagementservice.infrastructure.exception.PostNotFoundException;
import com.instagenius.postmanagementservice.infrastructure.mapper.PostMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
class PostRepository implements PostPersistencePort {
    private final JpaPostRepository jpaPostRepository;
    private final PostMapper postMapper = PostMapper.INSTANCE;

    @Override
    public Post save(Post post) {
        return postMapper.toPost(
                jpaPostRepository.save(
                        postMapper.toPostEntity(post)
                )
        );
    }

    @Override
    public List<Post> getPostsByUserId(UUID userId) {
        return jpaPostRepository
                .findAllByUserId(userId)
                .stream()
                .map(postMapper::toPost)
                .collect(Collectors.toList());
    }

    @Override
    public Post getPostByUserIdAndPostId(UUID userId, Long postId) {
        return postMapper.toPost(
                jpaPostRepository
                        .findByUserIdAndId(userId, postId)
                        .orElseThrow(() -> new PostNotFoundException("Post with given id do not exist"))
        );
    }

    @Override
    public void deletePostByUserIdAndPostId(UUID userId, Long postId) {
        jpaPostRepository.deleteByUserIdAndId(userId, postId);
    }
}

@Repository
interface JpaPostRepository extends JpaRepository<PostEntity, Long> {
    Optional<PostEntity> findByUserIdAndId(UUID userId, Long id);
    List<PostEntity> findAllByUserId(UUID userId);
    void deleteByUserIdAndId(UUID userId, Long id);
}
