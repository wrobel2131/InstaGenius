package com.instagenius.user.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UpdateUserDto {
    private String email;
    private String username;
    private String firstName;
    private String lastName;
    private Boolean emailVerified;
    private Boolean enabled;
}
