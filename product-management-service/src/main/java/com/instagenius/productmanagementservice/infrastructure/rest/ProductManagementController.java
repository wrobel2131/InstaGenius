package com.instagenius.productmanagementservice.infrastructure.rest;

import com.instagenius.productmanagementservice.application.ProductManagementUseCase;
import com.instagenius.productmanagementservice.domain.Price;
import com.instagenius.productmanagementservice.domain.ProductType;
import com.instagenius.productmanagementservice.infrastructure.dto.*;
import com.instagenius.productmanagementservice.infrastructure.mapper.ProductMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/products")
public class ProductManagementController {
    private final ProductManagementUseCase productManagementUseCase;
    private static final ProductMapper productMapper = ProductMapper.INSTANCE;

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<ProductsResponseDto> getActiveProducts(
            @RequestParam(required = false) ProductType type) {

        return ResponseEntity.ok(new ProductsResponseDto(productManagementUseCase
                                                                 .getActiveProducts(type)
                                                                 .stream()
                                                                 .map(productMapper::toProductResponseDto)
                                                                 .toList()));
    }

    @PostMapping(value = "/batch", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<ProductsResponseDto> getProductsByIds(
            @RequestBody ProductIdsRequestDto productIdsRequestDto) {
        return ResponseEntity.ok(new ProductsResponseDto(productManagementUseCase
                                                                 .getProductsByIds(productIdsRequestDto.productIds())
                                                                 .stream()
                                                                 .map(productMapper::toProductResponseDto)
                                                                 .toList()));
    }

    @GetMapping(value = "/{productId}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<ProductResponseDto> getProductById(
            @PathVariable("productId") UUID productId) {
        return ResponseEntity.ok(
                productMapper.toProductResponseDto(productManagementUseCase.getProductById(productId)));
    }


    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProductResponseDto> createProduct(
            @Valid @RequestBody CreateProductRequestDto createProductRequestDto) {

        return ResponseEntity.ok(
                productMapper.toProductResponseDto(
                        productManagementUseCase.createProduct(createProductRequestDto.name(),
                                                               createProductRequestDto.description(),
                                                               ProductType.valueOf(createProductRequestDto.type()),
                                                               new Price(createProductRequestDto.price(),
                                                                         createProductRequestDto.currency()),
                                                               createProductRequestDto.imageUrl(), createProductRequestDto.attributes())
                )
        );
    }

    @PutMapping(value = "/{productId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces =
            MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProductResponseDto> updateProduct(
            @PathVariable("productId") UUID productId,
            @Valid @RequestBody UpdateProductRequestDto updateProductRequestDto) {

        return ResponseEntity.ok(productMapper.toProductResponseDto(
                productManagementUseCase.updateProduct(productId, updateProductRequestDto.name(),
                                                       updateProductRequestDto.description(),
                                                       updateProductRequestDto.type(),
                                                       new Price(updateProductRequestDto.price(),
                                                                 updateProductRequestDto.currency()),
                                                       updateProductRequestDto.imageUrl(),
                                                       updateProductRequestDto.attributes(),
                                                       updateProductRequestDto.isActive())
        ));
    }

    @PutMapping(value = "/{productId}")
    public ResponseEntity<ProductResponseDto> deleteProduct(@PathVariable("productId") UUID productId) {
        productManagementUseCase.archiveProduct(productId);
        return ResponseEntity.ok(
                productMapper.toProductResponseDto(
                        productManagementUseCase.archiveProduct(productId)
                )
        );
    }
}
