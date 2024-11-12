package com.instagenius.productmanagementservice.domain;

import java.time.Instant;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class Product {
    private final UUID id;
    private String name;
    private String description;
    private ProductType type;
    private Price price;
    private final Instant createdAt;
    private final Instant updatedAt;
    private boolean isActive;
    private final int version;
    private Map<String, Object> attributes;

    public Product(
            UUID id, String name, String description, ProductType type, Price price, Instant createdAt,
            Instant updatedAt,
            boolean isActive, int version, Map<String, Object> attributes) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.type = type;
        this.price = price;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.isActive = isActive;
        this.version = version;
        this.attributes = attributes;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public ProductType getType() {
        return type;
    }

    public Price getPrice() {
        return price;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public boolean isActive() {
        return isActive;
    }

    public int getVersion() {
        return version;
    }

    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setType(ProductType type) {
        this.type = type;
    }

    public void setPrice(Price price) {
        this.price = price;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public void setAttributes(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return isActive == product.isActive && version == product.version && Objects.equals(id,
                                                                                            product.id) && Objects.equals(
                name, product.name) && Objects.equals(description,
                                                      product.description) && type == product.type && Objects.equals(
                price, product.price) && Objects.equals(createdAt, product.createdAt) && Objects.equals(
                updatedAt, product.updatedAt) && Objects.equals(attributes, product.attributes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, type, price, createdAt, updatedAt, isActive, version, attributes);
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", type=" + type +
                ", price=" + price +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", isActive=" + isActive +
                ", version=" + version +
                ", attributes=" + attributes +
                '}';
    }
}
