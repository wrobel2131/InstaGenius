package com.instagenius.userservice.infrastructure.rest;

import com.instagenius.userservice.application.UserUseCase;
import com.instagenius.userservice.infrastructure.dto.CreateUserRequestDto;
import com.instagenius.userservice.infrastructure.dto.UpdateUserRequestDto;
import com.instagenius.userservice.infrastructure.dto.UserResponseDto;
import com.instagenius.userservice.infrastructure.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private static final UserMapper userMapper = UserMapper.INSTANCE;
    private final UserUseCase userUseCase;


    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<UserResponseDto> createUser(@RequestBody CreateUserRequestDto createUserRequestDto) {

        return ResponseEntity.ok(userMapper.toUserResponseDto(
                userUseCase.createUser(userMapper.toCreateUser(createUserRequestDto)
                )
        ));
    }

    @PutMapping(value = "/{kcRealmId}/{kcUserId}", consumes = MediaType.APPLICATION_JSON_VALUE,produces =
            MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<UserResponseDto> updateUser(@PathVariable("kcRealmId") String kcRealmId,
                                               @PathVariable("kcUserId") String kcUserId,
                                               @RequestBody UpdateUserRequestDto updateUserRequestDto) {

        return ResponseEntity.ok(userMapper.toUserResponseDto(
                userUseCase.updateUser(kcRealmId, kcUserId, userMapper.toUpdateUser(updateUserRequestDto)
                )
        ));
    }

    @DeleteMapping(value = "/{kcRealmId}/{kcUserId}")
    ResponseEntity<Void> deleteUser(@PathVariable("kcRealmId") String kcRealmId,
                                   @PathVariable("kcUserId") String kcUserId) {
        userUseCase.deleteUser(kcRealmId, kcUserId);
        return ResponseEntity.noContent().build();
    }
}
