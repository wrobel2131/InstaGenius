package com.instagenius.paymentservice.infrastructure.mapper;


import com.instagenius.paymentservice.domain.OrderedProduct;
import com.instagenius.paymentservice.domain.Product;
import com.instagenius.paymentservice.infrastructure.dto.OrderedProductRequestDto;
import com.instagenius.paymentservice.infrastructure.dto.ProductResponseDto;
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
    @Mapping(source = "paymentGatewayProductParams", target = "paymentGatewayProductParams")
    @Mapping(source = "attributes", target = "attributes")
    Product toProduct(ProductResponseDto productResponseDto);

    @Mapping(source = "productId", target = "productId")
    @Mapping(source = "quantity", target = "quantity")
    OrderedProduct toOrderedProduct(OrderedProductRequestDto orderedProductRequestDto);

}
