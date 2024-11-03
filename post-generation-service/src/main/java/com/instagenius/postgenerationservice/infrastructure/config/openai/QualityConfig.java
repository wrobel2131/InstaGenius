package com.instagenius.postgenerationservice.infrastructure.config.openai;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@NoArgsConstructor
@Data
public class QualityConfig {
    private String name;
    private int cost;

    public QualityConfig(String name) {
        this.name = name;
        this.cost = 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QualityConfig that = (QualityConfig) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }
}
