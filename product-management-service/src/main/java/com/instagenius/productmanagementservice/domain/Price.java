package com.instagenius.productmanagementservice.domain;

import java.math.BigDecimal;

public record Price(BigDecimal price, String currency) {
}
