package com.instagenius.postgenerationservice.domain;

import java.util.Arrays;

public record GeneratedImage(byte[] image) {
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GeneratedImage that = (GeneratedImage) o;
        return Arrays.equals(image, that.image);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(image);
    }

    @Override
    public String toString() {
        return "GeneratedImage{" +
                "image=" + Arrays.toString(image) +
                '}';
    }
}
