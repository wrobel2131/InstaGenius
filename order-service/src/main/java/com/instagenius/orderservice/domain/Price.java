package com.instagenius.orderservice.domain;

import java.math.BigDecimal;

public record Price(BigDecimal price, String currency) {
}
