package com.instagenius.productmanagementservice.domain;

import java.time.Instant;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class Product {
    private  UUID id;
    private String name;
    private String description;
    private ProductType type;
    private Price price;
    private Instant createdAt;
    private Instant updatedAt;
    private boolean active;
    private int version;
    private String imageUrl;
    private Map<String, Object> paymentGatewayProductParams;
    private Map<String, Object> attributes;

    public Product() {
    }

    public Product(UUID id, String name, String description, ProductType type, Price price, Instant createdAt,
            Instant updatedAt, boolean isActive, int version, String imageUrl, Map<String, Object> attributes, Map<String, Object> paymentGatewayProductParams) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.type = type;
        this.price = price;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.active = isActive;
        this.version = version;
        this.imageUrl = imageUrl;
        this.attributes = attributes;
        this.paymentGatewayProductParams = paymentGatewayProductParams;
    }

    public Product(String name, String description, ProductType type, Price price, Instant createdAt, Instant updatedAt,
            boolean isActive, int version, String imageUrl, Map<String, Object> attributes, Map<String, Object> paymentGatewayProductParams) {

        this(UUID.randomUUID(), name, description, type, price, createdAt, updatedAt, isActive, version, imageUrl, attributes,
                paymentGatewayProductParams);
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
        return active;
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
        this.active = active;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setAttributes(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    public Map<String, Object> getPaymentGatewayProductParams() {
        return paymentGatewayProductParams;
    }

    public void setPaymentGatewayProductParams(Map<String, Object> paymentGatewayProductParams) {
        this.paymentGatewayProductParams = paymentGatewayProductParams;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return active == product.active && version == product.version && Objects.equals(id,
                                                                                        product.id) && Objects.equals(
                name, product.name) && Objects.equals(description,
                                                      product.description) && type == product.type && Objects.equals(
                price, product.price) && Objects.equals(createdAt, product.createdAt) && Objects.equals(
                updatedAt, product.updatedAt) && Objects.equals(imageUrl,
                                                                product.imageUrl) && Objects.equals(
                paymentGatewayProductParams, product.paymentGatewayProductParams) && Objects.equals(attributes,
                                                                                                    product.attributes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, type, price, createdAt, updatedAt, active, version, imageUrl,
                            paymentGatewayProductParams, attributes);
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
                ", active=" + active +
                ", version=" + version +
                ", imageUrl='" + imageUrl + '\'' +
                ", paymentGatewayProductParams=" + paymentGatewayProductParams +
                ", attributes=" + attributes +
                '}';
    }
}
