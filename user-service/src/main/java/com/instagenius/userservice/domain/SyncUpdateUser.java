package com.instagenius.userservice.domain;

public record UpdateUser(String email, String firstName, String lastName, Boolean emailVerified, Boolean enabled) {
}
