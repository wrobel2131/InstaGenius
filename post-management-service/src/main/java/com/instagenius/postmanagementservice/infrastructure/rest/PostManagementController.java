package com.instagenius.postmanagementservice.infrastructure.rest;


import com.instagenius.postmanagementservice.application.PostManagementUseCase;
import com.instagenius.postmanagementservice.domain.Post;
import com.instagenius.postmanagementservice.infrastructure.dto.CreatePostRequestDto;
import com.instagenius.postmanagementservice.infrastructure.dto.PostResponseDto;
import com.instagenius.postmanagementservice.infrastructure.dto.PostsResponseDto;
import com.instagenius.postmanagementservice.infrastructure.mapper.DescriptionGenerationOptionsMapper;
import com.instagenius.postmanagementservice.infrastructure.mapper.ImageGenerationOptionsMapper;
import com.instagenius.postmanagementservice.infrastructure.mapper.PostMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
@Slf4j
public class PostManagementController {
    private final PostManagementUseCase postManagementUseCase;
    private static final DescriptionGenerationOptionsMapper descriptionGenerationOptionsMapper = DescriptionGenerationOptionsMapper.INSTANCE;
    private static final ImageGenerationOptionsMapper imageGenerationOptionsMapper = ImageGenerationOptionsMapper.INSTANCE;
    private static final PostMapper postMapper = PostMapper.INSTANCE;


    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<PostsResponseDto> getAllPostsByUserId() {
        UUID userId = UUID.randomUUID(); //TODO get UUID form TOKEN
        return ResponseEntity.ok(
                new PostsResponseDto(
                        postManagementUseCase
                                .getPostsByUserId(userId)
                                .stream()
                                .map(postMapper::toPostResponseDto)
                                .toList()
                )
        );
    }

    @GetMapping(value = "/{postId}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<PostResponseDto> getPostByUserIdAndId(@PathVariable("postId") Long postId) {
        UUID userId = UUID.randomUUID(); //TODO get UUID form TOKEN
        return ResponseEntity.ok(
                postMapper.toPostResponseDto(postManagementUseCase.getPostByUserIdAndId(userId, postId))
        );
    }


    @PostMapping(value = "/create-post", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<PostResponseDto> createPost(@Valid @RequestBody CreatePostRequestDto createPostRequestDto) {
        UUID userId = UUID.randomUUID(); //TODO get UUID form TOKEN
        Post post =  postManagementUseCase.createPost(
                userId,
                descriptionGenerationOptionsMapper.toDescriptionGenerationOptions(createPostRequestDto.descriptionOptions()),
                imageGenerationOptionsMapper.toImageGenerationOptions(createPostRequestDto.imageOptions()),
                createPostRequestDto.title()
        );
        return ResponseEntity.ok(
                postMapper.toPostResponseDto(
                       post
                )
        );
    }

    @DeleteMapping(value = "/posts/{postId}")
    ResponseEntity<Void> deletePost(@PathVariable("postId") Long postId) {
        UUID userId = UUID.randomUUID(); //TODO get UUID form TOKEN
        postManagementUseCase.deletePost(userId, postId);
        return ResponseEntity.noContent().build();
    }
}
