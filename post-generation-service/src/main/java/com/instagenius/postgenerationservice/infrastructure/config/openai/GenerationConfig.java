package com.instagenius.postgenerationservice.infrastructure.config.openai;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "generation")
@Data
public class GenerationConfig {
    private DescriptionConfig description;
    private ImageConfig image;
}
