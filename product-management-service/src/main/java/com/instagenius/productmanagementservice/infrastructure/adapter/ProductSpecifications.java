package com.instagenius.productmanagementservice.infrastructure.adapter;

import com.instagenius.productmanagementservice.domain.ProductType;
import lombok.experimental.UtilityClass;
import org.springframework.data.jpa.domain.Specification;

import java.util.UUID;

@UtilityClass
public class ProductSpecifications {

    public Specification<ProductEntity> isActive(boolean isActive) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("isActive"), isActive);
    }

    public Specification<ProductEntity> hasType(ProductType productType) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("type"), productType);
    }

    public Specification<ProductEntity> hasId(UUID id) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("id"), id);
    }

}
