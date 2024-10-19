package com.instagenius.coinmanagementservice.domain;

import java.math.BigDecimal;

public record Price(BigDecimal amount, String currency) {
}
