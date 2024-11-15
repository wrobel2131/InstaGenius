package com.instagenius.productmanagementservice.infrastructure.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import java.math.BigDecimal;
import java.util.Map;

public record CreateProductRequestDto(@NotNull(message = "Name is required!") String name,
                                      @NotNull(message = "Description is required!") String description,
                                      @NotNull(message = "Product type is required!") String type,
                                      @PositiveOrZero(message = "Price needs to be equal or greater than zero!") BigDecimal price,
                                      @NotNull(message = "Currency is required!") @Length(max = 3, min = 3, message =
                                              "Currency needs to be exactly 3 characters long!") String currency,
                                      Map<String, Object> attributes, @NotNull(message = "Image URL is required!")
                                      @URL(message = "Invalid image URL format!") String imageUrl) {
}
