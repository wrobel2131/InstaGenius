package com.instagenius.postmanagementservice.infrastructure.rest;


import com.instagenius.postmanagementservice.application.PostManagementUseCase;
import com.instagenius.postmanagementservice.infrastructure.dto.CreatePostDto;
import com.instagenius.postmanagementservice.infrastructure.dto.PostResponseDto;
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

    @PostMapping(value = "/users/{userId}/create-post", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<PostResponseDto> createPost(@PathVariable("userId") UUID userId, @Valid @RequestBody CreatePostDto createPostDto) {
        return ResponseEntity.ok(
                postMapper.toCreatePostDto(
                        postManagementUseCase.createPost(
                                userId,
                                descriptionGenerationOptionsMapper.toDescriptionGenerationOptions(createPostDto.descriptionOptions()),
                                imageGenerationOptionsMapper.toImageGenerationOptions(createPostDto.imageOptions()),
                                createPostDto.title())
                                )
                );
    }
}
