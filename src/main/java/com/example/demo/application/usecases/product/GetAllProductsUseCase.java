package com.example.demo.application.usecases.product;

import com.example.demo.application.mapper.ProductMapper;
import com.example.demo.application.output.ProductOutput;
import com.example.demo.domain.port.ProductsPort;
import com.example.demo.domain.model.Product;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class GetAllProductsUseCase {

    private final ProductsPort productsPort;

    private final ProductMapper productMapper;

    public List<ProductOutput> execute() {
        return productsPort.getAllProducts()
                .stream()
                .map(productMapper::toOutput)
                .collect(Collectors.toList());
    }
}
