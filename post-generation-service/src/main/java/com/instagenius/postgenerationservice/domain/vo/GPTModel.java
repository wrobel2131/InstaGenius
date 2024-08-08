package com.instagenius.postgenerationservice.domain.vo;

import java.util.Set;

public record GPTModel(String model) {
    private static final Set<String> ALLOWED_MODELS = Set.of("gpt-4o", "gpt-4o-mini", "gpt-4");

    public GPTModel {
        if(!ALLOWED_MODELS.contains(model)) {
            throw new IllegalArgumentException("Invalid GPT model!");
        }
    }
}
