package com.instagenius.postgenerationservice.infrastructure.config.openai;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ImageConfig {
    private List<ImageModelConfig> models;
}
