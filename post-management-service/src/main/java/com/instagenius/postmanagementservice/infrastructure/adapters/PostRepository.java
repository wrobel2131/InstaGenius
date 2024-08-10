package com.instagenius.postmanagementservice.infrastructure.adapters;

import com.instagenius.postmanagementservice.application.PostPersistencePort;
import com.instagenius.postmanagementservice.domain.Post;
import com.instagenius.postmanagementservice.infrastructure.mapper.PostMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PostRepository implements PostPersistencePort {
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
}

@Repository
interface JpaPostRepository extends JpaRepository<PostEntity, Long> {

}
