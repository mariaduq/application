package com.example.demo.infrastructure.rest.mappers;

import com.example.demo.application.output.ProductOutput;
import com.example.demo.infrastructure.rest.dto.ProductDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component("apiProductMapper")
public class ProductMapper {

    public ProductDTO fromProductOutputToProductDTO(ProductOutput productOutput) {
        return ProductDTO.builder()
                .productId(productOutput.getProductId())
                .productType(productOutput.getProductType())
                .brandName(productOutput.getBrandName())
                .brandId(productOutput.getBrandId())
                .description(productOutput.getDescription())
                .stock(productOutput.getStock())
                .productPrices(productOutput.getProductPrices())
                .build();
    }
}
