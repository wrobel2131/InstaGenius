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
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
class PostManagementController {
    private final PostManagementUseCase postManagementUseCase;
    private static final DescriptionGenerationOptionsMapper descriptionGenerationOptionsMapper = DescriptionGenerationOptionsMapper.INSTANCE;
    private static final ImageGenerationOptionsMapper imageGenerationOptionsMapper = ImageGenerationOptionsMapper.INSTANCE;
    private static final PostMapper postMapper = PostMapper.INSTANCE;


    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<PostsResponseDto> getPosts(@AuthenticationPrincipal Jwt jwt) {
        UUID userId = getUserUUIDFromJwtToken(jwt);
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
    ResponseEntity<PostResponseDto> getPostByPostId(@PathVariable("postId") UUID postId, @AuthenticationPrincipal Jwt jwt) {
        UUID userId = getUserUUIDFromJwtToken(jwt);
        return ResponseEntity.ok(
                postMapper.toPostResponseDto(postManagementUseCase.getPostByUserIdAndPostId(userId, postId))
        );
    }


    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<PostResponseDto> createPost(@Valid @RequestBody CreatePostRequestDto createPostRequestDto, @AuthenticationPrincipal Jwt jwt) {
        UUID userId = getUserUUIDFromJwtToken(jwt);
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

    @DeleteMapping(value = "/{postId}")
    ResponseEntity<Void> deletePost(@PathVariable("postId") UUID postId, @AuthenticationPrincipal Jwt jwt) {
        UUID userId = getUserUUIDFromJwtToken(jwt);
        postManagementUseCase.deletePost(userId, postId);
        return ResponseEntity.noContent().build();
    }

    private UUID getUserUUIDFromJwtToken(Jwt jwt) {
        return UUID.fromString(jwt.getClaim("sub"));
    }
}
