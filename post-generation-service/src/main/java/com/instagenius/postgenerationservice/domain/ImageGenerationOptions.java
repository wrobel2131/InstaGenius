package com.instagenius.postgenerationservice.domain;

import com.instagenius.postgenerationservice.domain.vo.DALLEModel;
import com.instagenius.postgenerationservice.domain.vo.ImageQuality;
import com.instagenius.postgenerationservice.domain.vo.ImageSize;
import com.instagenius.postgenerationservice.domain.vo.ImageStyle;

public record ImageGenerationOptions(String userPrompt, DALLEModel model, ImageQuality quality, ImageSize size,
                                     ImageStyle style) {
}
