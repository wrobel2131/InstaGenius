package com.instagenius.postgenerationservice.domain.vo;

import java.util.Set;

public record ImageStyle(String style) {
    private static final Set<String> ALLOWED_STYLES = Set.of("vivid", "natural");

    public ImageStyle {
        if(style != null && !ALLOWED_STYLES.contains(style)) {
            throw new IllegalArgumentException("Invalid image style!");
        }
    }
}
