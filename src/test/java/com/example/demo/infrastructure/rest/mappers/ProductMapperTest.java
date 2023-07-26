package com.example.demo.infrastructure.rest.mappers;

import com.example.demo.application.output.ProductOutput;
import com.example.demo.infrastructure.persistence.entities.PriceEntity;
import com.example.demo.infrastructure.rest.dto.ProductDTO;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProductMapperTest {

    private final ProductMapper productMapper = new ProductMapper();

    @Test
    void should_transform_from_output_to_dto() {
        //GIVEN
        ProductOutput productOutput = ProductOutput.builder()
                .productId(9126075)
                .productType("Lavadora")
                .brandName("Siemens")
                .brandId(3)
                .description("Electrodoméstico")
                .stock(12)
                .productPrices(List.of(PriceEntity.builder()
                        .priceList(1)
                        .startDate(Timestamp.valueOf(LocalDateTime.now()))
                        .endDate(Timestamp.valueOf(LocalDateTime.now().plusDays(2)))
                        .build()))
                .build();

        //WHEN
        ProductDTO productDTO = productMapper.fromProductOutputToProductDTO(productOutput);

        //THEN
        assertEquals(productOutput.getProductId(), productDTO.getProductId());
        assertEquals(productOutput.getProductType(), productDTO.getProductType());
        assertEquals(productOutput.getBrandName(), productDTO.getBrandName());
        assertEquals(productOutput.getBrandId(), productDTO.getBrandId());
        assertEquals(productOutput.getDescription(), productDTO.getDescription());
        assertEquals(productOutput.getStock(), productDTO.getStock());
        assertEquals(productOutput.getProductPrices(), productDTO.getProductPrices());
    }
}
