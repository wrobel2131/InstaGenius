package com.instagenius.productmanagementservice.infrastructure.rest;

import com.instagenius.productmanagementservice.application.ProductManagementUseCase;
import com.instagenius.productmanagementservice.domain.Price;
import com.instagenius.productmanagementservice.domain.Product;
import com.instagenius.productmanagementservice.domain.ProductType;
import com.instagenius.productmanagementservice.infrastructure.dto.CreateProductRequest;
import com.instagenius.productmanagementservice.infrastructure.dto.ProductResponse;
import com.instagenius.productmanagementservice.infrastructure.dto.ProductsResponse;
import com.instagenius.productmanagementservice.infrastructure.dto.UpdateProductRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping ("/api/v1/products")
public class ProductManagementController {
    private final ProductManagementUseCase productManagementUseCase;

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<ProductsResponse> getProducts(@AuthenticationPrincipal Jwt jwt) {
        List<ProductResponse> products = productManagementUseCase.getProducts().stream().map().toList();

        return ResponseEntity.ok(new ProductsResponse(products));
    }

    @GetMapping(value = "/{productId}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<ProductResponse> getProductById(@PathVariable("productId") UUID productId,
                                                   @AuthenticationPrincipal Jwt jwt) {
        ProductResponse productResponse = productManagementUseCase.getProductById(productId);

        return ResponseEntity.ok();
    }


    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProductResponse> createProduct(@AuthenticationPrincipal Jwt jwt,
                                                         @Valid @RequestBody CreateProductRequest createProductRequest) {
        ProductResponse productResponse = productManagementUseCase.createProduct(createProductRequest.name(),
                createProductRequest.description(),
                ProductType.valueOf(createProductRequest.type()), new Price(createProductRequest.price(), createProductRequest.currency())
                , createProductRequest.attributes());

        return ResponseEntity.ok(productResponse);
    }

    @PutMapping(value = "/{productId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces =
            MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProductResponse> updateProduct(@PathVariable("productId") UUID productId,
                                                         @AuthenticationPrincipal Jwt jwt,
                                                         @Valid @RequestBody UpdateProductRequest updateProductRequest) {
        Product updatedProduct = productManagementUseCase.updateProduct(productId, updateProductRequest.name(),
                updateProductRequest.description(), ProductType.valueOf(updateProductRequest.type()),
                new Price(updateProductRequest.price(), updateProductRequest.currency()),
                updateProductRequest.attributes(), updateProductRequest.isActive());

        return ResponseEntity.ok(updatedProduct);
    }

    @DeleteMapping(value = "/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable("productId") UUID productId) {
        productManagementUseCase.deleteProduct(productId);
        return ResponseEntity.noContent().build();
    }

    private UUID getUserUUIDFromJwtToken(Jwt jwt) {
        return UUID.fromString(jwt.getClaim("sub"));
    }
}
