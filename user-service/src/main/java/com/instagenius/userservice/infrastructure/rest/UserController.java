package com.instagenius.userservice.infrastructure.rest;

import com.instagenius.userservice.application.UserUseCase;
import com.instagenius.userservice.infrastructure.dto.UpdateUserRequestDto;
import com.instagenius.userservice.infrastructure.dto.UserResponseDto;
import com.instagenius.userservice.infrastructure.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@RestController
public class UserController {
    private static final UserMapper userMapper = UserMapper.INSTANCE;
    private final UserUseCase userUseCase;

    @GetMapping(value = "/{userId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserResponseDto> getUserProfile(@PathVariable("userId") UUID userId) {
        return ResponseEntity.ok(
                userMapper.toUserResponseDto(
                        userUseCase.findUserById(userId)
                )
        );
    }

    @PatchMapping(value = "/{userId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserResponseDto> updateUserProfile(@PathVariable("userId") UUID userId, @RequestBody UpdateUserRequestDto updateUserRequestDto) {
        System.out.println("userId = " + userId);
        return ResponseEntity.ok(
                userMapper.toUserResponseDto(
                        userUseCase.updateUser(userId, userMapper.toUpdateUser(updateUserRequestDto))
                )
        );
    }
}
