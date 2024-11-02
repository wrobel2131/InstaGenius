package com.instagenius.postgenerationservice.infrastructure.config.openai;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "generation")
@Getter
@Setter
public class GenerationConfig {
    private DescriptionConfig description;
    private ImageConfig image;
}
