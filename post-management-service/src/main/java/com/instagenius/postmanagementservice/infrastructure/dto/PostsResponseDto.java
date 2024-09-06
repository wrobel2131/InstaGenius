package com.instagenius.postmanagementservice.infrastructure.dto;

import java.util.List;

public record PostsResponseDto(List<PostResponseDto> posts) {
}
