package com.instagenius.paymentservice.infrastructure.adapter;


import com.instagenius.paymentservice.application.ProductManagementPort;
import com.instagenius.paymentservice.domain.Product;
import com.instagenius.paymentservice.infrastructure.dto.ProductIdsRequestDto;
import com.instagenius.paymentservice.infrastructure.exception.FeignExceptionUtils;
import com.instagenius.paymentservice.infrastructure.exception.ProductManagementException;
import com.instagenius.paymentservice.infrastructure.mapper.ProductRelatedMapper;
import com.instagenius.paymentservice.infrastructure.rest.ProductManagementClient;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ProductManagementAdapter implements ProductManagementPort {
    private final ProductManagementClient productManagementClient;
    private static final ProductRelatedMapper productRelatedMapper = ProductRelatedMapper.INSTANCE;


    @Override
    public List<Product> getProductsByIds(List<UUID> productIds) {
        try {
            return productManagementClient
                    .getProductsByIds(new ProductIdsRequestDto(productIds))
                    .products()
                    .stream()
                    .map(productRelatedMapper::toProduct)
                    .toList();
        } catch(FeignException e) {
            String errorMessage = FeignExceptionUtils.parseErrorResponse(e).message();

            throw new ProductManagementException(errorMessage, HttpStatus.valueOf(e.status()));
        }
    }
}
