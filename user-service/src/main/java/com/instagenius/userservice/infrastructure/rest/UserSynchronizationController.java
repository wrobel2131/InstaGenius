package com.instagenius.userservice.infrastructure.rest;

import com.instagenius.userservice.application.UserSynchronizationUseCase;
import com.instagenius.userservice.infrastructure.dto.CreateUserRequestDto;
import com.instagenius.userservice.infrastructure.dto.CreatedUserResponseDto;
import com.instagenius.userservice.infrastructure.dto.SyncUpdateUserRequestDto;
import com.instagenius.userservice.infrastructure.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/sync/users")
@RequiredArgsConstructor
public class UserSynchronizationController {
    private static final UserMapper userMapper = UserMapper.INSTANCE;
    private final UserSynchronizationUseCase userSynchronizationUseCase;

    @PreAuthorize("hasRole('ROLE_EVENT_LISTENER')")
    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<CreatedUserResponseDto> createUser(@RequestBody CreateUserRequestDto createUserRequestDto) {
        return ResponseEntity.ok(
                userMapper.toCreatedUserResponseDto(
                        userSynchronizationUseCase.createUser(
                                userMapper.toCreateUser(createUserRequestDto)
                        )
                )
        );
    }

    @PreAuthorize("hasRole('ROLE_EVENT_LISTENER')")
    @PutMapping(value = "/{id}")
    ResponseEntity<Void> updateUser(@PathVariable("id") UUID id,
                                    @RequestBody SyncUpdateUserRequestDto syncUpdateUserRequestDto) {

        userSynchronizationUseCase.updateUser(id, userMapper.toSyncUpdateUser(syncUpdateUserRequestDto));

        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ROLE_EVENT_LISTENER')")
    @DeleteMapping(value = "/{id}")
    ResponseEntity<Void> deleteUser(@PathVariable("id") UUID id) {
        userSynchronizationUseCase.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
