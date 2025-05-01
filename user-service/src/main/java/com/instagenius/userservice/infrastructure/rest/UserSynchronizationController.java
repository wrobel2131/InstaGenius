package com.instagenius.userservice.infrastructure.rest;

import com.instagenius.userservice.application.UserSynchronizationUseCase;
import com.instagenius.userservice.infrastructure.dto.CreateUserRequestDto;
import com.instagenius.userservice.infrastructure.dto.SyncUpdateUserRequestDto;
import com.instagenius.userservice.infrastructure.dto.UpdateUserRequestDto;
import com.instagenius.userservice.infrastructure.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/sync/users")
@RequiredArgsConstructor
@Slf4j
public class UserSynchronizationController {
    private static final UserMapper userMapper = UserMapper.INSTANCE;
    private final UserSynchronizationUseCase userSynchronizationUseCase;

    @PreAuthorize("hasRole('ROLE_USER_SYNC')")
    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createUser(@RequestBody CreateUserRequestDto createUserRequestDto) {
       log.info("creaitng user from kc");
        userSynchronizationUseCase.createUser(
                userMapper.toCreateUser(createUserRequestDto)
        );
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ROLE_USER_SYNC')")
    @PutMapping(value = "/{id}")
    public ResponseEntity<Void> updateUser(@PathVariable("id") UUID id,
            @RequestBody SyncUpdateUserRequestDto syncUpdateUserRequestDto) {
        log.info("updating user from kc");
        userSynchronizationUseCase.updateUser(id, userMapper.toSyncUpdateUser(syncUpdateUserRequestDto));
        return ResponseEntity.noContent().build();

    }

    @PreAuthorize("hasRole('ROLE_USER_SYNC')")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") UUID id) {
        log.info("deleting user from kc");
        userSynchronizationUseCase.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
