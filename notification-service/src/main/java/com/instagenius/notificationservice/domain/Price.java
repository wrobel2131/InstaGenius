package com.instagenius.notificationservice.domain;

import java.math.BigDecimal;

public record Price(BigDecimal price, String currency) {
}
