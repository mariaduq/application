package com.example.demo.application.usecases.product;

import com.example.demo.domain.port.ProductsPort;
import com.example.demo.domain.Product;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class GetAllProductsUseCase {

    private final ProductsPort productsPort;

    public List<Product> execute() {
        return productsPort.getAllProducts();
    }
}
