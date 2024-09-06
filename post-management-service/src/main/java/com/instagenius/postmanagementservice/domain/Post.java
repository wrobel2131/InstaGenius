package com.instagenius.postmanagementservice.domain;

import java.time.LocalDateTime;
import java.util.Base64;
import java.util.UUID;

public class Post {
    private final Long id;
    private final UUID userId;
    private final String title;
    private final FileKeyName imageKeyName;
    private GeneratedImage generatedImage;
    private final GeneratedDescription generatedDescription;
    private final LocalDateTime createdAt;
    private final LocalDateTime lastModified;

    public Post(Long id, UUID userId, String title, FileKeyName imageKeyName, GeneratedImage generatedImage, GeneratedDescription generatedDescription, LocalDateTime createdAt, LocalDateTime lastModified) {
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

    public Long getId() {
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getLastModified() {
        return lastModified;
    }
}