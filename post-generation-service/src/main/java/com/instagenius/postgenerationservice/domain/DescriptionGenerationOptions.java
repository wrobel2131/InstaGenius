package com.instagenius.postgenerationservice.domain;

import com.instagenius.postgenerationservice.domain.vo.GPTModel;

public record DescriptionGenerationOptions(String userPrompt, GPTModel model) {
}
