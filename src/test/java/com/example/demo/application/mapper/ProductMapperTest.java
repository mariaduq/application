package com.example.demo.application.mapper;

import com.example.demo.application.output.ProductOutput;
import com.example.demo.domain.model.Product;
import com.example.demo.infrastructure.persistence.entities.PriceEntity;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProductMapperTest {

    private final ProductMapper productMapper = new ProductMapper();

    @Test
    void should_transform_from_Product_to_ProductOutput() {
        //GIVEN
        Product product = Product.builder()
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
        ProductOutput productOutput = productMapper.toOutput(product);

        //THEN
        assertEquals(product.getProductId(), productOutput.getProductId());
        assertEquals(product.getProductType(), productOutput.getProductType());
        assertEquals(product.getBrandName(), productOutput.getBrandName());
        assertEquals(product.getBrandId(), productOutput.getBrandId());
        assertEquals(product.getDescription(), productOutput.getDescription());
        assertEquals(product.getStock(), productOutput.getStock());
        assertEquals(product.getProductPrices(), productOutput.getProductPrices());
    }
}
