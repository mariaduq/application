package com.example.demo.application.usecases.product;

import com.example.demo.application.mapper.ProductMapper;
import com.example.demo.application.output.ProductOutput;
import com.example.demo.domain.port.ProductsPort;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class GetProductsByTypeUseCase {

    private final ProductsPort productsPort;

    private final ProductMapper productMapper;

    public List<ProductOutput> execute(String productType) throws Exception {
        List<ProductOutput> list = productsPort.getAllProducts()
                .stream()
                .filter(product -> product.getProductType().equalsIgnoreCase(productType))
                .map(productMapper::toOutput)
                .toList();

        if (list.isEmpty()) throw new Exception("No disponemos de ese tipo de producto.");
        return list;
    }
}
