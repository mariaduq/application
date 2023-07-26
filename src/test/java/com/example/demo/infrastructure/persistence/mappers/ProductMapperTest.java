package com.example.demo.infrastructure.persistence.mappers;

import com.example.demo.domain.model.Product;
import com.example.demo.infrastructure.persistence.entities.PriceEntity;
import com.example.demo.infrastructure.persistence.entities.ProductEntity;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProductMapperTest {

    private final ProductMapper productMapper = new ProductMapper();

    @Test
    void should_transform_to_domain() {
        //GIVEN
        ProductEntity productEntity = ProductEntity.builder()
                .productId(9136275)
                .productType("Lavadora")
                .brandName("Bosch")
                .brandId(1)
                .description("Electrodoméstico")
                .stock(5)
                .productPrices(List.of(PriceEntity.builder()
                        .priceList(1)
                        .startDate(Timestamp.valueOf(LocalDateTime.now()))
                        .endDate(Timestamp.valueOf(LocalDateTime.now().plusDays(2)))
                        .build()))
                .build();

        //WHEN
        Product product = productMapper.toDomain(productEntity);

        //THEN
        assertEquals(productEntity.getProductId(), product.getProductId());
        assertEquals(productEntity.getProductType(), product.getProductType());
        assertEquals(productEntity.getBrandName(), product.getBrandName());
        assertEquals(productEntity.getBrandId(), product.getBrandId());
        assertEquals(productEntity.getDescription(), product.getDescription());
        assertEquals(productEntity.getStock(), product.getStock());
        assertEquals(productEntity.getProductPrices(), product.getProductPrices());
    }

    @Test
    void should_transform_to_entity() {
        //GIVEN
        Product product = Product.builder()
                .productId(9126275)
                .productType("Microondas")
                .brandName("Balay")
                .brandId(2)
                .description("Electrodoméstico")
                .stock(10)
                .productPrices(List.of(PriceEntity.builder()
                        .priceList(1)
                        .startDate(Timestamp.valueOf(LocalDateTime.now()))
                        .endDate(Timestamp.valueOf(LocalDateTime.now().plusDays(2)))
                        .build()))
                .build();

        //WHEN
        ProductEntity productEntity = productMapper.toEntity(product);

        //THEN
        assertEquals(product.getProductId(), productEntity.getProductId());
        assertEquals(product.getProductType(), productEntity.getProductType());
        assertEquals(product.getBrandName(), productEntity.getBrandName());
        assertEquals(product.getBrandId(), productEntity.getBrandId());
        assertEquals(product.getDescription(), productEntity.getDescription());
        assertEquals(product.getStock(), productEntity.getStock());
        assertEquals(product.getProductPrices(), productEntity.getProductPrices());
    }
}
