package com.instagenius.productmanagementservice.infrastructure.adapter;

import com.instagenius.productmanagementservice.application.ProductPersistencePort;
import com.instagenius.productmanagementservice.domain.Product;
import com.instagenius.productmanagementservice.domain.ProductType;
import com.instagenius.productmanagementservice.infrastructure.exception.ProductNotFoundException;
import com.instagenius.productmanagementservice.infrastructure.mapper.ProductMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class ProductRepository implements ProductPersistencePort {
    private final JpaProductRepository jpaProductRepository;
    private static final ProductMapper productMapper = ProductMapper.INSTANCE;

    @Override
    public Product saveProduct(Product product) {
        return productMapper.toProduct(
                jpaProductRepository.save(productMapper.toProductEntity(product))
        );
    }

    @Override
    public Product getProductById(UUID id, Boolean active) {
        Specification<ProductEntity> specification = Specification.where(ProductSpecifications.hasId(id));
        if(active != null) {
            specification = specification.and(ProductSpecifications.isActive(active));
        }
        return productMapper.toProduct(
                jpaProductRepository.findOne(specification).orElseThrow(() -> new ProductNotFoundException("Product not found!"))
        );
    }

    @Override
    public void deleteProduct(UUID id) {
        jpaProductRepository.deleteProductEntityById(id);
    }

    @Override
    public List<Product> getProducts(ProductType type, Boolean active) {
        Specification<ProductEntity> specification = Specification.where(null);

        if(type != null) {
            specification = specification.and(ProductSpecifications.hasType(type));
        }
        if(active != null) {
            specification = specification.and(ProductSpecifications.isActive(active));
        }
        return jpaProductRepository
                .findAll(specification)
                .stream()
                .map(productMapper::toProduct)
                .toList();
    }

    @Override
    public List<Product> getProductsByIds(List<UUID> ids) {
        return jpaProductRepository.findProductEntitiesByIds(ids)
                .stream()
                .map(productMapper::toProduct)
                .toList();
    }
}

@Repository
interface JpaProductRepository extends JpaRepository<ProductEntity, UUID>, JpaSpecificationExecutor<ProductEntity> {

    @Modifying
    @Query(value = "DELETE FROM ProductEntity p WHERE p.id := id")
    void deleteProductEntityById(@Param("id") UUID id);

    @Query(value = "SELECT p FROM ProductEntity p WHERE p.id IN :ids")
    List<ProductEntity> findProductEntitiesByIds(@Param("ids") List<UUID> ids);

}
