package com.instagenius.postgenerationservice.infrastructure.config.openai;

import lombok.Data;

import java.util.List;

@Data
public class ImageConfig {
    private List<ImageModelConfig> models;
}
