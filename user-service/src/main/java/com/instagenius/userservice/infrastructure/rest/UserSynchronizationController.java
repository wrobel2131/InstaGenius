package com.instagenius.userservice.infrastructure.rest;

import com.instagenius.userservice.application.UserSynchronizationUseCase;
import com.instagenius.userservice.infrastructure.dto.CreateUserRequestDto;
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
    public ResponseEntity<Void> createUser(@RequestBody CreateUserRequestDto createUserRequestDto) {
        System.out.println("creaitng user");
        userSynchronizationUseCase.createUser(
                userMapper.toCreateUser(createUserRequestDto)
        );
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ROLE_EVENT_LISTENER')")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") UUID id) {
        userSynchronizationUseCase.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
