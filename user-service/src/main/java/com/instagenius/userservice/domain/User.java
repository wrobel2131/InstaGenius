package com.instagenius.userservice.domain;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

public class User {
    private UUID id;
    private String kcUserId;
    private String kcRealmId;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private boolean emailVerified;
    private boolean enabled;
    private Instant createdAt;
    private Instant updatedAt;
    private int version;

    User(UUID id, String kcUserId, String kcRealmId, String username, String email, String firstName, String lastName,
         boolean emailVerified, boolean enabled, Long createdAt) {
        this(id, kcUserId, kcRealmId, username, email, firstName, lastName, emailVerified, enabled,
                Instant.ofEpochMilli(createdAt), null, 0);
    }

    User(UUID id, String kcUserId, String kcRealmId, String username, String email, String firstName, String lastName,
         boolean emailVerified, boolean enabled, Instant createdAt, Instant updatedAt, int version) {
        this.id = id;
        this.kcUserId = kcUserId;
        this.kcRealmId = kcRealmId;
        this.username = username;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailVerified = emailVerified;
        this.enabled = enabled;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.version = version;
    }

    UUID getId() {
        return id;
    }

    void setId(UUID id) {
        this.id = id;
    }

    String getKcUserId() {
        return kcUserId;
    }

    void setKcUserId(String kcUserId) {
        this.kcUserId = kcUserId;
    }

    String getKcRealmId() {
        return kcRealmId;
    }

    void setKcRealmId(String kcRealmId) {
        this.kcRealmId = kcRealmId;
    }

    String getUsername() {
        return username;
    }

    void setUsername(String username) {
        this.username = username;
    }

    String getEmail() {
        return email;
    }

    void setEmail(String email) {
        this.email = email;
    }

    String getFirstName() {
        return firstName;
    }

    void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    String getLastName() {
        return lastName;
    }

    void setLastName(String lastName) {
        this.lastName = lastName;
    }

    boolean isEmailVerified() {
        return emailVerified;
    }

    void setEmailVerified(boolean emailVerified) {
        this.emailVerified = emailVerified;
    }

    boolean isEnabled() {
        return enabled;
    }

    void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    Instant getCreatedAt() {
        return createdAt;
    }

    void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    Instant getUpdatedAt() {
        return updatedAt;
    }

    void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    int getVersion() {
        return version;
    }

    void setVersion(int version) {
        this.version = version;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return emailVerified == user.emailVerified && enabled == user.enabled && version == user.version &&
                Objects.equals(id, user.id) && Objects.equals(kcUserId, user.kcUserId) &&
                Objects.equals(kcRealmId, user.kcRealmId) && Objects.equals(username, user.username) &&
                Objects.equals(email, user.email) && Objects.equals(firstName, user.firstName) &&
                Objects.equals(lastName, user.lastName) && Objects.equals(createdAt, user.createdAt) &&
                Objects.equals(updatedAt, user.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, kcUserId, kcRealmId, username, email, firstName, lastName, emailVerified, enabled,
                createdAt, updatedAt, version);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", kcUserId='" + kcUserId + '\'' +
                ", kcRealmId='" + kcRealmId + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", emailVerified=" + emailVerified +
                ", enabled=" + enabled +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", version=" + version +
                '}';
    }
}
