package com.instagenius.postgenerationservice.infrastructure.config.openai;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ImageModelConfig {
    private String name;
    private List<String> qualities;
    private List<Size> sizes;
    private List<String> styles;
}
