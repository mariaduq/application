package com.example.demo.domain.port;

import com.example.demo.domain.model.Product;

import java.util.List;

public interface ProductsPort {

    List<Product> getAllProducts();
}
