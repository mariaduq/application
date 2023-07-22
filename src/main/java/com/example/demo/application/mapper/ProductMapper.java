package com.example.demo.application.mapper;

import com.example.demo.application.output.ProductOutput;
import com.example.demo.application.output.UserOutput;
import com.example.demo.domain.model.Product;
import com.example.demo.domain.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Component("applicationProductMapper")
@Service
public class ProductMapper {

    public ProductOutput toOutput(Product product) {
        return ProductOutput.builder()
                .productId(product.getProductId())
                .productType(product.getProductType())
                .brandName(product.getBrandName())
                .brandId(product.getBrandId())
                .description(product.getDescription())
                .stock(product.getStock())
                .productPrices(product.getProductPrices())
                .build();
    }
}
