package com.instagenius.postmanagementservice.domain;

import java.time.Instant;
import java.util.Base64;
import java.util.UUID;

public class Post {
    private final UUID id;
    private final UUID userId;
    private final String title;
    private final FileKeyName imageKeyName;
    private GeneratedImage generatedImage;
    private final GeneratedDescription generatedDescription;
    private final Instant createdAt;
    private final Instant lastModified;

    public Post(UUID id, UUID userId, String title, FileKeyName imageKeyName, GeneratedImage generatedImage, GeneratedDescription generatedDescription, Instant createdAt, Instant lastModified) {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.imageKeyName = imageKeyName;
        this.generatedImage = generatedImage;
        this.generatedDescription = generatedDescription;
        this.createdAt = createdAt;
        this.lastModified = lastModified;
    }

    public void setGeneratedImageFromBytes(byte[] image) {
        this.generatedImage = new GeneratedImage(Base64.getEncoder().encodeToString(image));
    }

    public void setGeneratedImage(GeneratedImage generatedImage) {
        this.generatedImage = generatedImage;
    }

    public UUID getId() {
        return id;
    }

    public UUID getUserId() {
        return userId;
    }

    public String getTitle() {
        return title;
    }

    public FileKeyName getImageKeyName() {
        return imageKeyName;
    }

    public GeneratedImage getGeneratedImage() {
        return generatedImage;
    }

    public GeneratedDescription getGeneratedDescription() {
        return generatedDescription;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getLastModified() {
        return lastModified;
    }
}