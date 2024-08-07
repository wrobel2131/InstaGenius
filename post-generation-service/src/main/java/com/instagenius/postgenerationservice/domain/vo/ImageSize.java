package com.instagenius.postgenerationservice.domain.vo;


import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public record ImageSize(Integer width, Integer height) {
    private static final Set<Entry<Integer, Integer>> ALLOWED_SIZES = Set.of(
            //Allowed sizes for DALL-E 2
            Map.entry(256, 256),
            Map.entry(512, 512),
            Map.entry(1024, 1024),

            //Allowed sizes for DALL-E 3
            Map.entry(1024, 1024),
            Map.entry(1792, 1024),
            Map.entry(1024, 1792)
            );

    public ImageSize {
        if(!ALLOWED_SIZES.contains(Map.entry(width, height))) {
            throw new IllegalArgumentException("Invalid image size.");
        }
    }
}
