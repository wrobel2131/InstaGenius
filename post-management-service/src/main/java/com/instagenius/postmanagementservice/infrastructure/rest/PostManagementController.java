package com.instagenius.postmanagementservice.infrastructure.rest;


import com.instagenius.postmanagementservice.application.PostManagementUseCase;
import com.instagenius.postmanagementservice.infrastructure.dto.CreatePostRequestDto;
import com.instagenius.postmanagementservice.infrastructure.dto.PostResponseDto;
import com.instagenius.postmanagementservice.infrastructure.dto.PostsResponseDto;
import com.instagenius.postmanagementservice.infrastructure.mapper.DescriptionGenerationOptionsMapper;
import com.instagenius.postmanagementservice.infrastructure.mapper.ImageGenerationOptionsMapper;
import com.instagenius.postmanagementservice.infrastructure.mapper.PostMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/post-management")
@RequiredArgsConstructor
public class PostManagementController {
    private final PostManagementUseCase postManagementUseCase;
    private static final DescriptionGenerationOptionsMapper descriptionGenerationOptionsMapper = DescriptionGenerationOptionsMapper.INSTANCE;
    private static final ImageGenerationOptionsMapper imageGenerationOptionsMapper = ImageGenerationOptionsMapper.INSTANCE;
    private static final PostMapper postMapper = PostMapper.INSTANCE;


    @GetMapping(value = "/users/{userId}/posts", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<PostsResponseDto> getAllPostsByUserId(@PathVariable("userId") UUID userId) {
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

    @GetMapping(value = "/users/{userId}/posts/{postId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<PostResponseDto> getPostByUserIdAndId(@PathVariable("userId") UUID userId, @PathVariable("postId") Long postId) {
        return ResponseEntity.ok(
                postMapper.toPostResponseDto(postManagementUseCase.getPostByUserIdAndId(userId, postId))
        );
    }


    @PostMapping(value = "/users/{userId}/posts/create-post", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<PostResponseDto> createPost(@PathVariable("userId") UUID userId, @Valid @RequestBody
    CreatePostRequestDto createPostRequestDto) {
        return ResponseEntity.ok(
                postMapper.toPostResponseDto(
                        postManagementUseCase.createPost(
                                userId,
                                descriptionGenerationOptionsMapper.toDescriptionGenerationOptions(createPostRequestDto.descriptionOptions()),
                                imageGenerationOptionsMapper.toImageGenerationOptions(createPostRequestDto.imageOptions()),
                                createPostRequestDto.title())
                                )
                );
    }

    @DeleteMapping(value = "/users/{userId}/posts/{postId}")
    ResponseEntity<Void> deletePost(@PathVariable("userId") UUID userId, @PathVariable("postId") Long postId) {

        postManagementUseCase.deletePost(userId, postId);
        return ResponseEntity.noContent().build();
    }




}
