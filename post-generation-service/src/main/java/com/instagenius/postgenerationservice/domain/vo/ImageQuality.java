package com.instagenius.postgenerationservice.domain.vo;

import java.util.Set;

public record ImageQuality(String quality) {
    private static final Set<String> ALLOWED_QUALITIES = Set.of("hd", "standard");

    public ImageQuality {
        if(quality != null && !ALLOWED_QUALITIES.contains(quality)) {
            throw new IllegalArgumentException("Invalid b64Image quality!");
        }
    }
}
