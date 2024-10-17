package com.instagenius.coinmanagementservice.domain;

import java.math.BigDecimal;

public record Price(BigDecimal price, String currency) {
}
