package com.instagenius.postgenerationservice.domain.vo;

import java.util.Set;

public record DALLEModel(String model) {
    private static final Set<String> ALLOWED_MODELS = Set.of("dall-e-2", "dall-e-3");

    public DALLEModel {
        if(!ALLOWED_MODELS.contains(model)) {
            throw new IllegalArgumentException("Invalid DALL-E model!");
        }
    }
}
