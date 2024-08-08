package com.instagenius.postgenerationservice.infrastructure.dto;

import java.util.Arrays;

public record GeneratedImageResponseDto(byte[] image) {
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GeneratedImageResponseDto that = (GeneratedImageResponseDto) o;
        return Arrays.equals(image, that.image);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(image);
    }

    @Override
    public String toString() {
        return "GeneratedImageResponseDto{" +
                "image=" + Arrays.toString(image) +
                '}';
    }
}
