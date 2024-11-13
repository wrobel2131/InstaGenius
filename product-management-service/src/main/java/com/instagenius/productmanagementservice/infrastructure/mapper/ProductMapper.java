package com.instagenius.productmanagementservice.infrastructure.mapper;

import com.instagenius.productmanagementservice.domain.Product;
import com.instagenius.productmanagementservice.infrastructure.adapter.ProductEntity;
import com.instagenius.productmanagementservice.infrastructure.dto.ProductResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProductMapper {
    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "type", target = "type")
    @Mapping(target = "price", expression = "java(new Price(productEntity.getPrice(), productEntity.getCurrency()))")
    @Mapping(source = "createdAt", target = "createdAt")
    @Mapping(source = "updatedAt", target = "updatedAt")
    @Mapping(source = "isActive", target = "isActive")
    @Mapping(source = "version", target = "version")
    @Mapping(source = "attributes", target = "attributes")
    @Mapping(source = "paymentGatewayProductParams", target = "paymentGatewayProductParams")
    Product toProduct(ProductEntity productEntity);


    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "type", target = "type")
    @Mapping(source = "price.price", target = "price")
    @Mapping(source = "price.currency", target = "currency")
    @Mapping(source = "createdAt", target = "createdAt")
    @Mapping(source = "updatedAt", target = "updatedAt", ignore = true)
    @Mapping(source = "isActive", target = "isActive")
    @Mapping(source = "version", target = "version")
    @Mapping(source = "attributes", target = "attributes")
    @Mapping(source = "paymentGatewayProductParams", target = "paymentGatewayProductParams")
    ProductEntity toProductEntity(Product product);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "type", target = "type")
    @Mapping(source = "price.price", target = "price")
    @Mapping(source = "price.currency", target = "currency")
    @Mapping(source = "attributes", target = "attributes")
    ProductResponseDto toProductResponseDto(Product product);
}
