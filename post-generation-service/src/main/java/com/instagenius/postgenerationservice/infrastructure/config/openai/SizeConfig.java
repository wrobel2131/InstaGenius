package com.instagenius.postgenerationservice.infrastructure.config.openai;

import lombok.*;

import java.util.Objects;


@NoArgsConstructor
@Data
public class SizeConfig {
    private int width;
    private int height;
    private int cost;

    public SizeConfig(int width, int height) {
        this.width = width;
        this.height = height;
        this.cost = 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SizeConfig that = (SizeConfig) o;
        return width == that.width && height == that.height;
    }

    @Override
    public int hashCode() {
        return Objects.hash(width, height);
    }
}
