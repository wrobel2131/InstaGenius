package com.instagenius.postgenerationservice.infrastructure.config.openai;

import lombok.Data;

import java.util.List;

@Data
public class ImageModelConfig {
    private String name;
    private List<QualityConfig> qualities;
    private List<SizeConfig> sizes;
    private List<String> styles;
}
