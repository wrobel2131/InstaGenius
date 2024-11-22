package com.instagenius.userservice.infrastructure.rest;

import com.instagenius.userservice.application.UserUseCase;
import com.instagenius.userservice.infrastructure.dto.UpdateUserRequestDto;
import com.instagenius.userservice.infrastructure.dto.UserResponseDto;
import com.instagenius.userservice.infrastructure.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private static final UserMapper userMapper = UserMapper.INSTANCE;
    private final UserUseCase userUseCase;

    @GetMapping(value = "/profile", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<UserResponseDto> getUserProfile(@AuthenticationPrincipal Jwt jwt) {
        UUID userId = getUserUUIDFromJwtToken(jwt);
        return ResponseEntity.ok(
                userMapper.toUserResponseDto(
                        userUseCase.findUserById(userId)
                )
        );
    }

    @PatchMapping(value = "/profile", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<UserResponseDto> updateUserProfile(@AuthenticationPrincipal Jwt jwt, @RequestBody
    UpdateUserRequestDto updateUserRequestDto) {
        UUID userId = getUserUUIDFromJwtToken(jwt);
        return ResponseEntity.ok(
                userMapper.toUserResponseDto(
                        userUseCase.updateUser(userId, userMapper.toUpdateUser(updateUserRequestDto))
                )
        );
    }


    private UUID getUserUUIDFromJwtToken(Jwt jwt) {
        return UUID.fromString(jwt.getClaim("instagenius_user_id"));
    }
}
