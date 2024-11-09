package com.instagenius.coinmanagementservice.domain;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

public class CoinPackage {
        private final UUID id;
        private String name;
        private String description;
        private CoinAmount coinAmount;
        private Price price;
        private final Instant createdAt;
        private final Instant updatedAt;
        private final String type;
        private boolean active;
        private final int version;

    public CoinPackage(
            UUID id, String name, String description, CoinAmount coinAmount, Price price, Instant createdAt,
            Instant updatedAt, boolean active, int version) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.coinAmount = coinAmount;
        this.price = price;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.type = "COIN_PACKAGE";
        this.active = active;
        this.version = version;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCoinAmount(CoinAmount coinAmount) {
        this.coinAmount = coinAmount;
    }

    public void setPrice(Price price) {
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public void setActive(boolean active) {
        this.active = active;
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

    public CoinAmount getCoinAmount() {
        return coinAmount;
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

    @Override
    public String toString() {
        return "CoinPackage{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", coinAmount=" + coinAmount +
                ", price=" + price +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", isActive=" + active +
                ", version=" + version +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CoinPackage that = (CoinPackage) o;
        return active == that.active && version == that.version && Objects.equals(id,
                                                                                  that.id) && Objects.equals(
                name, that.name) && Objects.equals(description, that.description) && Objects.equals(
                coinAmount, that.coinAmount) && Objects.equals(price, that.price) && Objects.equals(
                createdAt, that.createdAt) && Objects.equals(updatedAt, that.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, coinAmount, price, createdAt, updatedAt, active, version);
    }
}
