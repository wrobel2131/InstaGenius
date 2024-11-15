package com.instagenius.paymentservice.domain;

import java.math.BigDecimal;

public record Price(BigDecimal price, String currency) {
}
