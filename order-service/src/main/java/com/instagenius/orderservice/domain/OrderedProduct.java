package com.instagenius.orderservice.domain;

import java.util.UUID;

public record OrderedProduct(UUID id, ProductType productType, int quantity) {
}
