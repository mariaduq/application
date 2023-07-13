package com.example.demo.infraestructure.persistence.mappers;

import com.example.demo.infraestructure.persistence.entities.ProductEntity;
import com.example.demo.domain.model.Product;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@NoArgsConstructor
public class ProductMapper {

    public Product toDomain(ProductEntity productEntity) {
        if(Objects.isNull(productEntity)) {
            return null;
        }

        return Product.builder()
                .productId(productEntity.getProductId())
                .productType(productEntity.getProductType())
                .brandName(productEntity.getBrandName())
                .brandId(productEntity.getBrandId())
                .description(productEntity.getDescription())
                .stock(productEntity.getStock())
                .productPrices(productEntity.getProductPrices())
                .build();
    }

    public ProductEntity toEntity(Product product) {
        if(Objects.isNull(product)) {
            return null;
        }

        return ProductEntity.builder()
                .productType(product.getProductType())
                .brandName(product.getBrandName())
                .brandId(product.getBrandId())
                .description(product.getDescription())
                .stock(product.getStock())
                .productPrices(product.getProductPrices())
                .build();
    }
}
