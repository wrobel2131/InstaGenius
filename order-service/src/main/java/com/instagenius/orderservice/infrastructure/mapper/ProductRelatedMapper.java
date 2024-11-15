package com.instagenius.orderservice.infrastructure.mapper;

import com.instagenius.orderservice.domain.Product;
import com.instagenius.orderservice.infrastructure.dto.ProductResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProductRelatedMapper {
    ProductRelatedMapper INSTANCE = Mappers.getMapper(ProductRelatedMapper.class);


    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "type", target = "type")
    @Mapping(target = "price", expression = "java(new Price(productResponseDto.price(), productResponseDto.currency()))")
    @Mapping(source = "attributes", target = "attributes")
    Product toProduct(ProductResponseDto productResponseDto);
}
